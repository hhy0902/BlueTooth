package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class X5(
    @SerializedName("blaster")
    val blaster: BlasterXX?,
    @SerializedName("plug")
    val plug: PlugXXXX?
)