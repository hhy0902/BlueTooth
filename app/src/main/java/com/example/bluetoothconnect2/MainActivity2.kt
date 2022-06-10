package com.example.bluetoothconnect2

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.SensorManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothconnect2.adapter.RoomAdapter
import com.example.bluetoothconnect2.databinding.ActivityMain2Binding
import com.example.bluetoothconnect2.model.Room
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.InvocationTargetException
import java.nio.Buffer
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    var i = 1
    var z = 0
    lateinit var roomList : MutableList<Room>

    var spinnerData1 = ""
    var editData1 = ""
    var spinnerData2 = ""
    var editData2 = ""
    var spinnerData3 = ""
    var editData3 = ""
    var spinnerData4 = ""
    var editData4 = ""
    var spinnerData5 = ""
    var editData5 = ""
    var spinnerData6 = ""
    var editData6 = ""
    var spinnerData7 = ""
    var editData7 = ""
    var spinnerData8 = ""
    var editData8 = ""
    var spinnerData9 = ""
    var editData9 = ""
    var spinnerData10 = ""
    var editData10 = ""

    var address = ""
    var name = ""
    var roomName = ""
    val random = Random().nextInt(9)+1

    var testNumber = 1

    lateinit var sharedPreferences : SharedPreferences

    companion object {
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var progress: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomList = mutableListOf<Room>()
        roomList.add(Room("room 1"))

        var editDataList = mutableListOf<String>()
        var spinnerDataList = mutableListOf<String>()

        binding.gateWayText.text = "$address $name"

        val startActivityLauncher : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        spinnerData1 = it.data?.getStringExtra("spinner1").toString()
                        editData1 = it.data?.getStringExtra("editdata1").toString()
                        spinnerData2 = it.data?.getStringExtra("spinner2").toString()
                        editData2 = it.data?.getStringExtra("editdata2").toString()
                        spinnerData3 = it.data?.getStringExtra("spinner3").toString()
                        editData3 = it.data?.getStringExtra("editdata3").toString()
                        spinnerData4 = it.data?.getStringExtra("spinner4").toString()
                        editData4 = it.data?.getStringExtra("editdata4").toString()
                        spinnerData5 = it.data?.getStringExtra("spinner5").toString()
                        editData5 = it.data?.getStringExtra("editdata5").toString()
                        spinnerData6 = it.data?.getStringExtra("spinner6").toString()
                        editData6 = it.data?.getStringExtra("editdata6").toString()
                        spinnerData7 = it.data?.getStringExtra("spinner7").toString()
                        editData7 = it.data?.getStringExtra("editdata7").toString()
                        spinnerData8 = it.data?.getStringExtra("spinner8").toString()
                        editData8 = it.data?.getStringExtra("editdata8").toString()
                        spinnerData9 = it.data?.getStringExtra("spinner9").toString()
                        editData9 = it.data?.getStringExtra("editdata9").toString()
                        spinnerData10 = it.data?.getStringExtra("spinner10").toString()
                        editData10 = it.data?.getStringExtra("editdata10").toString()

                        roomName = it.data?.getStringExtra("roomName").toString()
                        Log.d("asdf roomname","$roomName")
                    }
                }
            }

        val startActivityLauncher2 : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        address = it.data?.getStringExtra("Device_address").toString()
                        name = it.data?.getStringExtra("Device_name").toString()
                        binding.gateWayText.text = "$name $address"

                        Thread {
                            try {
                                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                                val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
                                bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

                                bluetoothSocket?.connect()
                                isConnected = true

                                runOnUiThread {
                                    Toast.makeText(this, "${bluetoothSocket?.remoteDevice?.name} 연결 성공", Toast.LENGTH_SHORT).show()
                                    binding.checkLed.setImageResource(R.drawable.ic_baseline_circle_24)
                                }

                            } catch (e: IOException) {
                                e.printStackTrace()
                                isConnected = false
                                runOnUiThread {
                                    Toast.makeText(this, "연결 실패", Toast.LENGTH_SHORT).show()
                                    binding.checkLed.setImageResource(R.drawable.ic_baseline_circle_red_24)
                                }
                            }
                        }.start()
                    }
                }
            }

        val roomAdapter = RoomAdapter(this,roomList, startActivityLauncher, name)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = roomAdapter

        binding.btnAdd.setOnClickListener {
            i = roomList.size + 1
            roomList.add(Room("room $i"))
            roomAdapter.notifyDataSetChanged()
        }

        binding.btnDelete.setOnClickListener {
            roomList.removeAt(roomList.size-1)
            roomAdapter.notifyDataSetChanged()
        }

        binding.btnClearData.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("clear button")
            builder.setMessage("모든 데이터를 삭제하시겠습니까?")
            builder.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, which ->
                roomAllClear()
                Log.d("asdf dialog cancle", "clear")
            })
            builder.setNegativeButton("취소",DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                Log.d("asdf dialog cancle", "cancel")
            })
            builder.show()
        }

        binding.btnSave.setOnClickListener {
            Log.d("qwer", roomList.size.toString())
            binding.dataText.text = spinnerData1 + spinnerData2 + spinnerData3 + spinnerData4 + spinnerData5 + spinnerData6 + spinnerData7 + spinnerData8 + spinnerData9 + spinnerData10
            binding.dataText2.text = editData1 + editData2 + editData3 + editData4 + editData5 + editData6 + editData7 + editData8 + editData9 + editData10

            spinnerDataList.add(0, spinnerData1)
            spinnerDataList.add(1, spinnerData2)
            spinnerDataList.add(2, spinnerData3)
            spinnerDataList.add(3, spinnerData4)
            spinnerDataList.add(4, spinnerData5)
            spinnerDataList.add(5, spinnerData6)
            spinnerDataList.add(6, spinnerData7)
            spinnerDataList.add(7, spinnerData8)
            spinnerDataList.add(8, spinnerData9)
            spinnerDataList.add(9, spinnerData10)

            editDataList.add(0, editData1)
            editDataList.add(1, editData2)
            editDataList.add(2, editData3)
            editDataList.add(3, editData4)
            editDataList.add(4, editData5)
            editDataList.add(5, editData6)
            editDataList.add(6, editData7)
            editDataList.add(7, editData8)
            editDataList.add(8, editData9)
            editDataList.add(9, editData10)

            Log.d("asdf 1", isConnected.toString())
            Log.d("asdf 2", bluetoothSocket?.isConnected.toString())

            if (bluetoothSocket?.isConnected == true) {
                for (p in 1..10) {
                    val testDelete3 =
                        "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_$p\"}],\"id\":\"qwerasd$random\"}"
                    sendCommand(testDelete3)
                    Thread.sleep(200)
                }

                for(i in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                    Log.d("asdf spinnerData5_2", "${sharedPreferences.all.get("spinnerData5_Device")}")
                    Log.d("asdf spinnerData5_3", "${sharedPreferences.all.get("spinnerData5_Company")}")
                    Log.d("asdf spinnerData5_4", "${sharedPreferences.all.get("spinnerData5_Model")}")
                    val jsonrpcObject = JSONObject()
                    val jsonrpcArray = JSONArray()
                    val jsonrpcArrayObject = JSONObject()
                    val jsonrpcValue = JSONObject()
                    val jsonrpcValue2 = JSONObject()
                    val jsonrpcUnitsValue = JSONObject()

                    jsonrpcObject.put("jsonrpc","2.0")
                    jsonrpcObject.put("method","patch_gz")
                    jsonrpcArrayObject.put("op","add")
                    jsonrpcArrayObject.put("path","/room_$i")
                    jsonrpcValue2.put("node_id",sharedPreferences.all.get("editdata1").toString().toInt())
                    jsonrpcValue.put("blaster",jsonrpcValue2)

                    for(z in 1..9) {
                        var jsonrpcUnitsValue2 = JSONObject()
                        var jsonrpcPlugValue = JSONObject()

/*
        1 = pc, 2= 모니터, 3= 스피커, 4= 프로젝터1, 5= 프로젝터2, 6= 공기청정기, 7=에어컨, 8=조명, 9=좌타모니터
*/
                        if(z == 4 || z == 5) {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        } else if(z == 1 || z == 2 || z == 3 || z == 6 || z == 9) {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                        } else if(z == 7 || z == 8) {
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        }
//                        if(sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 5 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 6) {
//                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
//                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
//                        } else if(sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 1 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 2 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 3 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 4 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 7) {
//                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
//                        } else if(sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 10 || sharedPreferences.all.get("spinnerSelect${z}").toString().toInt() == 8) {
//                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
//                        }
                        jsonrpcUnitsValue.put("$z", jsonrpcUnitsValue2)
                    }

                    jsonrpcValue.put("units", jsonrpcUnitsValue)
                    jsonrpcArrayObject.put("value",jsonrpcValue)
                    jsonrpcArray.put(jsonrpcArrayObject)
                    jsonrpcObject.put("params",jsonrpcArray)
                    jsonrpcObject.put("id","qwerasd$random")

                    sendCommand(jsonrpcObject.toString())

                    Log.d("asdf jsonrpcobject", jsonrpcObject.toString())
                    Thread.sleep(200)
                }

                //receiveData()

                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1..9) {
                        val jsonrpcObject_2 = JSONObject()
                        val jsonrpcArray_2 = JSONArray()
                        val jsonrpcArrayObject_2 = JSONObject()

                        jsonrpcObject_2.put("jsonrpc", "2.0")
                        jsonrpcObject_2.put("method", "patch_gz")
                        jsonrpcArrayObject_2.put("op", "add")
                        jsonrpcArrayObject_2.put("path", "/room_$q/units/$i/plug/nid")
                        jsonrpcArrayObject_2.put("value", sharedPreferences.all.get("editdata${i+1}").toString().toInt())
                        jsonrpcArray_2.put(jsonrpcArrayObject_2)
                        jsonrpcObject_2.put("params", jsonrpcArray_2)
                        jsonrpcObject_2.put("id", "qwerasd$random")
                        sendCommand(jsonrpcObject_2.toString())

                        Thread.sleep(100)
                        Log.d("asdf jsonrpcobject_node_id_${q}_$i", jsonrpcObject_2.toString())
                    }
                }

                //receiveData()

                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1..9) {
                        val jsonrpcObject_2 = JSONObject()
                        val jsonrpcArray_2 = JSONArray()
                        val jsonrpcArrayObject_2 = JSONObject()

                        jsonrpcObject_2.put("jsonrpc", "2.0")
                        jsonrpcObject_2.put("method", "patch_gz")
                        jsonrpcArrayObject_2.put("op", "add")
                        jsonrpcArrayObject_2.put("path", "/room_$q/units/$i/plug/use_switch")

                        if(sharedPreferences.all.get("spinnerSelect${i+1}").toString().toInt() == 1) {
                            jsonrpcArrayObject_2.put("value", true)
                        } else
                            jsonrpcArrayObject_2.put("value", false)

                        //jsonrpcArrayObject_2.put("value", false)
                        jsonrpcArray_2.put(jsonrpcArrayObject_2)
                        jsonrpcObject_2.put("params", jsonrpcArray_2)
                        jsonrpcObject_2.put("id", "qwerasd$random")
                        sendCommand(jsonrpcObject_2.toString())

                        Thread.sleep(100)
                        Log.d("asdf jsonrpcobject_use_switch_${q}_$i", jsonrpcObject_2.toString())
                        Log.d("asdf jsonrpcobject_spinnerSelect${q}_$i", sharedPreferences.all.get("spinnerSelect${i}").toString())
                        Log.d("asdf jsonrpcobject_editdata${q}_$i", sharedPreferences.all.get("editdata${i}").toString())
                    }
                }

                //receiveData()

                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1..9) {
                        val jsonrpcObject_2 = JSONObject()
                        val jsonrpcArray_2 = JSONArray()
                        val jsonrpcArrayObject_2 = JSONObject()

                        jsonrpcObject_2.put("jsonrpc", "2.0")
                        jsonrpcObject_2.put("method", "patch_gz")
                        jsonrpcArrayObject_2.put("op", "add")
                        jsonrpcArrayObject_2.put("path", "/room_$q/units/$i/blaster/ir_key")
                        jsonrpcArrayObject_2.put("value", "${sharedPreferences.all.get("spinnerData${i+1}_Device")}/${sharedPreferences.all.get("spinnerData${i+1}_Company")}/${sharedPreferences.all.get("spinnerData${i+1}_Model")}")
                        //jsonrpcArrayObject_2.put("value", "projector/maxell/mp-eu5002")
                        jsonrpcArray_2.put(jsonrpcArrayObject_2)
                        jsonrpcObject_2.put("params", jsonrpcArray_2)
                        jsonrpcObject_2.put("id", "qwerasd$random")
                        sendCommand(jsonrpcObject_2.toString())

                        Thread.sleep(100)
                        Log.d("asdf jsonrpcobject_ir_key_${q}_$i", jsonrpcObject_2.toString())
                    }
                }

                //receiveData()
                Log.d("asdf roomlistsize", "${roomList.size}")

