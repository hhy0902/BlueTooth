package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Load(
    @SerializedName("id")
    val id: String?,
    @SerializedName("jsonrpc")
    val jsonrpc: String?,
    @SerializedName("result")
    val result: Result?
)