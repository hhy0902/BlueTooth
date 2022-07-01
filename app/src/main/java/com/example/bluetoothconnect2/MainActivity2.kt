package com.example.bluetoothconnect2

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothconnect2.ItemKey.Companion.MAXELL
import com.example.bluetoothconnect2.ItemKey.Companion.PROJECTOR
import com.example.bluetoothconnect2.adapter.RoomAdapter
import com.example.bluetoothconnect2.databinding.ActivityMain2Binding
import com.example.bluetoothconnect2.model.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
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

    var loadJsonString = ""

    lateinit var readMessage : String

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

        // 룸 추가한거 유지를 위해 ActivityResultLauncher 사용?
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


                        roomName = it.data?.getStringExtra("roomName").toString()
                        Log.d("asdf roomname","$roomName")
                    }
                }
            }

        // 룸 추가한거 유지를 위해 ActivityResultLauncher 사용?
        val startActivityLauncher2 : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        address = it.data?.getStringExtra("Device_address").toString()
                        name = it.data?.getStringExtra("Device_name").toString()
                        binding.gateWayText.text = "$name $address"

                        // bluetooth 연결
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

        // recyclerView에 룸 추가
        binding.btnAdd.setOnClickListener {
            i = roomList.size + 1
            roomList.add(Room("room $i"))
            roomAdapter.notifyDataSetChanged()
        }

        // recyclerView에서 룸 삭제
        binding.btnDelete.setOnClickListener {
            roomList.removeAt(roomList.size-1)
            roomAdapter.notifyDataSetChanged()
        }

        // 룸에 있는 모든 데이터 삭제 & 다이얼로그
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

        // gateway로 값을 저장하는 버튼 누르면 gateway로 값이 들어감
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


            editDataList.add(0, editData1)
            editDataList.add(1, editData2)
            editDataList.add(2, editData3)
            editDataList.add(3, editData4)
            editDataList.add(4, editData5)
            editDataList.add(5, editData6)
            editDataList.add(6, editData7)
            editDataList.add(7, editData8)


            Log.d("asdf 1", isConnected.toString())
            Log.d("asdf 2", bluetoothSocket?.isConnected.toString())



            if (bluetoothSocket?.isConnected == true) {

                // 룸1부터 10까지 초기화
                for (p in 1..10) {
                    val testDelete3 =
                        "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_$p\"}],\"id\":\"qwerasd$random\"}"
                    sendCommand(testDelete3)
                    Thread.sleep(200)
                }

                //irdb 초기화
                val testDelete4 =
                    "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/irdb\"}],\"id\":\"qwerasd$random\"}"
                sendCommand(testDelete4)
                Thread.sleep(200)



                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 룸 틀만 만들어서 보내줌
                // 틀이랑 내용물 같이 보내기?
                for(i in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                    val jsonrpcObject = JSONObject()
                    val jsonrpcArray = JSONArray()
                    val jsonrpcArrayObject = JSONObject()
                    val jsonrpcValue = JSONObject()
                    val jsonrpcValue2 = JSONObject()
                    val jsonrpcUnitsValue = JSONObject()
                    val irdbObject = JSONObject()

                    jsonrpcObject.put("jsonrpc","2.0")
                    jsonrpcObject.put("method","patch_gz")
                    jsonrpcArrayObject.put("op","add")
                    jsonrpcArrayObject.put("path","/room_$i")
                    jsonrpcValue2.put("node_id",sharedPreferences.all.get("editdata1").toString().toInt())
                    jsonrpcValue.put("blaster",jsonrpcValue2)

                    for(z in 1 until 7) {
                        var jsonrpcUnitsValue2 = JSONObject()
                        var jsonrpcPlugValue = JSONObject()
/*
        1=pc, 2=스피커, 3=조명, 4=프로젝터1, 5=프로젝터2, 6=에어컨
*/
                        if(z == 4 || z == 5) {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        } else if(z == 1 || z == 2)   {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                        } else if(z == 6 || z == 3) {
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        }

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

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 plug/nid만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 7) {

                        if (i != 3 && i != 6) {
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
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 plug/use_switch만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 7) {
                        if (i != 3 && i != 6) {
                            val jsonrpcObject_2 = JSONObject()
                            val jsonrpcArray_2 = JSONArray()
                            val jsonrpcArrayObject_2 = JSONObject()
                            jsonrpcObject_2.put("jsonrpc", "2.0")
                            jsonrpcObject_2.put("method", "patch_gz")
                            jsonrpcArrayObject_2.put("op", "add")
                            jsonrpcArrayObject_2.put("path", "/room_$q/units/$i/plug/use_switch")

                            if (i == 1) {
                                jsonrpcArrayObject_2.put("value", true)
                            } else {
                                jsonrpcArrayObject_2.put("value", false)
                            }

//                            if(sharedPreferences.all.get("spinnerSelect${i+1}").toString().toInt() == 1) {
//                                jsonrpcArrayObject_2.put("value", true)
//                            } else
//                                jsonrpcArrayObject_2.put("value", false)

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
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 blaster/ir_key만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 7) {
                        if(i == 3 || i == 6 || i == 4 || i == 5) {
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
                }

                for(i in 1..1) {

//                    // irdb.keys ['projector', 'aircon',
//                    for i in irdb.keys:
//                    for j in manufe~.keys:
//                    for k in models.keys:
//                    path = / irdb / + i + / + j + / + k + /
//                    jsonString =


                    // base ==========================================================================================================================================================

                    val order = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order)
                    Thread.sleep(200)

                    val irdbString = assets.open("irdbV2.json").reader().readText()
                    val irdb = JSONObject(irdbString)
                    Log.d("asdf irdbString", irdb.toString())
                    val irdb_device_key_list = mutableListOf<String>()
                    irdb.keys().forEach {
                        irdb_device_key_list.add(it)
                        Log.d("asdf irdb key", it)
                    }

                    // projector ==========================================================================================================================================================

                    val irdb_projector = irdb.getString("projector")
                    Log.d("asdf irdb_projector", irdb_projector)
                    val irdb_projector_key = JSONObject(irdb_projector)
                    val irdb_projector_model_key_list = mutableListOf<String>()
                    irdb_projector_key.keys().forEach {
                        Log.d("asdf irdb_projector_key" , "${it}")
                        irdb_projector_model_key_list.add(it)
                    }
                    val irdb_projector_json = JSONObject(irdb_projector)
                    val irdb_projector_modle1 = irdb_projector_json.getString("${irdb_projector_model_key_list.get(0)}")
                    val irdb_projector_modle2 = irdb_projector_json.getString("${irdb_projector_model_key_list.get(1)}")
                    Log.d("asdf irdb_projector_modle1", "${irdb_projector_modle1}")
                    Log.d("asdf irdb_projector_modle2", "${irdb_projector_modle2}")

                    val order_projector =
                        "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_projector)
                    Thread.sleep(200)

                    for(i in 0 until irdb_projector_model_key_list.size) {
                        val irdb_projector_modle = irdb_projector_json.getString("${irdb_projector_model_key_list.get(i)}")
                        val order_projector_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${irdb_projector_model_key_list.get(i)}\", \"value\": ${irdb_projector_modle}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_model)
                        Thread.sleep(100)
                    }

//                    한번에 보내는거
//                    val send_projector = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector\", \"value\": ${irdb_projector}}], \"id\": \"qwerasd$random\"}"
//                    sendCommand(send_projector)
//                    Thread.sleep(200)

                    // lighting ==========================================================================================================================================================

                    val irdb_lighting = irdb.getString("lighting")
                    Log.d("asdf irdb_lighting", irdb_lighting)
                    val irdb_lighting_key = JSONObject(irdb_lighting)
                    val irdb_lighting_model_key_list = mutableListOf<String>()
                    irdb_lighting_key.keys().forEach {
                        Log.d("asdf irdb_lighting_key" , "${it}")
                        irdb_lighting_model_key_list.add(it)
                    }
                    val irdb_lighting_json = JSONObject(irdb_lighting)
                    val irdb_lighting_modle1 = irdb_lighting_json.getString("${irdb_lighting_model_key_list.get(0)}")
                    val irdb_lighting_modle2 = irdb_lighting_json.getString("${irdb_lighting_model_key_list.get(1)}")
                    Log.d("asdf irdb_lighting_modle1", "${irdb_lighting_modle1}")
                    Log.d("asdf irdb_lighting_modle2", "${irdb_lighting_modle2}")

                    val order_lighting =
                        "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_lighting)
                    Thread.sleep(200)

//                    한번에 보내는거
//                    val order_lighting = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting\", \"value\": ${irdb_lighting}}], \"id\": \"qwerasd$random\"}"
//                    sendCommand(order_lighting)
//                    Thread.sleep(200)

                    for(i in 0 until irdb_lighting_model_key_list.size) {
                        val irdb_lighting_modle = irdb_lighting_json.getString("${irdb_lighting_model_key_list.get(i)}")
                        val order_lighting_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_model_key_list.get(i)}\", \"value\": ${irdb_lighting_modle}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_lighting_model)
                        Thread.sleep(100)
                    }

                    // air_conditioner ==========================================================================================================================================================

                    val irdb_aircon = irdb.getString("air_conditioner")
                    Log.d("asdf irdb_aircon", irdb_aircon)
                    val irdb_aircon_key = JSONObject(irdb_aircon)
                    val irdb_aircon_model_key_list = mutableListOf<String>()
                    irdb_aircon_key.keys().forEach {
                        Log.d("asdf irdb_aircon_key" , "${it}")
                        irdb_aircon_model_key_list.add(it)
                    }
                    val irdb_aircon_json = JSONObject(irdb_aircon)
                    val irdb_aircon_modle1 = irdb_aircon_json.getString("${irdb_aircon_model_key_list.get(0)}")
                    val irdb_aircon_modle2 = irdb_aircon_json.getString("${irdb_aircon_model_key_list.get(1)}")
                    Log.d("asdf irdb_aircon_modle1", "${irdb_aircon_modle1}")
                    Log.d("asdf irdb_aircon_modle2", "${irdb_aircon_modle2}")

                    val order_aircon =
                        "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_aircon)
                    Thread.sleep(200)

//                    한번에 보내는거
//                    val order_aircon = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner\", \"value\": ${irdb_aircon}}], \"id\": \"qwerasd$random\"}"
//                    sendCommand(order_aircon)
//                    Thread.sleep(200)

                    for(i in 0 until irdb_aircon_model_key_list.size) {
                        val irdb_aircon_modle = irdb_aircon_json.getString("${irdb_aircon_model_key_list.get(i)}")
                        val order_aircon_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner/${irdb_aircon_model_key_list.get(i)}\", \"value\": ${irdb_aircon_modle}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_aircon_model)
                        Thread.sleep(100)
                    }

                }

                editDataList.clear()
                spinnerDataList.clear()

                Toast.makeText(this,"전송 완료",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }

        // 연결버튼 누르면 연결 페이지로 이동 / 만약 이미 연결되어 있으면 기존 연결 끊
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

        // 게이트웨이에 있는 값을 명령어로 불러오는 기능
        binding.btnLoad.setOnClickListener {
            // load 버튼을 누르면 sharedPreferences 생성 true/false로 클릭여부 확인하여 만약 클릭하면 룸에다가 가져온 정보 보여줌
            val sharedPreferences = getSharedPreferences("LoadToF", Context.MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreferences.edit()

            // 연결되었으면 실행
            if (bluetoothSocket?.isConnected == true) {
                Toast.makeText(this, "load",Toast.LENGTH_SHORT).show()
                //val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_1/units/1\"}, \"id\": \"ro33b7sz\"}"

                // 방 개수만큼 blaster/node_id를 구하는 sendCommand로 명령어 보내고 receiveData로 불러오기
               for(i in 1..roomList.size) {
                    val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/blaster/node_id\"}, \"id\": \"qwerasd$random\"}"
                    sendCommand(loadJson)
                    receiveData()
                    val jsonObject = JSONObject(readMessage)
                    //Log.d("asdf jsonobject", "${jsonObject}")
                    val result = jsonObject.get("result")
                    Log.d("asdf room${i}_blaster", result.toString())

                    val sharedPreferences = getSharedPreferences("load$i", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("load_blaster_$i",result.toString())

                    editor.commit()
                }

                // 2중 for문으로 룸 개수와 디바이스 개수만큼 반복해서 plug/nid를 찾는 명령어를 sendCommand로 보내고 receiveData응답을 받는다
                for (i in 1..roomList.size) {
                    for (z in 1 until 7) {
                        if(z != 3 && z != 6) {
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$z/plug/nid\"}, \"id\": \"qwerasd$random\"}"
                            sendCommand(loadJson)
                            receiveData()
                            val jsonObject = JSONObject(readMessage)
                            //Log.d("asdf jsonobject", "${jsonObject}")
                            val result = jsonObject.get("result")
                            Log.d("asdf room${i}_nid$z", result.toString())

                            val sharedPreferences = getSharedPreferences("load$i", Context.MODE_PRIVATE)
                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("load_nid_$z",result.toString())

                            editor.commit()

                        }
                    }
                }

                // 2중 for문으로 룸 개수와 디바이스 개수만큼 반복해서 plug/use_switch를 찾는 명령어를 sendCommand로 보내고 receiveData응답을 받는다
                for (i in 1..roomList.size) {
                    for (z in 1 until 7) {
                        if(z != 3 && z != 6) {
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$z/plug/use_switch\"}, \"id\": \"qwerasd$random\"}"
                            sendCommand(loadJson)
                            receiveData()
                            val jsonObject = JSONObject(readMessage)
                            //Log.d("asdf jsonobject2", "${jsonObject2}")
                            val result = jsonObject.get("result")
                            Log.d("asdf room${i}_use_switch$z", result.toString())

                        }
                    }
                }

                // 2중 for문으로 룸 개수와 디바이스 개수만큼 반복해서 blaster/ir_key를 찾는 명령어를 sendCommand로 보내고 receiveData응답을 받는다
                for (i in 1..roomList.size) {
                    for (z in 1 until 7) {
                        if(z == 3 || z == 6 || z == 4 || z == 5) {
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$z/blaster/ir_key\"}, \"id\": \"qwerasd$random\"}"
                            sendCommand(loadJson)
                            receiveData()
                            val jsonObject = JSONObject(readMessage)
                            //Log.d("asdf jsonobject2", "${jsonObject2}")
                            val result = jsonObject.get("result")
                            Log.d("asdf room${i}_ir_key$z", result.toString())

                            val sharedPreferences = getSharedPreferences("load$i", Context.MODE_PRIVATE)
                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("load_ir_key_$z",result.toString())

                            editor.commit()

                        }
                    }
                }

                // load 클릭해서 쉐어드 프리퍼런스에 true 입
                editor.putBoolean("btnLoadClick",true)
                editor.commit()

                Toast.makeText(this,"load 완료",Toast.LENGTH_SHORT).show()

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

    // 보낸 값의 응답을 받는 함수다 inputStream을 이용해 응답을 받는다?
    private fun receiveData() {
        try {

//            val byteAvailable = bluetoothSocket!!.inputStream.available()
//            //val readColor = ByteArray(832)
//            val readColor = ByteArray(byteAvailable)
//            bluetoothSocket!!.inputStream.read(readColor)
//            val string = String(readColor)
//            Log.d("asdf receiveData", "${string}")

            val buffer = ByteArray(1024)
            var bytes = bluetoothSocket!!.inputStream.read(buffer)
            readMessage = String(buffer, 0, bytes)
            //loadJsonString = loadJsonString + readMessage
            Log.d("asdf receiveData", "${readMessage}")

        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("asdf receiveData", "No receiveData")
        }

    }

    // 블루투스로 값을 보내는 함수 outputStream을 이용한다 용량이 작아서 큰거는 나눠서 보내야 한다
    private fun sendCommand(input: String) {
        if(bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(input.toByteArray())

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    // 블루투스 연결해제 하는 함수
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

    // 모든 룸에 있는 데이터를 지워주는 함수다
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
