//                for(e in 1..roomList.size) {
//                    sharedPreferences = getSharedPreferences("$e", Context.MODE_PRIVATE)
//
//                    val testBody2 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                    val testBody3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                    val testBody4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasd$random\"}"
//                    val testBody5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//
//                    Thread.sleep(100)
//                    sendCommand(testBody2)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody3)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody4)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody5)
//                    Thread.sleep(100)
//
//                    for (w in 1..10) {
//                        val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody6)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody6_${e}_$w", testBody6)
//
//                        val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody7)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody7_${e}_$w", testBody7)
//
//                        val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/use_switch\",\"value\":\"false\"}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody8)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody8_${e}_$w", testBody8)
//
//                        val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/remote_key\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody9)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody9_${e}_$w", testBody9)
//                    }
//                    receiveData()
//                }

                editDataList.clear()
                spinnerDataList.clear()

                Toast.makeText(this,"전송 완료",Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnConnect.setOnClickListener {
            if (bluetoothSocket?.isConnected == true) {
                bluetoothSocket!!.close()
                Log.d("asdf 3", "끊기")
            }

            val intent = Intent(this, MainActivity::class.java)
            //itemView.context.startActivity(intent)
            intent.putExtra("test123","hello")
            startActivityLauncher2.launch(intent)
        }

        binding.btnLoad.setOnClickListener {

            if (bluetoothSocket?.isConnected == true) {
                receiveData()

//                // 임시로 나중에 지워야
//                for (p in 1..roomList.size) {
//                    val testDelete3 =
//                        "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_$p\"}],\"id\":\"qwerasd$random\"}"
//                    sendCommand(testDelete3)
//                    Thread.sleep(1000)
//                    Log.d("asdf delete","clear")
//                }
//
//                val testBodys =
//                    "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"replace\",\"path\":\"\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                sendCommand(testBodys)
//                Thread.sleep(1000)
//
//                val testBody =
//                    "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                sendCommand(testBody)
//                Thread.sleep(1000)

            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        if (z == 0)
            Toast.makeText(this,"한번 더 누르면 종료",Toast.LENGTH_SHORT).show()
        z++
        if(z >= 2) {
            disconnect()
            super.onBackPressed()
            z = 0
        }
    }

    private fun receiveData() {
        try {
            val byteAvailable = bluetoothSocket!!.inputStream.available()
            if (byteAvailable > 0) {
                var byte = bluetoothSocket!!.inputStream.read()
                //val readColor = ByteArray(832)
                val readColor = ByteArray(byteAvailable - 2)
                bluetoothSocket!!.inputStream.read(readColor)
                val string = String(readColor)
                val test = string.length
//                    Log.d("asdf receiveData2", "${byteAvailable}")
//                    Log.d("asdf receiveData3", "${bluetoothSocket!!.inputStream.read()}")
//                    Log.d("asdf receiveData4", "${byte}")
                Log.d("asdf receiveData7", "${string}")
                //Log.d("asdf receiveData8", "${test}")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("asdf receiveData", "No receiveData")
        }

    }

    private fun sendCommand(input: String) {
        if(bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(input.toByteArray())

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun disconnect() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.close()
                bluetoothSocket = null
                if(!isConnected == false)
                    Toast.makeText(applicationContext,"연결 해제",Toast.LENGTH_SHORT).show()
                isConnected = false
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }

    private fun roomAllClear() {
        val sharedPreferences = getSharedPreferences("1",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        val sharedPreferences2 = getSharedPreferences("2",Context.MODE_PRIVATE)
        val editor2 : SharedPreferences.Editor = sharedPreferences2.edit()

        val sharedPreferences3 = getSharedPreferences("3",Context.MODE_PRIVATE)
        val editor3 : SharedPreferences.Editor = sharedPreferences3.edit()

        val sharedPreferences4 = getSharedPreferences("4",Context.MODE_PRIVATE)
        val editor4 : SharedPreferences.Editor = sharedPreferences4.edit()

        val sharedPreferences5 = getSharedPreferences("5",Context.MODE_PRIVATE)
        val editor5 : SharedPreferences.Editor = sharedPreferences5.edit()

        val sharedPreferences6 = getSharedPreferences("6",Context.MODE_PRIVATE)
        val editor6 : SharedPreferences.Editor = sharedPreferences6.edit()

        val sharedPreferences7 = getSharedPreferences("7",Context.MODE_PRIVATE)
        val editor7 : SharedPreferences.Editor = sharedPreferences7.edit()

        val sharedPreferences8 = getSharedPreferences("8",Context.MODE_PRIVATE)
        val editor8 : SharedPreferences.Editor = sharedPreferences8.edit()

        val sharedPreferences9 = getSharedPreferences("9",Context.MODE_PRIVATE)
        val editor9 : SharedPreferences.Editor = sharedPreferences9.edit()

        val sharedPreferences10 = getSharedPreferences("10",Context.MODE_PRIVATE)
        val editor10 : SharedPreferences.Editor = sharedPreferences10.edit()

        val sharedPreferences11 = getSharedPreferences("11",Context.MODE_PRIVATE)
        val editor11 : SharedPreferences.Editor = sharedPreferences11.edit()

        val sharedPreferences12 = getSharedPreferences("12",Context.MODE_PRIVATE)
        val editor12 : SharedPreferences.Editor = sharedPreferences12.edit()

        val sharedPreferences13 = getSharedPreferences("13",Context.MODE_PRIVATE)
        val editor13 : SharedPreferences.Editor = sharedPreferences13.edit()

        editor.clear()
        editor.commit()
        editor2.clear()
        editor2.commit()
        editor3.clear()
        editor3.commit()
        editor4.clear()
        editor4.commit()
        editor5.clear()
        editor5.commit()
        editor6.clear()
        editor6.commit()
        editor7.clear()
        editor7.commit()
        editor8.clear()
        editor8.commit()
        editor9.clear()
        editor9.commit()
        editor10.clear()
        editor10.commit()
        editor11.clear()
        editor11.commit()
        editor12.clear()
        editor12.commit()
        editor13.clear()
        editor13.commit()
    }
}
























