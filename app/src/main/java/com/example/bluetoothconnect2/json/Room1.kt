package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Room1(
    @SerializedName("blaster")
    val blaster: Blaster?,
    @SerializedName("units")
    val units: Units?
)