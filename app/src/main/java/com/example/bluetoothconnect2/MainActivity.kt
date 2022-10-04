package com.example.bluetoothconnect2

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothconnect2.adapter.BlueAdapter
import com.example.bluetoothconnect2.adapter.BlueListAdapter
import com.example.bluetoothconnect2.model.BlueTooth
import com.example.bluetoothconnect2.model.Room

class MainActivity : AppCompatActivity() {

    // 여기는 불루투스 연결된걸 확인하고 recyclerView로 보여주는 곳
    lateinit var bluetoothManager: BluetoothManager
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    lateinit var pairedDevice: Set<BluetoothDevice>

    lateinit var blueToothList : MutableList<BlueTooth>

    lateinit var blueListAdapter : BlueListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNotificationPermissions()

        // 여기에 블루투스 페어링 한 리스트가 담겨짐
        blueToothList = mutableListOf<BlueTooth>()

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? -> }

        bluetoothManager =
            applicationContext.getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (!bluetoothAdapter.isEnabled) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityIfNeeded(intent, REQUEST_ENABLE_BT)
        }

        val testValue = intent.getStringExtra("test123")
        Log.d("asdf", testValue.toString())

        pairedDevice = bluetoothAdapter!!.bondedDevices

        /*
        기존에 사용하던 BlueAdapter를 BlueListAdapter로 교체함
        만약 오류가 난다면 밑에 주석을 풀고 BlueListAdapter를 주석으로 처리하기
        */

//        val blueAdapter = BlueAdapter(this,blueToothList)
//        findViewById<RecyclerView>(R.id.mainRecyclerView).layoutManager = LinearLayoutManager(this)
//        findViewById<RecyclerView>(R.id.mainRecyclerView).adapter = blueAdapter
//        blueAdapter.blueToothList.clear()

        blueListAdapter = BlueListAdapter(blueListClick = {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("Device_address", it.id)
            intent.putExtra("Device_name", it.name)
            //itemView.context.startActivity(intent)

            setResult(RESULT_OK, intent)
            Toast.makeText(this,"연결 중",Toast.LENGTH_SHORT).show()
            finish()
        })
        findViewById<RecyclerView>(R.id.mainRecyclerView).layoutManager = LinearLayoutManager(this)
        findViewById<RecyclerView>(R.id.mainRecyclerView).adapter = blueListAdapter
        blueListAdapter.submitList(blueToothList)



        if (!pairedDevice.isEmpty() /*&& blueToothList.size == 0*/) {
            pairedDevice.forEach {
                blueToothList.add(BlueTooth(it.name, it.address))
            }

        } else {
            Toast.makeText(this, "페어링된 기기 없음", Toast.LENGTH_SHORT).show()
        }
        Toast.makeText(this,"${blueToothList.size}",Toast.LENGTH_SHORT).show()

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





























