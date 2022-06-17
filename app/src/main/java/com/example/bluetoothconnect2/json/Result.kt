package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("room_1")
    val room1: Room1?,
    @SerializedName("room_2")
    val room2: Room2?
)