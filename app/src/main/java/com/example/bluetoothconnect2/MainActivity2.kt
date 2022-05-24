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
                val json = "{\"jsonrpc\": \"2.0\",\"method\": " +
                        "\"gz_patch\",\"params\": " + "[{\"op\": \"replace\",\"path\": \"/plugs\"}],\"value\": [{\"node_id\": 1234567,\"room_id\": 1,\"unit_id\": 1,\"use_blaster\": true},{\"node_id\": 1234568,\"room_id\": 1,\"unit_id\": 2,\"use_blaster\": false}]}"

                val jsonObject = JSONObject()
                val jsonObjectList = JSONArray()
                val jsonObjectList2 = JSONArray()
                val jsonObjectList3 = JSONArray()

                val jsonObject2 = JSONObject()
                val jsonObject3 = JSONObject()


                //sharedPreferences = getSharedPreferences("1",Context.MODE_PRIVATE)

//                for (i in 0..9) {
//                    val tempJsonObject = JSONObject()
//                    tempJsonObject.put("node_id", editDataList[i])
//                    //tempJsonObject.put("room_id", i+1)
//                    tempJsonObject.put("room_id", roomName)
//                    tempJsonObject.put("unit_id", spinnerDataList[i])
//                    tempJsonObject.put("use_blaster", true)
//                    jsonObjectList.put(tempJsonObject)
//                }

//                for(a in 1..roomList.size) {
//                    sharedPreferences = getSharedPreferences("$a",Context.MODE_PRIVATE)
//                    for (i in 1..10) {
//                        //Log.d("asdf share2", "${sharedPreferences.all.get("editdata$i")}")
//                        val tempJsonObject = JSONObject()
//                        tempJsonObject.put("node_id", sharedPreferences.all.get("editdata$i"))
//                        //tempJsonObject.put("room_id", i+1)
//                        tempJsonObject.put("room_id", a)
//                        tempJsonObject.put("unit_id", sharedPreferences.all.get("spinnerSelect$i"))
//                        tempJsonObject.put("use_blaster", false)
//                        jsonObjectList.put(tempJsonObject)
//
//                    }
//                    Log.d("asdf list2 ", "${jsonObjectList.length()}")
//                }


                jsonObject.put("jsonrpc", "2.0")
                jsonObject.put("method", "patch_gz")
                val jsonObjectParams = JSONObject()
                jsonObjectParams.put("op","replace")
                //jsonObjectParams.put("path","/plugs")
                jsonObjectList2.put(jsonObjectParams)
                jsonObject.put("params", jsonObjectList2)

//                jsonObject.put("value",jsonObjectList)
//                Log.d("asdf jsonobject", jsonObject.toString())



//                for(a in 1..roomList.size) {
//                    sharedPreferences = getSharedPreferences("$a",Context.MODE_PRIVATE)
//                    for (i in 1..10) {
//                        val tempJsonObject = JSONObject()
//
//                        tempJsonObject.put("node_id", sharedPreferences.all.get("editdata$i"))
//                        tempJsonObject.put("room_id", a)
//                        tempJsonObject.put("unit_id", sharedPreferences.all.get("spinnerSelect$i"))
//
//                        Log.d("asdf tempjsonobject", tempJsonObject.toString())
//                        jsonObjectList.put(tempJsonObject)
//                        jsonObject2.put("device $i", jsonObjectList.get(i-1))
//                        //jsonObject2.put("device $i", tempJsonObject)
//                        Log.d("asdf jsonobject2", jsonObject2.toString())
//                        jsonObject3.put("room_$a", jsonObject2)
//                    }
//                    Log.d("asdf jsonobject3", jsonObject3.toString())
//                    jsonObject.put("plugs",jsonObject3)
//
//                    jsonObject.put("id","qwerasdf$random")
//                }
//
//                Log.d("asdf jsonobject3-2", jsonObject3.toString())
//                Log.d("asdf jsonobject4", jsonObject.toString())
//                Log.d("asdf listsize", "${jsonObjectList.length()}")

                //sendCommand(jsonObject.toString())


                val qwer = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": room_1\"\"}, \"id\": \"0o8hyx24\"}"
                val zxcv = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"\"}, \"id\": \"looj2y8v\"}"

                val jsonrpcObject = JSONObject()
                val jsonrpcArray = JSONArray()
                val jsonrpcArray2 = JSONArray()
                jsonrpcObject.put("jsonrpc","2.0")
                jsonrpcObject.put("method","patch_gz")


                val jsonrpcObjectParams = JSONObject()
                val jsonrpcObjectParams2 = JSONObject()
                val jsonrpcObjectParams3 = JSONObject()
                val jsonrpcObjectParams4 = JSONObject()
                val jsonrpcObjectParams5 = JSONObject()
                val jsonrpcObjectParams6 = JSONObject()



