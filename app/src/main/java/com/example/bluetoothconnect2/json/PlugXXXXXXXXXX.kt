package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class PlugXXXXXXXXXX(
    @SerializedName("nid")
    val nid: Int?,
    @SerializedName("use_switch")
    val useSwitch: Boolean?
)