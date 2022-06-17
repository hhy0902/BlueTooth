package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Blaster(
    @SerializedName("node_id")
    val nodeId: Int?
)