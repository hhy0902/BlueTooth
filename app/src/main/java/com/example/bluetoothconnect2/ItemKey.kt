package com.example.bluetoothconnect2

import org.json.JSONArray
import org.json.JSONObject

class ItemKey {
    companion object {
        const val PROJECTOR = "projector"
        const val MAXELL = "maxell"
    }

    fun makeJson() {

        val jsonrpcObject = JSONObject()
        val jsonrpcArray = JSONArray()
        val jsonrpcArrayObject = JSONObject()

        val irdbDeviceObject = JSONObject()
        val irdbCompanyObject = JSONObject()
        val irdbModelObject = JSONObject()
        val irdbValueObject = JSONObject()
        val irdbOnOffObject = JSONObject()

        jsonrpcObject.put("jsonrpc","2.0")
        jsonrpcObject.put("method","patch_gz")
        jsonrpcArrayObject.put("op","add")
        jsonrpcArrayObject.put("path","/irdb")

        irdbDeviceObject.put(PROJECTOR, irdbCompanyObject)
        irdbCompanyObject.put(MAXELL,irdbModelObject)
        irdbModelObject.put("mp-eu5002", irdbValueObject)
        irdbValueObject.put("type","nec")
        irdbValueObject.put("bitlen",32)
        irdbValueObject.put("data", irdbOnOffObject)
        irdbOnOffObject.put("off", "88C00510")
        irdbOnOffObject.put("on", "88008080")

        val irdbCompanyObject2 = JSONObject()
        val irdbModelObject2 = JSONObject()
        val irdbValueObject2 = JSONObject()
        val irdbOnOffObject2 = JSONObject()

        irdbDeviceObject.put("air_conditioner", irdbCompanyObject2)
        irdbCompanyObject2.put("lg", irdbModelObject2)
        irdbModelObject2.put("our_office", irdbValueObject2)
        irdbValueObject2.put("type","nec")
        irdbValueObject2.put("bitlen", 28)
        irdbValueObject2.put("data",irdbOnOffObject2)
        irdbOnOffObject2.put("off","88C00510")
        irdbOnOffObject2.put("on","88008080")

        val irdbCompanyObject3 = JSONObject()
        val irdbModelObject3 = JSONObject()
        val irdbModelObject3_2 = JSONObject()
        val irdbValueObject3 = JSONObject()
        val irdbValueObject3_2 = JSONObject()
        val irdbOnOffObject3 = JSONObject()

        irdbDeviceObject.put("lighting", irdbCompanyObject3)
        irdbCompanyObject3.put("skt",irdbModelObject3)
        irdbCompanyObject3.put("kt",irdbModelObject3_2)
        irdbModelObject3.put("sktModel1", irdbValueObject3)
        irdbModelObject3.put("sktModel2", irdbValueObject3)
        irdbModelObject3_2.put("ktModel1", irdbValueObject3_2)
        irdbModelObject3_2.put("ktModel2", irdbValueObject3_2)
        irdbValueObject3.put("type","nec")
        irdbValueObject3.put("bitlen",24)
        irdbValueObject3.put("data",irdbOnOffObject3)
        irdbValueObject3_2.put("type","nec2")
        irdbValueObject3_2.put("bitlen",32)
        irdbValueObject3_2.put("data",irdbOnOffObject3)
        irdbOnOffObject3.put("off","88C00510")
        irdbOnOffObject3.put("on","88008080")

        jsonrpcArrayObject.put("value",irdbDeviceObject)
        jsonrpcArray.put(jsonrpcArrayObject)
        jsonrpcObject.put("params",jsonrpcArray)
        jsonrpcObject.put("id","qwerasd1")

        //return Pair(irdbDeviceObject.toString(), irdbCompanyObject.toString())

    }
}