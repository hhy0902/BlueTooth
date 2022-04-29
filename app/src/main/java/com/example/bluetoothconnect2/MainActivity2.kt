package com.example.bluetoothconnect2

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
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
import java.nio.Buffer
import java.util.*

class MainActivity2 : AppCompatActivity() {


    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    var i = 1
    var z = 0
    lateinit var roomList : MutableList<Room>
    var stringBuffer = StringBuffer()

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

    val handler = Handler()

    var address = ""
    var name = ""
    var roomName = ""

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
                for (i in 0..9) {
                    val tempJsonObject = JSONObject()
                    tempJsonObject.put("node_id", editDataList[i])
                    //tempJsonObject.put("room_id", i+1)
                    tempJsonObject.put("room_id", roomName)
                    tempJsonObject.put("unit_id", spinnerDataList[i])
                    tempJsonObject.put("use_blaster", true)
                    jsonObjectList.put(tempJsonObject)
                }
                jsonObject.put("jsonrpc", "2.0")
                jsonObject.put("method", "gz_patch")

                val jsonObjectParams = JSONObject()
                jsonObjectParams.put("op","replace")
                jsonObjectParams.put("path","/plugs")
                jsonObjectList2.put(jsonObjectParams)

                jsonObject.put("params", jsonObjectList2)
                jsonObject.put("value",jsonObjectList)

                Log.d("asdf jsonobject", jsonObject.toString())
                Log.d("asdf jsonobject", json)

                sendCommand(jsonObject.toString())

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
            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }

        // ConnectToDevice(this, binding.checkLed).execute()

//        if(isConnected == false) {
//            Toast.makeText(applicationContext,"연결 해제",Toast.LENGTH_SHORT).show()
//            bluetoothSocket!!.close()
//        }



//        val hadlerTask = object : Runnable {
//            override fun run() {
//                Log.d("asdf", "-----")
//                Log.d("asdf", name)
//                Log.d("asdf", address)
//                Log.d("asdf", isRealConnect.toString())
//                binding.gateWayText.text = "$name $address"
//
//                handler.postDelayed(this, 2000)
//            }
//        }
//        handler.post(hadlerTask)


    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)

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
        Thread {
            try {
                val byteAvailable = bluetoothSocket!!.inputStream.available()

                if(byteAvailable > 0) {

                    var byte = bluetoothSocket!!.inputStream.read()

                    //val readColor = ByteArray(832)
                    val readColor = ByteArray(byteAvailable-2)

                    bluetoothSocket!!.inputStream.read(readColor)

                    val string = String(readColor)
                    val test = string.length

                    Log.d("asdf receiveData2", "${byteAvailable}")
                    Log.d("asdf receiveData3", "${bluetoothSocket!!.inputStream.read()}")
                    Log.d("asdf receiveData4", "${byte}")
                    Log.d("asdf receiveData7", "${string}")
                    Log.d("asdf receiveData8", "${test}")

                }
            } catch (e : IOException) {
                e.printStackTrace()
                Log.d("asdf receiveData", "No receiveData")
            }

        }.start()
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



//    private class ConnectToDevice(c: Context, val checkLed : ImageView) : AsyncTask<Void, Void, String>() {
//
//        private var connectSuccess: Boolean = true
//        private val context: Context
//
//        init {
//            this.context = c
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//            //Toast.makeText(context,"${name} 연결 실패2", Toast.LENGTH_SHORT).show()
//            progress = ProgressDialog.show(context,"connecting","please wait")
//            //Toast.makeText(context,"${name} 연결 성공", Toast.LENGTH_SHORT).show()
//        }
//
//        override fun doInBackground(vararg p0: Void?): String? {
//
//            try {
//                if(bluetoothSocket == null || !isConnected) {
//                    //bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
//                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
//                    val device : BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
//                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
//                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
//                    bluetoothSocket!!.connect()
//                } else if(connectSuccess == false) {
//                    //checkLed.setImageResource(R.drawable.ic_baseline_circle_red_24)
//                    Toast.makeText(context,"${name} 연결 해제", Toast.LENGTH_SHORT).show()
//                }
//            } catch (e:IOException) {
//                connectSuccess = false
//                e.printStackTrace()
//                //checkLed.setImageResource(R.drawable.ic_baseline_circle_red_24)
//                Toast.makeText(context,"${name} 연결 실패3", Toast.LENGTH_SHORT).show()
//            }
//            return null
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            if(!connectSuccess) {
//                Log.d("data","couldn't connect")
//                checkLed.setImageResource(R.drawable.ic_baseline_circle_red_24)
//            } else {
//                isConnected = true
//            }
//            progress.dismiss()
//        }
//
//        override fun onCancelled() {
//            Toast.makeText(context,"${name} 연결 실패4", Toast.LENGTH_SHORT).show()
//            checkLed.setImageResource(R.drawable.ic_baseline_circle_red_24)
//
//        }
//    }
//

}
























