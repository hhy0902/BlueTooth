package com.example.bluetoothconnect2

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val btnPaired: Button by lazy {
        findViewById(R.id.btn_paired)
    }
    private val listView: ListView by lazy {
        findViewById(R.id.listview)
    }
    private val listView2: ListView by lazy {
        findViewById(R.id.listview2)
    }

    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    lateinit var pairedDevice: Set<BluetoothDevice>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNotificationPermissions()

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? -> }

        bluetoothManager =
            applicationContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (!bluetoothAdapter.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityIfNeeded(intent, REQUEST_ENABLE_BT)
        }


        btnPaired.setOnClickListener {
            pairedDevice = bluetoothAdapter!!.bondedDevices
            val list: ArrayList<BluetoothDevice> = ArrayList()
            val list2: ArrayList<String> = ArrayList()

            if (!pairedDevice.isEmpty()) {
                for (device: BluetoothDevice in pairedDevice) {
                    list.add(device)
                    list2.add(device.name)
                }
            } else {
                Toast.makeText(this, "페어링된 기기 없음", Toast.LENGTH_SHORT).show()
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
            val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, list2)
            listView.adapter = adapter
            listView2.adapter = adapter2

            listView.setOnItemClickListener { adapterView, view, position, l ->
                val device: BluetoothDevice = list[position]
                val address: String = device.address
                val name: String = device.name

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("Device_address", address)
                intent.putExtra("Device_name", name)
                startActivity(intent)
            }

            listView2.setOnItemClickListener { adapterView, view, position, l ->
                val device: BluetoothDevice = list[position]
                val address: String = device.address
                val name: String = device.name

                val intent = Intent(this, MainActivity2::class.java)
                intent.putExtra("Device_address", address)
                intent.putExtra("Device_name", name)
                startActivity(intent)
            }

        }


    }

    private fun requestNotificationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ),
            REQUEST_ACCESS_NOTIFICATION_PERMISSIONS
        )
    }

    companion object {
        private const val REQUEST_ACCESS_NOTIFICATION_PERMISSIONS = 100
        private const val REQUEST_ENABLE_BT = 1
    }
}





























