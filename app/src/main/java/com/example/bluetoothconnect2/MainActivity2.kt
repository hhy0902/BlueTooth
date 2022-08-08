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
import com.example.bluetoothconnect2.adapter.RoomAdapter
import com.example.bluetoothconnect2.databinding.ActivityMain2Binding
import com.example.bluetoothconnect2.model.Room
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*

class MainActivity2 : AppCompatActivity() {

    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    var i = 1 // recyclerVIew +-에 사용되는 변수입니다.

    lateinit var roomList : MutableList<Room>

    var address = ""
    var name = ""
    var roomName = ""
    val random = Random().nextInt(9)+1 // send명령어 보낼때 적어주는 id에 사용되는 변수입니다.

    var loadJsonString = ""

    lateinit var readMessage : String

    lateinit var sharedPreferences : SharedPreferences

    companion object {
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomList = mutableListOf<Room>()
        roomList.add(Room("room 1"))

        binding.gateWayText.text = "$address $name"

        // 룸 추가한거 유지를 위해 ActivityResultLauncher 사용?
        val startActivityLauncher : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
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

            Log.d("asdf 1", isConnected.toString())
            Log.d("asdf 2", bluetoothSocket?.isConnected.toString())

            if (bluetoothSocket?.isConnected == true) {

                // 룸1부터 10까지 초기화
                for (p in 1..10) {
                    val testDelete3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/room_$p\"}],\"id\":\"qwerasd$random\"}"
                    sendCommand(testDelete3)
                    receiveData()
                    //Thread.sleep(200)
                }

                //irdb 초기화
                val testDelete4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"remove\",\"path\":\"/irdb\"}],\"id\":\"qwerasd$random\"}"
                sendCommand(testDelete4)
                receiveData()
                //Thread.sleep(200)

                val numList = mutableListOf<Int>(0, 7, 3, 1, 9, 10, 6, 2)
                //신버전 : 0=ir블래스터 더미 값, 1=조명, 2=터치스크린, 3=스피커, 6=에어컨, 7=pc, 9=프로젝터1, 10=프로젝터2

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

                    jsonrpcObject.put("jsonrpc","2.0")
                    jsonrpcObject.put("method","patch_gz")
                    jsonrpcArrayObject.put("op","add")
                    jsonrpcArrayObject.put("path","/room_$i")
                    jsonrpcValue2.put("nid",sharedPreferences.all.get("editdata1").toString().toInt())
                    jsonrpcValue.put("blaster",jsonrpcValue2)

                    for(z in 1 until 8) {
                        var jsonrpcUnitsValue2 = JSONObject()
                        var jsonrpcPlugValue = JSONObject()

                        // 1=pc, 2=스피커, 3=조명, 4=프로젝터1, 5=프로젝터2, 6=에어컨 7=터치스크린 추가하기
                        // 스피커 값 if 문에 들어가는거 변경해주기

                        if(z == 4 || z == 5) {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        } else if(z == 1 || z == 7)   {
                            jsonrpcUnitsValue2.put("plug", jsonrpcPlugValue)
                        } else if(z == 6 || z == 3 || z == 2 ) {
                            jsonrpcUnitsValue2.put("blaster", jsonrpcPlugValue)
                        }
                        val temp = numList[z]
                        jsonrpcUnitsValue.put("$temp", jsonrpcUnitsValue2)
                    }

                    jsonrpcValue.put("units", jsonrpcUnitsValue)
                    jsonrpcArrayObject.put("value",jsonrpcValue)
                    jsonrpcArray.put(jsonrpcArrayObject)
                    jsonrpcObject.put("params",jsonrpcArray)
                    jsonrpcObject.put("id","qwerasd$random")

                    sendCommand(jsonrpcObject.toString())
                    receiveData()
                    Log.d("asdf jsonrpcobject", jsonrpcObject.toString())
                    //Thread.sleep(200)
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 plug/nid만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 8) {
                        if (i != 3 && i != 6 && i !=2) {
                            val jsonrpcObject_2 = JSONObject()
                            val jsonrpcArray_2 = JSONArray()
                            val jsonrpcArrayObject_2 = JSONObject()

                            val temp = numList[i]
                            jsonrpcObject_2.put("jsonrpc", "2.0")
                            jsonrpcObject_2.put("method", "patch_gz")
                            jsonrpcArrayObject_2.put("op", "add")
                            jsonrpcArrayObject_2.put("path", "/room_$q/units/$temp/plug/nid")
                            jsonrpcArrayObject_2.put("value", sharedPreferences.all.get("editdata${i+1}").toString().toInt())
                            jsonrpcArray_2.put(jsonrpcArrayObject_2)
                            jsonrpcObject_2.put("params", jsonrpcArray_2)
                            jsonrpcObject_2.put("id", "qwerasd$random")
                            sendCommand(jsonrpcObject_2.toString())
                            receiveData()

                            //Thread.sleep(100)
                            Log.d("asdf jsonrpcobject_node_id_${q}_$i", jsonrpcObject_2.toString())
                        }
                    }
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 plug/use_switch만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 8) {
                        if (i != 3 && i != 6 && i != 2) {
                            val jsonrpcObject_2 = JSONObject()
                            val jsonrpcArray_2 = JSONArray()
                            val jsonrpcArrayObject_2 = JSONObject()
                            val temp = numList[i]
                            jsonrpcObject_2.put("jsonrpc", "2.0")
                            jsonrpcObject_2.put("method", "patch_gz")
                            jsonrpcArrayObject_2.put("op", "add")
                            jsonrpcArrayObject_2.put("path", "/room_$q/units/$temp/plug/use_switch")

                            if (i == 1) {
                                jsonrpcArrayObject_2.put("value", true)
                            } else {
                                jsonrpcArrayObject_2.put("value", false)
                            }

                            jsonrpcArray_2.put(jsonrpcArrayObject_2)
                            jsonrpcObject_2.put("params", jsonrpcArray_2)
                            jsonrpcObject_2.put("id", "qwerasd$random")
                            sendCommand(jsonrpcObject_2.toString())
                            receiveData()
                            //Thread.sleep(100)

                        }

                    }
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 plug/soft_turnoff만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 8) {
                        if (i == 1 || i == 4 || i == 5) {
                            val jsonrpcObject_2 = JSONObject()
                            val jsonrpcArray_2 = JSONArray()
                            val jsonrpcArrayObject_2 = JSONObject()

                            val temp = numList[i]
                            jsonrpcObject_2.put("jsonrpc", "2.0")
                            jsonrpcObject_2.put("method", "patch_gz")
                            jsonrpcArrayObject_2.put("op", "add")
                            jsonrpcArrayObject_2.put("path", "/room_$q/units/$temp/plug/soft_turnoff")
                            jsonrpcArrayObject_2.put("value", "true")
                            jsonrpcArray_2.put(jsonrpcArrayObject_2)
                            jsonrpcObject_2.put("params", jsonrpcArray_2)
                            jsonrpcObject_2.put("id", "qwerasd$random")
                            sendCommand(jsonrpcObject_2.toString())
                            receiveData()

                            //Thread.sleep(100)
                            Log.d("asdf jsonrpcobject_node_id_${q}_$i", jsonrpcObject_2.toString())
                        }
                    }
                }

                // sendCommand로 데이터 보내는데 블루투스 용량 때문에 나눠서 보냄 여기는 blaster/ir_key만 만들어서 보내줌
                for(q in 1..roomList.size) {
                    sharedPreferences = getSharedPreferences("$q", Context.MODE_PRIVATE)
                    for (i in 1 until 8) {
                        if(i == 2 || i == 3 || i == 6 || i == 4 || i == 5) {
                            val jsonrpcObject_2 = JSONObject()
                            val jsonrpcArray_2 = JSONArray()
                            val jsonrpcArrayObject_2 = JSONObject()

                            val temp = numList[i]

                            jsonrpcObject_2.put("jsonrpc", "2.0")
                            jsonrpcObject_2.put("method", "patch_gz")
                            jsonrpcArrayObject_2.put("op", "add")
                            jsonrpcArrayObject_2.put("path", "/room_$q/units/$temp/blaster/ir_key")
                            jsonrpcArrayObject_2.put("value", "/${sharedPreferences.all.get("spinnerData${i+1}_Device")}/${sharedPreferences.all.get("spinnerData${i+1}_Company")}/${sharedPreferences.all.get("spinnerData${i+1}_Model")}")
                            //jsonrpcArrayObject_2.put("value", "projector/maxell/mp-eu5002")
                            jsonrpcArray_2.put(jsonrpcArrayObject_2)
                            jsonrpcObject_2.put("params", jsonrpcArray_2)
                            jsonrpcObject_2.put("id", "qwerasd$random")
                            sendCommand(jsonrpcObject_2.toString())
                            receiveData()
                            //Thread.sleep(100)
                            Log.d("asdf jsonrpcobject_ir_key_${q}_$i", jsonrpcObject_2.toString())

                        }
                    }
                }

                for(i in 1..1) {

                    val order = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order)
                    receiveData()
                    //Thread.sleep(100)

                    val order_speaker = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/speaker\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_speaker)
                    receiveData()
                    //Thread.sleep(100)

                    val order_projector = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_projector)
                    receiveData()
                    //Thread.sleep(100)

                    val order_lighting = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lamp\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_lighting)
                    receiveData()
                    //Thread.sleep(100)

                    val order_aircon = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                    sendCommand(order_aircon)
                    receiveData()
                    //Thread.sleep(100)


                    // base ==========================================================================================================================================================
                    val irdbString = assets.open("irdbV2.json").reader().readText()
                    val irdb = JSONObject(irdbString)
                    val irdb_lighting_company_key_list = mutableListOf<String>()
                    val irdb_projector_company_key_list = mutableListOf<String>()
                    val irdb_air_conditioner_company_key_list = mutableListOf<String>()
                    val irdb_speaker_company_key_list = mutableListOf<String>()

                    val irdb_lighting = irdb.getString("lamp")
                    val irdb_lighting_key = JSONObject(irdb_lighting)

                    val irdb_projector = irdb.getString("projector")
                    val irdb_projector_key = JSONObject(irdb_projector)

                    val irdb_air_conditioner = irdb.getString("air_conditioner")
                    val irdb_air_conditioner_key = JSONObject(irdb_air_conditioner)

                    val irdb_speaker = irdb.getString("speaker")
                    val irdb_speaker_key = JSONObject(irdb_speaker)

                    irdb_speaker_key.keys().forEach {
                        Log.d("asdf irdb_speaker_company_key_list", "${it}")
                        irdb_speaker_company_key_list.add(it)
                    }

                    irdb_lighting_key.keys().forEach {
                        Log.d("asdf irdb_lighting_company_key_list", "${it}")
                        irdb_lighting_company_key_list.add(it)
                    }

                    irdb_projector_key.keys().forEach {
                        Log.d("asdf irdb_projector_company_key_list", "${it}")
                        irdb_projector_company_key_list.add(it)
                    }

                    irdb_air_conditioner_key.keys().forEach {
                        Log.d("asdf irdb_air_conditioner_company_key_list", "${it}")
                        irdb_air_conditioner_company_key_list.add(it)
                    }

                    for (i in 1..irdb_speaker_company_key_list.size) {
                        val order_speaker_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/speaker/${irdb_speaker_company_key_list.get(i-1)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_speaker_address)
                        receiveData()
                        //Thread.sleep(100)
                    }
                    for (i in 1..irdb_air_conditioner_company_key_list.size) {
                        val order_aircon_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner/${irdb_air_conditioner_company_key_list.get(i-1)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_aircon_address)
                        receiveData()
                        //Thread.sleep(100)
                    }
                    for (i in 1..irdb_projector_company_key_list.size) {
                        val order_projector_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${irdb_projector_company_key_list.get(i-1)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_address)
                        receiveData()
                        //Thread.sleep(100)
                    }
                    for (i in 1..irdb_lighting_company_key_list.size) {
                        val order_lighting_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lamp/${irdb_lighting_company_key_list.get(i-1)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_lighting_address)
                        receiveData()
                        //Thread.sleep(100)
                    }

                    // lighting ==========================================================================================================================================================
                    for(i in 1..roomList.size) {
                        sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                        val spinnerData4_Company = sharedPreferences.getString("spinnerData4_Company", "")
                        val spinnerData4_Model = sharedPreferences.getString("spinnerData4_Model", "")

                        val tempLighting = JSONObject(irdb.getString("lamp").toString())
                        val tempLightingData = JSONObject(tempLighting.getString("${spinnerData4_Company}"))
                        val tempLightingModelData = tempLightingData.getString("${spinnerData4_Model}")
                        Log.d("asdf tempLightingModelData", tempLightingModelData)
                        // extractedDB 에다가 "lighting" + spinnerData4_Company + spinnerData4_Model, tempData 저

                        val order_lighting_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lamp/${spinnerData4_Company}/${spinnerData4_Model}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_lighting_address)
                        receiveData()
                        //Thread.sleep(100)
                        Log.d("asdf order_lighting_deep_address", "${order_lighting_address}")

                        val order_lighting_value = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lamp/${spinnerData4_Company}/${spinnerData4_Model}\", \"value\": ${tempLightingModelData}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_lighting_value)
                        receiveData()
                        //Thread.sleep(100)
                        Log.d("asdf order_lighting_value", "${order_lighting_value}")
                    }

                    // projector ==========================================================================================================================================================
                    for(i in 1..roomList.size) {
                        sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                        val spinnerData5_Company = sharedPreferences.getString("spinnerData5_Company", "")
                        val spinnerData5_Model = sharedPreferences.getString("spinnerData5_Model", "")

                        val spinnerData6_Company = sharedPreferences.getString("spinnerData6_Company", "")
                        val spinnerData6_Model = sharedPreferences.getString("spinnerData6_Model", "")

                        val tempProjector = JSONObject(irdb.getString("projector").toString())
                        val tempProjectorData = JSONObject(tempProjector.getString("${spinnerData5_Company}"))
                        val tempProjectorModelData = tempProjectorData.getString("${spinnerData5_Model}")

                        val tempProjectorData2 = JSONObject(tempProjector.getString("${spinnerData6_Company}"))
                        val tempProjectorModelData2 = tempProjectorData2.getString("${spinnerData6_Model}")

                        val order_projector_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${spinnerData5_Company}/${spinnerData5_Model}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_address)
                        receiveData()
                        //Thread.sleep(100)

                        val order_projector_value = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${spinnerData5_Company}/${spinnerData5_Model}\", \"value\": ${tempProjectorModelData}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_value)
                        receiveData()
                        //Thread.sleep(100)

                        val order_projector_address_2 = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${spinnerData6_Company}/${spinnerData6_Model}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_address_2)
                        receiveData()
                        //Thread.sleep(100)

                        val order_projector_value_2 = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/projector/${spinnerData6_Company}/${spinnerData6_Model}\", \"value\": ${tempProjectorModelData2}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_projector_value_2)
                        receiveData()
                        //Thread.sleep(100)
                    }

                    // aircon =========================================================================================================================================================
                    for(i in 1..roomList.size) {
                        sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                        val spinnerData7_Company = sharedPreferences.getString("spinnerData7_Company", "")
                        val spinnerData7_Model = sharedPreferences.getString("spinnerData7_Model", "")

                        val tempAircon = JSONObject(irdb.getString("air_conditioner").toString())
                        val tempAirconData = JSONObject(tempAircon.getString("${spinnerData7_Company}"))
                        val tempAirconModelData = tempAirconData.getString("${spinnerData7_Model}")

                        val order_aircon_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner/${spinnerData7_Company}/${spinnerData7_Model}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_aircon_address)
                        receiveData()
                        //Thread.sleep(100)

                        val order_aircon_value = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/air_conditioner/${spinnerData7_Company}/${spinnerData7_Model}\", \"value\": ${tempAirconModelData}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_aircon_value)
                        receiveData()
                        //Thread.sleep(100)
                    }

                    // speaker ==========================================================================================================================================================
                    for(i in 1..roomList.size) {
                        sharedPreferences = getSharedPreferences("$i", Context.MODE_PRIVATE)

                        val spinnerData3_Company = sharedPreferences.getString("spinnerData3_Company", "")
                        val spinnerData3_Model = sharedPreferences.getString("spinnerData3_Model", "")

                        val tempSpeaker = JSONObject(irdb.getString("speaker").toString())
                        val tempSpeakerData = JSONObject(tempSpeaker.getString("${spinnerData3_Company}"))
                        val tempSpeakerModelData = tempSpeakerData.getString("${spinnerData3_Model}")
                        Log.d("asdf tempSpeakerModelData", tempSpeakerModelData)

                        val order_speaker_address = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/speaker/${spinnerData3_Company}/${spinnerData3_Model}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_speaker_address)
                        receiveData()
                        //Thread.sleep(100)
                        Log.d("asdf order_speaker_deep_address", "${order_speaker_address}")

                        val order_speaker_value = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/speaker/${spinnerData3_Company}/${spinnerData3_Model}\", \"value\": ${tempSpeakerModelData}}], \"id\": \"qwerasd$random\"}"
                        sendCommand(order_speaker_value)
                        receiveData()
                        //Thread.sleep(100)
                        Log.d("asdf order_speaker_value", "${order_speaker_value}")
                    }

                }

                val commit = "{\"jsonrpc\": \"2.0\", \"method\": \"commit_gz\", \"id\": \"qwerasd$random\"}"
                sendCommand(commit)
                receiveData()

                Toast.makeText(this,"commit 완료",Toast.LENGTH_SHORT).show()

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
            val numList = mutableListOf<Int>(0, 7, 3, 1, 9, 10, 6, 2)
            //신버전 : 0=ir블래스터 더미 값, 1=조명, 2=터치스크린, 3=스피커, 6=에어컨, 7=pc, 9=프로젝터1, 10=프로젝터2

            // 연결되었으면 실행
            if (bluetoothSocket?.isConnected == true) {
                Toast.makeText(this, "load",Toast.LENGTH_SHORT).show()
                //val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_1/units/1\"}, \"id\": \"ro33b7sz\"}"

                // 방 개수만큼 blaster/node_id를 구하는 sendCommand로 명령어 보내고 receiveData로 불러오기
                for(i in 1..roomList.size) {
                    val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/blaster/nid\"}, \"id\": \"qwerasd$random\"}"
                    sendCommand(loadJson)
                    receiveData()
                    val jsonObject = JSONObject(readMessage)
                    Log.d("asdf jsonobject", "${jsonObject}")
                    val result = jsonObject.get("result")
                    Log.d("asdf room${i}_blaster", result.toString())

                    val sharedPreferences = getSharedPreferences("load$i", Context.MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("load_blaster_$i",result.toString())

                    editor.commit()
                }

                // 2중 for문으로 룸 개수와 디바이스 개수만큼 반복해서 plug/nid를 찾는 명령어를 sendCommand로 보내고 receiveData응답을 받는다
                for (i in 1..roomList.size) {
                    for (z in 1 until 8) {
                        if(z != 3 && z != 6 && z != 2) {
                            val temp = numList[z]
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$temp/plug/nid\"}, \"id\": \"qwerasd$random\"}"
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
                    for (z in 1 until 8) {
                        if(z != 3 && z != 6 && z != 2) {
                            val temp = numList[z]
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$temp/plug/use_switch\"}, \"id\": \"qwerasd$random\"}"
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
                    for (z in 1 until 8) {
                        if(z == 2 || z == 3 || z == 6 || z == 4 || z == 5) {
                            val temp = numList[z]
                            val loadJson : String = "{\"jsonrpc\": \"2.0\", \"method\": \"get_gz\", \"params\": {\"path\": \"/room_$i/units/$temp/blaster/ir_key\"}, \"id\": \"qwerasd$random\"}"
                            sendCommand(loadJson)
                            receiveData()
                            val jsonObject = JSONObject(readMessage)
                            //Log.d("asdf jsonobject2", "${jsonObject2}")
                            val result = jsonObject.get("result")
                            Log.d("asdf room${i}_ir_key_$z", result.toString())

                            val sharedPreferences = getSharedPreferences("load$i", Context.MODE_PRIVATE)
                            val editor : SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("load_ir_key_$z",result.toString())

                            editor.commit()

                        }
                    }
                }

                // load 클릭해서 쉐어드 프리퍼런스에 true 입력
                editor.putBoolean("btnLoadClick",true)
                editor.commit()

                Toast.makeText(this,"load 완료",Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 보낸 값의 응답을 받는 함수다 inputStream을 이용해 응답을 받는다?
    private fun receiveData() {
        try {

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



















































