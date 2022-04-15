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
    {
  "plugs": [
    {
      "node_id": 1234567,
      "room_id": 1,
      "unit_id": 1,
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
            sendCommand(spinnerData)
        }

        binding.btnDisconnect.setOnClickListener {
            disconnect()
        }

        address = intent.getStringExtra("Device_address").toString()
        name = intent.getStringExtra("Device_name").toString()

        binding.gateWayText.text = "$address \n$name"

        ConnectToDevice(this).execute()

//        control_led_on.setOnClickListener {
//            sendCommand(editText.text.toString())
//            editText.text = null
//        }

//        control_led_disconnect.setOnClickListener {
//            disconnect()
//        }
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
                isConnected = false
                Toast.makeText(applicationContext,"연결 해제",Toast.LENGTH_SHORT).show()
            }catch (e: IOException) {
                e.printStackTrace()
            }
        }
        finish()
    }

    private class ConnectToDevice(c: Context) : AsyncTask<Void, Void, String>() {

        private var connectSuccess: Boolean = true
        private val context: Context

        init {
            this.context = c
        }

        override fun onPreExecute() {
            super.onPreExecute()
            progress = ProgressDialog.show(context,"connecting","please wait")
            Toast.makeText(context,"${name} 연결 성공", Toast.LENGTH_SHORT).show()
        }

        override fun doInBackground(vararg p0: Void?): String? {

            try {
                if(bluetoothSocket == null || !isConnected) {
                    //bluetoothManager = context.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                    val device : BluetoothDevice = bluetoothAdapter.getRemoteDevice(address)
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(myUUID)
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                    bluetoothSocket!!.connect()
                }
            } catch (e:IOException) {
                connectSuccess = false
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(!connectSuccess) {
                Log.d("data","couldn't connect")
            } else {
                isConnected = true
            }
            progress.dismiss()
        }
    }

    companion object {
        var myUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
        var bluetoothSocket: BluetoothSocket? = null
        lateinit var progress: ProgressDialog
        lateinit var bluetoothAdapter: BluetoothAdapter
        lateinit var bluetoothManager: BluetoothManager
        lateinit var bluetoothDevice: BluetoothDevice
        var isConnected: Boolean = false
        lateinit var address: String
        lateinit var name: String
    }
}
























