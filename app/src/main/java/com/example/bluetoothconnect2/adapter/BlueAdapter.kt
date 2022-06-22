package com.example.bluetoothconnect2.adapter

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothconnect2.MainActivity
import com.example.bluetoothconnect2.MainActivity2
import com.example.bluetoothconnect2.R
import com.example.bluetoothconnect2.model.BlueTooth
import java.io.IOException
import java.util.*

class BlueAdapter(val context: Context, val blueToothList: MutableList<BlueTooth>) :
    RecyclerView.Adapter<BlueAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val name : TextView
        val id : TextView

        init {
            name = itemView.findViewById(R.id.blue_name)
            id = itemView.findViewById(R.id.blue_id)

            itemView.setOnClickListener {
                val position : Int = adapterPosition

                val intent = Intent(itemView.context, MainActivity2::class.java)
                intent.putExtra("Device_address", blueToothList[position].id)
                intent.putExtra("Device_name", blueToothList[position].name)
                //itemView.context.startActivity(intent)

                (context as Activity).setResult(RESULT_OK, intent)
                Toast.makeText(context,"연결 중",Toast.LENGTH_SHORT).show()
                context.finish()

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.blue_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = blueToothList.get(position).name
        holder.id.text = blueToothList.get(position).id


    }

    override fun getItemCount(): Int {
        return blueToothList.size
    }

}


















