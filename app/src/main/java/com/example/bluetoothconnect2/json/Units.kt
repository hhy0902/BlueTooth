package com.example.bluetoothconnect2.json


import com.google.gson.annotations.SerializedName

data class Units(
    @SerializedName("1")
    val x1: X1?,
    @SerializedName("2")
    val x2: X2?,
    @SerializedName("3")
    val x3: X3?,
    @SerializedName("4")
    val x4: X4?,
    @SerializedName("5")
    val x5: X5?,
    @SerializedName("6")
    val x6: X6?,
    @SerializedName("7")
    val x7: X7?,
    @SerializedName("8")
    val x8: X8?,
    @SerializedName("9")
    val x9: X9?
)