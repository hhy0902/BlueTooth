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
import java.io.IOException
import java.util.*

class MainActivity2 : AppCompatActivity() {


    private val binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }

    var i = 1
    var z = 0
    lateinit var roomList : MutableList<Room>

    var spinnerData = ""
    var editData = ""
    val handler = Handler()
    var isRealConnect = false
    var address = ""
    var name = ""

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

        binding.gateWayText.text = "$address $name"

        val startActivityLauncher : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        spinnerData = it.data?.getStringExtra("spinner1").toString()
                        editData = it.data?.getStringExtra("editdata1").toString()
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
            binding.dataText.text = spinnerData
            binding.dataText2.text = editData

            Log.d("asdf 1", isConnected.toString())
            Log.d("asdf 2", bluetoothSocket?.isConnected.toString())

            if (bluetoothSocket?.isConnected == true) {
                sendCommand("{\"jsonrpc\": \"2.0\",\"method\": \"gz_patch\",\"params\": [{\"op\": \"replace\",\"path\": \"/plugs\"}],\"value\": [{\"node_id\": 1234567,\"room_id\": 1,\"unit_id\": 1,\"use_blaster\": true},{\"node_id\": 1234568,\"room_id\": 1,\"unit_id\": 2,\"use_blaster\": false}]}")
                sendCommand(spinnerData + editData)
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
            //disconnect()
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
























