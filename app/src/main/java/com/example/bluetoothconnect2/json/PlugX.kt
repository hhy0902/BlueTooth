package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class PlugX(
    @SerializedName("nid")
    val nid: Int?,
    @SerializedName("use_switch")
    val useSwitch: Boolean?
)