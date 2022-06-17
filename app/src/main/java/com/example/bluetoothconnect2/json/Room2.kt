package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Room2(
    @SerializedName("blaster")
    val blaster: BlasterXXXXX?,
    @SerializedName("units")
    val units: UnitsX?
)