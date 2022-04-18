package com.example.bluetoothconnect2

import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
    lateinit var roomList : MutableList<Room>

    var spinnerData = ""
    var editData = ""

    /*
    {"jsonrpc":"2.0","method":"gz_patch", "params": "op": "replace", "path": "/plugs", "value": {여기에 플러그?}}
     */

    /*
    {
  "plugs": [
    {
      "node_id": 1234567,
      "room_id": 1,
      "unit_id": 1, = 제품 종류?
      "value?" : "qwerasdf"
      "use_blaster": true
    },
    {
      "node_id": 1234568,
      "room_id": 1,
      "unit_id": 2,
      "use_blaster": false
    }
  ],
  "blaster": {
    "1": {
      "type": 6,
      "opcode": [
    {
      "cmd": "on",
      "code": 1234545666
    },
    {
      "cmd": "off",
       "code": 123123123
    }
      ]
    }
  }
}
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        roomList = mutableListOf<Room>()
        roomList.add(Room("room 1"))

        val startActivityLauncher : ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                when(it.resultCode) {
                    RESULT_OK -> {
                        Log.d("asdf", it.data?.getStringExtra("roomSizeValue").toString())
                        spinnerData = it.data?.getStringExtra("spinner1").toString()
                        editData = it.data?.getStringExtra("editdata1").toString()
                    }
                }
            }

        val roomAdapter = RoomAdapter(this,roomList, startActivityLauncher)
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
            Log.d("asdf", bluetoothSocket?.isConnected.toString())
            if (isConnected) {
                sendCommand(spinnerData + editData)
            }
            else {
                Toast.makeText(this,"연결이 필요합니다",Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDisconnect.setOnClickListener {
            disconnect()
        }

        address = intent.getStringExtra("Device_address").toString()
        name = intent.getStringExtra("Device_name").toString()

        binding.gateWayText.text = "$address \n$name"

        // ConnectToDevice(this, binding.checkLed).execute()

//        if(isConnected == false) {
//            Toast.makeText(applicationContext,"연결 해제",Toast.LENGTH_SHORT).show()
//            bluetoothSocket!!.close()
//        }


        Thread {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
            bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
            BluetoothAdapter.getDefaultAdapter().cancelDiscovery()

            try {
                bluetoothSocket?.connect()
                isConnected = true

                runOnUiThread {
                    Toast.makeText(this, "연결 성공", Toast.LENGTH_SHORT).show()
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

    companion object {
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var progress: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        var isConnected: Boolean = false
        lateinit var address: String
        lateinit var name: String
    }
}
























