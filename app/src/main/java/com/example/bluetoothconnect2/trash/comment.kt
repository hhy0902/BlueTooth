package com.example.bluetoothconnect2.trash

class comment {


//                    val jsonrpcObject = JSONObject()
//                    val jsonrpcArray = JSONArray()
//                    val jsonrpcArrayObject = JSONObject()
//
//                    val irdbDeviceObject = JSONObject()
//                    val irdbCompanyObject = JSONObject()
//                    val irdbModelObject = JSONObject()
//                    val irdbValueObject = JSONObject()
//                    val irdbOnOffObject = JSONObject()
//
//                    jsonrpcObject.put("jsonrpc","2.0")
//                    jsonrpcObject.put("method","patch_gz")
//                    jsonrpcArrayObject.put("op","add")
//                    jsonrpcArrayObject.put("path","/irdb")
//
//                    irdbDeviceObject.put("projector", irdbCompanyObject)
//                    irdbCompanyObject.put("maxell",irdbModelObject)
//                    irdbModelObject.put("mp-eu5002", irdbValueObject)
//                    irdbValueObject.put("type","nec")
//                    irdbValueObject.put("bitlen",32)
//                    irdbValueObject.put("data", irdbOnOffObject)
//                    irdbOnOffObject.put("off", "88C00510")
//                    irdbOnOffObject.put("on", "88008080")
//
//                    val irdbCompanyObject2 = JSONObject()
//                    val irdbModelObject2 = JSONObject()
//                    val irdbValueObject2 = JSONObject()
//                    val irdbOnOffObject2 = JSONObject()
//
//                    irdbDeviceObject.put("air_conditioner", irdbCompanyObject2)
//                    irdbCompanyObject2.put("lg", irdbModelObject2)
//                    irdbModelObject2.put("our_office", irdbValueObject2)
//                    irdbValueObject2.put("type","nec")
//                    irdbValueObject2.put("bitlen", 28)
//                    irdbValueObject2.put("data",irdbOnOffObject2)
//                    irdbOnOffObject2.put("off","88C00510")
//                    irdbOnOffObject2.put("on","88008080")
//
//                    val irdbCompanyObject3 = JSONObject()
//                    val irdbModelObject3 = JSONObject()
//                    val irdbModelObject3_2 = JSONObject()
//                    val irdbValueObject3 = JSONObject()
//                    val irdbValueObject3_2 = JSONObject()
//                    val irdbOnOffObject3 = JSONObject()
//
//                    irdbDeviceObject.put("lighting", irdbCompanyObject3)
//                    irdbCompanyObject3.put("skt",irdbModelObject3)
//                    irdbCompanyObject3.put("kt",irdbModelObject3_2)
//                    irdbModelObject3.put("sktModel1", irdbValueObject3)
//                    irdbModelObject3.put("sktModel2", irdbValueObject3)
//                    irdbModelObject3_2.put("ktModel1", irdbValueObject3_2)
//                    irdbModelObject3_2.put("ktModel2", irdbValueObject3_2)
//                    irdbValueObject3.put("type","nec")
//                    irdbValueObject3.put("bitlen",24)
//                    irdbValueObject3.put("data",irdbOnOffObject3)
//                    irdbValueObject3_2.put("type","nec2")
//                    irdbValueObject3_2.put("bitlen",32)
//                    irdbValueObject3_2.put("data",irdbOnOffObject3)
//                    irdbOnOffObject3.put("off","88C00510")
//                    irdbOnOffObject3.put("on","88008080")
//
//                    jsonrpcArrayObject.put("value",irdbDeviceObject)
//                    jsonrpcArray.put(jsonrpcArrayObject)
//                    jsonrpcObject.put("params",jsonrpcArray)
//                    jsonrpcObject.put("id","qwerasd$random")
//
//                    sendCommand(jsonrpcObject.toString())
//                    Log.d("asdf jsonrpcObject", jsonrpcObject.toString())
//                    Log.d("asdf jsonrpcObject size", jsonrpcObject.toString().length.toString())

    //                for(e in 1..roomList.size) {
//                    sharedPreferences = getSharedPreferences("$e", Context.MODE_PRIVATE)
//
//                    val testBody2 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                    val testBody3 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                    val testBody4 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/blaster/node_id\",\"value\":300002}],\"id\":\"qwerasd$random\"}"
//                    val testBody5 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//
//                    Thread.sleep(100)
//                    sendCommand(testBody2)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody3)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody4)
//
//                    Thread.sleep(100)
//                    sendCommand(testBody5)
//                    Thread.sleep(100)
//
//                    for (w in 1..10) {
//                        val testBody6 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w\",\"value\":{}}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody6)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody6_${e}_$w", testBody6)
//
//                        val testBody7 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/node_id\",\"value\":${sharedPreferences.all.get("editdata${w}")}}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody7)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody7_${e}_$w", testBody7)
//
//                        val testBody8 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/use_switch\",\"value\":\"false\"}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody8)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody8_${e}_$w", testBody8)
//
//                        val testBody9 = "{\"jsonrpc\":\"2.0\",\"method\":\"patch_gz\",\"params\":[{\"op\":\"add\",\"path\":\"/room_$e/plugs/$w/remote_key\",\"value\":\"projector/maxell/mp-eu5002\"}],\"id\":\"qwerasd$random\"}"
//                        sendCommand(testBody9)
//                        Thread.sleep(100)
//                        Log.d("asdf testBody9_${e}_$w", testBody9)
//                    }
//                    receiveData()
//                }


    //when (spinnerSelect4_Company) {
//                        1 -> {
//                            Log.d("asdf spinnerSelect4_Company value check1" , "1234")
//                            order_lighting_company = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_company_key_list.get(0)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
//                            when (spinnerSelect4_Model) {
//                                1 -> {
//
//                                }
//                                2 -> {
//
//                                }
//                                3 -> {
//
//                                }
//                            }
//                        }
//                        2 -> {
//                            Log.d("asdf spinnerSelect4_Company value check2" , "5678")
//                            order_lighting_company = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_company_key_list.get(1)}\", \"value\": {}}], \"id\": \"qwerasd$random\"}"
//                            when (spinnerSelect4_Model) {
//                                1 -> {
//                                    Log.d("asdf spinnerSelect4_Model value check1" , "qw")
//                                    order_lighting_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_company_key_list.get(1)}/${irdb_lighting_modle_key_list.get(0)}\", \"value\": ${irdb_lighting_modle_value}}], \"id\": \"qwerasd$random\"}"
//                                }
//                                2 -> {
//                                    Log.d("asdf spinnerSelect4_Model value check2" , "er")
//                                    order_lighting_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_company_key_list.get(1)}/${irdb_lighting_modle_key_list.get(1)}\", \"value\": ${irdb_lighting_modle_value2}}], \"id\": \"qwerasd$random\"}"
//                                }
//                                3 -> {
//                                    Log.d("asdf spinnerSelect4_Model value check3" , "as")
//                                    order_lighting_model = "{\"jsonrpc\": \"2.0\", \"method\": \"patch_gz\", \"params\": [{\"op\": \"add\", \"path\": \"/irdb/lighting/${irdb_lighting_company_key_list.get(1)}/${irdb_lighting_modle_key_list.get(2)}\", \"value\": ${irdb_lighting_modle_value3}}], \"id\": \"qwerasd$random\"}"
//                                }
//                            }
//                        }
//                    }




































}

