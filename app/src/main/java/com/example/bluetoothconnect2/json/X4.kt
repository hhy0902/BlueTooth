package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class X4(
    @SerializedName("blaster")
    val blaster: BlasterX?,
    @SerializedName("plug")
    val plug: PlugXXX?
)