//                for(a in 1..roomList.size) {
//                    sharedPreferences = getSharedPreferences("$a",Context.MODE_PRIVATE)
//                    for (i in 1..10) {
//                        //Log.d("asdf share2", "${sharedPreferences.all.get("editdata$i")}")
//                        val tempJsonObject = JSONObject()
//                        tempJsonObject.put("node_id", sharedPreferences.all.get("editdata$i"))
//                        //tempJsonObject.put("room_id", i+1)
//                        tempJsonObject.put("room_id", a)
//                        tempJsonObject.put("unit_id", sharedPreferences.all.get("spinnerSelect$i"))
//                        tempJsonObject.put("use_blaster", false)
//                        jsonObjectList.put(tempJsonObject)
//
//                    }
//                    Log.d("asdf list2 ", "${jsonObjectList.length()}")
//                }

                jsonrpcObjectParams.put("op","replace")
                jsonrpcObjectParams.put("path","")



//                jsonrpcObjectParams6.put("node_id",sharedPreferences.all.get("editdata2"))
//                jsonrpcObjectParams6.put("use_switch","true")
//                jsonrpcObjectParams6.put("remote_key","/projector/maxell/mp-eu5002")
//                jsonrpcObjectParams5.put("1",jsonrpcObjectParams6)

//                for(i in 0..9) {
//                    val jsonrpcObjectParams6 = JSONObject()
//                    jsonrpcObjectParams6.put("node_id",sharedPreferences.all.get("editdata${i+1}"))
//                    jsonrpcObjectParams6.put("use_switch","false")
//                    jsonrpcObjectParams6.put("remote_id","projector")
//
//                    //Log.d("asdf jsonrpcObjectParams6", "${jsonrpcObjectParams6}")
//                    jsonrpcArray2.put(jsonrpcObjectParams6)
//
//                    jsonrpcObjectParams5.put("${i+1}",jsonrpcArray2.get(i))
//
//                }
//                Log.d("asdf jsonrpcArray2", "${jsonrpcArray2}")
//                Log.d("asdf sharedPreferences", "${sharedPreferences.all.get("editdata10")}")
//                Log.d("asdf jsonrpcArray9", "${jsonrpcArray2.get(9)}")
//
//                jsonrpcObjectParams4.put("node_id",300002)
//                jsonrpcObjectParams3.put("blaster",jsonrpcObjectParams4)
//                jsonrpcObjectParams3.put("plugs",jsonrpcObjectParams5)
//
//                jsonrpcObjectParams2.put("room_1",jsonrpcObjectParams3)
//
//                jsonrpcObjectParams.put("value",jsonrpcObjectParams2)
//
//                jsonrpcArray.put(jsonrpcObjectParams)
//                jsonrpcObject.put("params", jsonrpcArray)
//                jsonrpcObject.put("id","qwerasdf$random")

                val test = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs/11\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"

                for (p in 1..roomList.size) {
                    val testDelete3 =
                        "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_$p\"}],\"id\":\"qwerasd$random\"}"
                    sendCommand(testDelete3)
                    Thread.sleep(500)
                }

                val testDelete4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"room_2\"}],\"id\":\"qwerasdf$random\"}"
                sendCommand(testDelete4)
                Thread.sleep(50)

                val testDelete5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_3\"}],\"id\":\"qwerasdf$random\"}"
                sendCommand(testDelete5)
                Thread.sleep(50)

                val testDelete6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_4\"}],\"id\":\"qwerasdf$random\"}"
                sendCommand(testDelete6)
                Thread.sleep(50)

                val testDelete7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_5\"}],\"id\":\"qwerasdf$random\"}"
                sendCommand(testDelete7)
                Thread.sleep(50)

                val testDelete8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_6\"}],\"id\":\"qwerasdf$random\"}"
                sendCommand(testDelete8)
                Thread.sleep(50)

                Log.d("asdf roomlistsize", "${roomList.size}")

//                val testBodys = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBodys)
//                Thread.sleep(1000)

//                val testBody = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody)
//                Thread.sleep(1000)

                val testBody2 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/blaster\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasdf$random\"}"
                val testBody5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"

                val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/blaster\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasdf$random\"}"
                val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/plugs\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"

                val testBody10 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody11 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/blaster\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
                val testBody12 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasdf$random\"}"
                val testBody13 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"

                sharedPreferences = getSharedPreferences("1", Context.MODE_PRIVATE)
