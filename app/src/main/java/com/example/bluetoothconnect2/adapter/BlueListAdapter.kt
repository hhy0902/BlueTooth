package com.example.bluetoothconnect2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bluetoothconnect2.R
import com.example.bluetoothconnect2.model.BlueTooth

class BlueListAdapter(val blueListClick : (BlueTooth) -> Unit) : ListAdapter<BlueTooth, BlueListAdapter.ViewHolder> (diffUtil) {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder (itemView) {

        fun bind(item : BlueTooth) {

            val name = itemView.findViewById<TextView>(R.id.blue_name)
            val id = itemView.findViewById<TextView>(R.id.blue_id)

            name.text = item.name
            id.text = item.id

            itemView.setOnClickListener {
                blueListClick(item)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.blue_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList.get(position))
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BlueTooth>() {
            override fun areItemsTheSame(oldItem: BlueTooth, newItem: BlueTooth): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: BlueTooth, newItem: BlueTooth): Boolean {
                return oldItem == newItem
            }

        }
    }


}














