//
//                sendCommand(testBody2)
//                Thread.sleep(1000)
//                sendCommand(testBody3)
//                Thread.sleep(1000)
//                sendCommand(testBody4)
//                Thread.sleep(1000)
//                sendCommand(testBody5)
//                Thread.sleep(1000)
//
//                for (w in 1..10) {
//                    val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs/$w\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody6)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody6", testBody6)
//
//                    val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody7)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody7", testBody7)
//
//                    val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs/$w/use_switch\",\"value\":\"true\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody8)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody8", testBody8)
//
//                    val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_1/plugs/$w/remote_id\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody9)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody9", testBody9)
//                }
//
//                sendCommand(testBody6)
//                Thread.sleep(1000)
//                sendCommand(testBody7)
//                Thread.sleep(1000)
//                sendCommand(testBody8)
//                Thread.sleep(1000)
//                sendCommand(testBody9)
//                Thread.sleep(1000)
//
//                for (w in 1..10) {
//                    val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/plugs/$w\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody6)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody6_2", testBody6)
//
//                    val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody7)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody7_2", testBody7)
//
//                    val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/plugs/$w/use_switch\",\"value\":\"true\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody8)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody8_2", testBody8)
//
//                    val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_2/plugs/$w/remote_id\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody9)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody9_2", testBody9)
//                }
//
//                sendCommand(testBody10)
//                Thread.sleep(1000)
//                sendCommand(testBody11)
//                Thread.sleep(1000)
//                sendCommand(testBody12)
//                Thread.sleep(1000)
//                sendCommand(testBody13)
//                Thread.sleep(1000)

//                val testBody6_3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/1\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_3)
//                Thread.sleep(1000)
//
//                val testBody6_4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/2\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_4)
//                Thread.sleep(1000)
//
//                val testBody6_5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/3\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_5)
//                Thread.sleep(1000)
//
//                val testBody6_6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/4\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_6)
//                Thread.sleep(1000)
//
//                val testBody6_7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/5\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_7)
//                Thread.sleep(1000)
//
//                val testBody6_8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/6\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_8)
//                Thread.sleep(1000)
//
//                val testBody6_9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/7\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_9)
//                Thread.sleep(1000)
//
//                val testBody6_10 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/8\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_10)
//                Thread.sleep(1000)
//
//                val testBody6_11 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/9\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_11)
//                Thread.sleep(1000)
//
//                val testBody6_12 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/10\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                sendCommand(testBody6_12)
//                Thread.sleep(1000)
//
//
//                for (w in 1..10) {
//                    val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/$w\",\"value\":{}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody6)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody6_3", testBody6)
//
//                    val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody7)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody7_3", testBody7)
//
//                    val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/$w/use_switch\",\"value\":\"true\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody8)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody8_3", testBody8)
//
//                    val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_3/plugs/$w/remote_id\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasdf$random\"}"
//                    sendCommand(testBody9)
//                    Thread.sleep(1000)
//                    Log.d("asdf testBody9_3", testBody9)
//                }

                for(e in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$e", Context.MODE_PRIVATE)

                    val testBody2 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
                    val testBody3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
                    val testBody4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasd$random\"}"
                    val testBody5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs\",\"value\":{}}],\"id\":\"qwerasd$random\"}"

                    Thread.sleep(100)
                    sendCommand(testBody2)

                    Thread.sleep(100)
                    sendCommand(testBody3)

                    Thread.sleep(100)
                    sendCommand(testBody4)

                    Thread.sleep(100)
                    sendCommand(testBody5)
                    Thread.sleep(100)

                    for (w in 1..10) {
                        val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
                        sendCommand(testBody6)
                        Thread.sleep(100)
                        Log.d("asdf testBody6_${e}_$w", testBody6)

                        val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasd$random\"}"
                        sendCommand(testBody7)
                        Thread.sleep(100)
                        Log.d("asdf testBody7_${e}_$w", testBody7)

                        val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/use_switch\",\"value\":\"false\"}],\"id\":\"qwerasd$random\"}"
                        sendCommand(testBody8)
                        Thread.sleep(100)
                        Log.d("asdf testBody8_${e}_$w", testBody8)

                        val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/remote_key\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasd$random\"}"
                        sendCommand(testBody9)
                        Thread.sleep(100)
                        Log.d("asdf testBody9_${e}_$w", testBody9)
                    }
                    receiveData()
                }

                //sendCommand(jsonrpcObject.toString())
                //val asdf = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"replace\", \"path\":\"\", \"value\": \"{room_1qwerasdf}\"}],\"id\":\"qwerasdf$random\"}"
                //sendCommand(asdf)

                Log.d("asdf jsonobject length", jsonObject.toString().length.toString())
                editDataList.clear()
                spinnerDataList.clear()

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
























