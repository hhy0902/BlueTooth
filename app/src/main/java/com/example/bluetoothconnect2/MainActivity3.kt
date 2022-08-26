package com.example.bluetoothconnect2

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bluetoothconnect2.databinding.ActivityMain3Binding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONObject

class MainActivity3 : AppCompatActivity() {

    private val binding2 by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
    }

    var isSave = 0

    var roomName = ""

    var spinnerData3_Device : String = "speaker"
    var spinnerData3_Company : String = ""
    var spinnerData3_Model : String = ""

    var spinnerData4_Device : String = "lamp"
    var spinnerData4_Company : String = ""
    var spinnerData4_Model : String = ""

    var spinnerData5_Device : String = "projector"
    var spinnerData5_Company : String = ""
    var spinnerData5_Model : String = ""

    var spinnerData6_Device : String = "projector"
    var spinnerData6_Company : String = ""
    var spinnerData6_Model : String = ""

    var spinnerData7_Device : String = "air_conditioner"
    var spinnerData7_Company : String = ""
    var spinnerData7_Model : String = ""

    var editData1 = ""
    var editData2 = ""
    var editData3 = ""
    var editData4 = ""
    var editData5 = ""
    var editData6 = ""
    var editData7 = ""
    var editData8 = ""

    var spinnerSelect3_Device = 1
    var spinnerSelect3_Company = 0
    var spinnerSelect3_Model = 0

    var spinnerSelect4_Device = 1
    var spinnerSelect4_Company = 0
    var spinnerSelect4_Model = 0

    var spinnerSelect5_Device = 1
    var spinnerSelect5_Company = 0
    var spinnerSelect5_Model = 0

    var spinnerSelect6_Device = 1
    var spinnerSelect6_Company = 0
    var spinnerSelect6_Model = 0

    var spinnerSelect7_Device = 1
    var spinnerSelect7_Company = 0
    var spinnerSelect7_Model = 0

    var qrButtonClickCheck = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)

        val abcd = intent.getStringExtra("abcd") // 룸 sharedpreferences에 사용되는 중요한 변수
        val deviceId = intent.getStringExtra("deviceId")
        binding2.numberText.text = "Room $abcd"
        roomName = "$abcd"

        // 메인 화면에서 로드하면 발생하는 기능
        val sharedPreferences2 = getSharedPreferences("LoadToF", Context.MODE_PRIVATE)
        val LoadToF = sharedPreferences2.getBoolean("btnLoadClick", false)

        //val sharedPreferences3 = getSharedPreferences("appFirst", Context.MODE_PRIVATE)

        // 메인 쉐어드프리퍼런스 액티비티2에서도 많이 사용 함
        val sharedPreferences4 = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        // 메인2에서 load누르고 난 뒤 사용되는 프리퍼런스
        val sharedPreferencesLoad = getSharedPreferences("load$roomName", Context.MODE_PRIVATE)

        // irdbv2.json 가져오기
        val irdbString = assets.open("irdbV2.json").reader().readText()
        val irdb = JSONObject(irdbString)
        Log.d("asdf irdbString", irdb.toString())

        // 각각 프로젝터, 조명, 에어컨, 스피커의 값들을 가져오는 부분
        val irdb_projector = irdb.getString("projector")
        Log.d("asdf irdb_projector", irdb_projector)
        val irdb_lighting = irdb.getString("lamp")
        Log.d("asdf irdb_lighting", irdb_lighting)
        val irdb_air_conditioner = irdb.getString("air_conditioner")
        Log.d("asdf irdb_air_conditioner", irdb_air_conditioner)
        val irdb_speaker = irdb.getString("speaker")

        // speaker ==========================================================================================================================================================
        // 스피커 회사 종류 가죠오는 부분
        val irdb_speaker_key = JSONObject(irdb_speaker)
        val irdb_speaker_company_key_list = mutableListOf<String>()
        irdb_speaker_key.keys().forEach {
            Log.d("asdf irdb_speaker_company_key", "${it}")
            irdb_speaker_company_key_list.add(it)
        }

        // projector ==========================================================================================================================================================
        // 프로젝터 회사 종류 가져오는 부분
        val irdb_projector_key = JSONObject(irdb_projector)
        val irdb_projector_company_key_list = mutableListOf<String>()
        irdb_projector_key.keys().forEach {
            Log.d("asdf irdb_projector_company_key", "${it}")
            irdb_projector_company_key_list.add(it)
        }

        // air_conditioner ==========================================================================================================================================================
        // 에어컨 회사 종류 가죠오는 부분
        val irdb_aircon_key = JSONObject(irdb_air_conditioner)
        val irdb_aircon_company_key_list = mutableListOf<String>()
        irdb_aircon_key.keys().forEach {
            Log.d("asdf irdb_aircon_company_key", "${it}")
            irdb_aircon_company_key_list.add(it)
        }

        // lighting ==========================================================================================================================================================
        // 조명 회사 종류가죠오는 부분
        val irdb_lighting_key = JSONObject(irdb_lighting)
        val irdb_lighting_company_key_list = mutableListOf<String>()
        irdb_lighting_key.keys().forEach {
            Log.d("asdf irdb_lighting_company_key", "${it}")
            irdb_lighting_company_key_list.add(it)
        }

        // 스피커 리스트에 값 넣기 이게 스피너로 들어감 ==========================================================================================================================================================
        // 스피커 회사 리스트에 값 넣어주기
        val speakerCompanyList = mutableListOf<String>()
        speakerCompanyList.add("")
        for(i in 0 until irdb_speaker_company_key_list.size) {
            speakerCompanyList.add(irdb_speaker_company_key_list.get(i))
        }

        // 프로젝터 리스트에 값 넣기 이게 스피너로 들어감 ==========================================================================================================================================================
        // 프로젝터 회사 리스트에 값 넣어주기
        val projectorCompanyList = mutableListOf<String>()
        projectorCompanyList.add("")
        for(i in 0 until irdb_projector_company_key_list.size) {
            projectorCompanyList.add(irdb_projector_company_key_list.get(i))
        }

        // 에어컨 리스트에 값 넣기 이게 스피너로 들어감 ==========================================================================================================================================================
        // 에어컨 회사 리스트에 값 넣어주기
        val airconCompanyList = mutableListOf<String>()
        airconCompanyList.add("")
        for(i in 0 until irdb_aircon_company_key_list.size) {
            airconCompanyList.add(irdb_aircon_company_key_list.get(i))
        }

        // 조명 리스트에 값 넣기 이게 스피너로 들어감  ==========================================================================================================================================================
        // 조명 회사 리스트에 값 넣어주기
        val lightingCompanyList = mutableListOf<String>()
        lightingCompanyList.add("")
        for (i in 0 until irdb_lighting_company_key_list.size) {
            lightingCompanyList.add(irdb_lighting_company_key_list.get(i))
        }

        // 스피너 설정
        val speakerList = mutableListOf<String>()
        val spinnerAdapter3_3 = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, speakerCompanyList)
        binding2.spinner33.adapter = spinnerAdapter3_3
        binding2.spinner33.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData3_Company = binding2.spinner33.getItemAtPosition(p2).toString()
                spinnerSelect3_Company = p2
                Log.d("spinnerAdapter3_3","${binding2.spinner33.getItemAtPosition(p2)}")
                speakerList.clear()
                speakerList.add("")

                // 위 스피너에서 선택한 값을 받아서 irdb_speaker_model_key를 추출하고, speakerList에 넣어줘서 종속성 탈출~
                val irdb_speaker_company_key = irdb_speaker_key.getString("${binding2.spinner33.getItemAtPosition(p2)}")
                val irdb_speaker_model_key = JSONObject(irdb_speaker_company_key)
                irdb_speaker_model_key.keys().forEach {
                    Log.d("asdf speakerList", "${it}")
                    speakerList.add(it.toString())
                }

                // 스피커
                val loadSpeakerList = mutableListOf<String>()
                // 만약 메인2에서 load를 눌렀으면 동작하는 코드 load로 읽어온데이터를 저장하고 split으로 쪼개고 해당하는 값을 스피너에 넣어준다
                if (LoadToF) {
                    loadSpeakerList.clear()
                    val load_speaker = sharedPreferencesLoad.getString("load${roomName}_ir_key_2", "")
                    load_speaker?.split("/")?.forEach {
                        loadSpeakerList.add(it)
                    }

                    // 회사
                    // 해당되는 값 스피너에 넣어주기 회사니까 2
                    val loadSpeakerCompany = spinnerAdapter3_3.getPosition("${loadSpeakerList.get(2)}")
                    binding2.spinner33.setSelection(loadSpeakerCompany)

                }

                val spinnerAdapter3_4 = ArrayAdapter(this@MainActivity3,R.layout.support_simple_spinner_dropdown_item, speakerList)
                binding2.spinner34.adapter = spinnerAdapter3_4
                binding2.spinner34.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            spinnerData3_Model = binding2.spinner34.getItemAtPosition(p2).toString()
                            spinnerSelect3_Model = p2
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                val spinnerSelect3_Model = sharedPreferences4.getInt("spinnerSelect3_Model", 0)
                binding2.spinner34.setSelection(spinnerSelect3_Model)

                if (LoadToF) {
                    // 모델
                    // 해당되는 값 스피너에 넣어주기 모델이니까 3
                    val loadAirconModel = spinnerAdapter3_4.getPosition("${loadSpeakerList.get(3)}")
                    binding2.spinner34.setSelection(loadAirconModel)
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val lampList = mutableListOf<String>()
        val spinnerAdapter4_3 = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, lightingCompanyList)
        binding2.spinner43.adapter = spinnerAdapter4_3
        binding2.spinner43.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData4_Company = binding2.spinner43.getItemAtPosition(p2).toString()
                spinnerSelect4_Company = p2
                Log.d("spinnerAdapter4_3","${binding2.spinner43.getItemAtPosition(p2)}")
                lampList.clear()
                lampList.add("")

                val irdb_lighting_company_key = irdb_lighting_key.getString("${binding2.spinner43.getItemAtPosition(p2)}")
                val irdb_lighting_model_key = JSONObject(irdb_lighting_company_key)
                irdb_lighting_model_key.keys().forEach {
                    Log.d("asdf lampList", "${it}")
                    lampList.add(it)
                }

                // 조명
                val loadLampList = mutableListOf<String>()
                if (LoadToF) {
                    loadLampList.clear()
                    val load_lamp = sharedPreferencesLoad.getString("load${roomName}_ir_key_3", "")
                    load_lamp?.split("/")?.forEach {
                        loadLampList.add(it)
                    }

                    // 회사
                    val loadLampCompany = spinnerAdapter4_3.getPosition("${loadLampList.get(2)}")
                    binding2.spinner43.setSelection(loadLampCompany)

                }

                val spinnerAdapter4_4 = ArrayAdapter(this@MainActivity3,R.layout.support_simple_spinner_dropdown_item, lampList)
                binding2.spinner44.adapter = spinnerAdapter4_4
                binding2.spinner44.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            spinnerData4_Model = binding2.spinner44.getItemAtPosition(p2).toString()
                            spinnerSelect4_Model = p2

                            if (LoadToF) {
                                // 모델
                                val loadLampModel = spinnerAdapter4_4.getPosition("${loadLampList.get(3)}")
                                binding2.spinner44.setSelection(loadLampModel)
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                val spinnerSelect4_Model = sharedPreferences4.getInt("spinnerSelect4_Model", 0)
                binding2.spinner44.setSelection(spinnerSelect4_Model)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val projectorList = mutableListOf<String>()
        val spinnerAdapter5_3 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, projectorCompanyList)
        binding2.spinner53.adapter = spinnerAdapter5_3
        binding2.spinner53.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5_Company = binding2.spinner53.getItemAtPosition(p2).toString()
                spinnerSelect5_Company = p2

                Log.d("spinnerAdapter5_3","${binding2.spinner53.getItemAtPosition(p2)}")
                projectorList.clear()
                projectorList.add("")

                val irdb_projector_company_key = irdb_projector_key.getString("${binding2.spinner53.getItemAtPosition(p2)}")
                val irdb_projector_model_key = JSONObject(irdb_projector_company_key)
                irdb_projector_model_key.keys().forEach {
                    Log.d("asdf projectorList", "${it}")
                    projectorList.add(it)
                }

                // 프로젝터1
                val loadProjectorList1 = mutableListOf<String>()
                if (LoadToF) {
                    loadProjectorList1.clear()
                    val load_projector = sharedPreferencesLoad.getString("load${roomName}_ir_key_4", "")
                    load_projector?.split("/")?.forEach {
                        loadProjectorList1.add(it)
                    }

                    // 회사
                    val loadProjectorCompany = spinnerAdapter5_3.getPosition("${loadProjectorList1.get(2)}")
                    binding2.spinner53.setSelection(loadProjectorCompany)

                }

                val spinnerAdapter5_4 = ArrayAdapter(this@MainActivity3,R.layout.support_simple_spinner_dropdown_item, projectorList)
                binding2.spinner54.adapter = spinnerAdapter5_4
                binding2.spinner54.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                            spinnerSelect5_Model = p2

                            if (LoadToF) {
                                // 모델
                                val loadProjectorModel = spinnerAdapter5_4.getPosition("${loadProjectorList1.get(3)}")
                                binding2.spinner54.setSelection(loadProjectorModel)
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                val spinnerSelect5_Model = sharedPreferences4.getInt("spinnerSelect5_Model", 0)
                binding2.spinner54.setSelection(spinnerSelect5_Model)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val projectorList2 = mutableListOf<String>()
        val spinnerAdapter6_3 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item, projectorCompanyList)
        binding2.spinner63.adapter = spinnerAdapter6_3
        binding2.spinner63.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6_Company = binding2.spinner63.getItemAtPosition(p2).toString()
                spinnerSelect6_Company = p2
                Log.d("spinnerAdapter6_3","${binding2.spinner63.getItemAtPosition(p2)}")
                projectorList2.clear()
                projectorList2.add("")

                val irdb_projector2_company_key = irdb_projector_key.getString("${binding2.spinner63.getItemAtPosition(p2)}")
                val irdb_projector2_model_key = JSONObject(irdb_projector2_company_key)
                irdb_projector2_model_key.keys().forEach {
                    Log.d("asdf projectorList2", "${it}")
                    projectorList2.add(it)
                }

                // 프로젝터2
                val loadProjectorList2 = mutableListOf<String>()
                if (LoadToF) {
                    loadProjectorList2.clear()
                    val load_projector = sharedPreferencesLoad.getString("load${roomName}_ir_key_5", "")
                    load_projector?.split("/")?.forEach {
                        loadProjectorList2.add(it)
                    }

                    // 회사
                    val loadProjectorCompany = spinnerAdapter6_3.getPosition("${loadProjectorList2.get(2)}")
                    binding2.spinner63.setSelection(loadProjectorCompany)

                }

                val spinnerAdapter6_4 = ArrayAdapter(this@MainActivity3,R.layout.support_simple_spinner_dropdown_item, projectorList2)
                binding2.spinner64.adapter = spinnerAdapter6_4
                binding2.spinner64.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                            spinnerSelect6_Model = p2

                            if (LoadToF) {
                                // 모델
                                val loadProjectorModel = spinnerAdapter6_4.getPosition("${loadProjectorList2.get(3)}")
                                binding2.spinner64.setSelection(loadProjectorModel)
                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                val spinnerSelect6_Model = sharedPreferences4.getInt("spinnerSelect6_Model", 0)
                binding2.spinner64.setSelection(spinnerSelect6_Model)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        val air_conditionerList = mutableListOf<String>()
        val spinnerAdapter7_3 = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, airconCompanyList)
        binding2.spinner73.adapter = spinnerAdapter7_3
        binding2.spinner73.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData7_Company = binding2.spinner73.getItemAtPosition(p2).toString()
                spinnerSelect7_Company = p2
                Log.d("spinnerAdapter7_3","${binding2.spinner73.getItemAtPosition(p2)}")
                air_conditionerList.clear()
                air_conditionerList.add("")

                val irdb_air_conditioner_company_key = irdb_aircon_key.getString("${binding2.spinner73.getItemAtPosition(p2)}")
                val irdb_air_conditioner_model_key = JSONObject(irdb_air_conditioner_company_key)
                irdb_air_conditioner_model_key.keys().forEach {
                    Log.d("asdf air_conditionerList", "${it}")
                    air_conditionerList.add(it)
                }

                // 에어컨
                val loadAirconList = mutableListOf<String>()
                if (LoadToF) {
                    loadAirconList.clear()
                    val load_aircon = sharedPreferencesLoad.getString("load${roomName}_ir_key_6", "")
                    load_aircon?.split("/")?.forEach {
                        loadAirconList.add(it)
                    }

                    // 회사
                    val loadAirconCompany = spinnerAdapter7_3.getPosition("${loadAirconList.get(2)}")
                    binding2.spinner73.setSelection(loadAirconCompany)

                    // 밑에서 함수로 만들어서 할려고 했는데 왜 그런지 모르겠지만 덮어써지지가 않아서 그냥 여기다 함
                    // 쓰레드로 할까? 생각중
                    val editTextValue1 = sharedPreferencesLoad.getString("load_blaster_$roomName", "")
                    Log.d("asdf load_blaster", "${editTextValue1}")
                    //clearEdittext()
                    binding2.editText1.setText(editTextValue1.toString())
                    Log.d("asdf edittext1 = ", "${binding2.editText1.text}")
                    val editTextValue2 = sharedPreferencesLoad.getString("load_nid_1", "")
                    binding2.editText2.setText(editTextValue2.toString())
                    val editTextValue5 = sharedPreferencesLoad.getString("load_nid_4", "")
                    binding2.editText5.setText(editTextValue5.toString())
                    val editTextValue6 = sharedPreferencesLoad.getString("load_nid_5", "")
                    binding2.editText6.setText(editTextValue6.toString())
                    val editTextValue8 = sharedPreferencesLoad.getString("load_nid_7", "")
                    binding2.editText8.setText(editTextValue8.toString())
                }

                val spinnerAdapter7_4 = ArrayAdapter(this@MainActivity3,R.layout.support_simple_spinner_dropdown_item, air_conditionerList)
                binding2.spinner74.adapter = spinnerAdapter7_4
                binding2.spinner74.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            spinnerData7_Model = binding2.spinner74.getItemAtPosition(p2).toString()
                            spinnerSelect7_Model = p2

                            // 메인 페이지에서 만약 load버튼을 눌렀으면 LoadToF가 true 아니면 false
                            // 만약 true면 gateway에서 가져온 값을 보여주고 false면 기존에 저장된 값을 보여준다
                            if (LoadToF) {
                                // 모델
                                val loadAirconModel = spinnerAdapter7_4.getPosition("${loadAirconList.get(3)}")
                                binding2.spinner74.setSelection(loadAirconModel)

                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                        }
                    }
                val spinnerSelect7_Model = sharedPreferences4.getInt("spinnerSelect7_Model", 0)
                binding2.spinner74.setSelection(spinnerSelect7_Model)

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }


        // 저장된 값을 불러와 화면에 뿌려주는 로드
        load()

        // 클리어 버튼 에디트 텍스트 값을 초기화 해줌
        binding2.btnClear.setOnClickListener {
            clearEdittext()
        }

        // 세이브 버튼
        binding2.btnSave.setOnClickListener {
            val saveEditor : SharedPreferences.Editor = sharedPreferences4.edit()
            // 밑에서 사용할 firstMovement의 값을 save버튼 누르면 변경
            saveEditor.putBoolean("firstMovement",false)
            isSave++
            save()
            saveEditor.commit()
        }

        // 저장된 값을 불러오는 load 버튼
        binding2.btnLoad.setOnClickListener {
            load()
        }

        // qrcode 누르면 qrButtonClickCheck에 해당하는 값을 넣어주고 runQRcodeReader()를 실행해준다
        binding2.qrImage1.setOnClickListener {
            qrButtonClickCheck = "button1"
            runQRcodeReader()
        }
        binding2.qrImage2.setOnClickListener {
            qrButtonClickCheck = "button2"
            runQRcodeReader()
        }
        binding2.qrImage5.setOnClickListener {
            qrButtonClickCheck = "button5"
            runQRcodeReader()
        }
        binding2.qrImage6.setOnClickListener {
            qrButtonClickCheck = "button6"
            runQRcodeReader()
        }
        binding2.qrImage8.setOnClickListener {
            qrButtonClickCheck = "button8"
            runQRcodeReader()
        }


    }

    // 저장된 값을 불러온다
    fun load() {
        // 각각의 roomName으로 저장된 값들을 불러온다
        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        val editTextValue = sharedPreferences.getString("editdata1", "")
        val editTextValue2 = sharedPreferences.getString("editdata2", "")
        val editTextValue5 = sharedPreferences.getString("editdata5", "")
        val editTextValue6 = sharedPreferences.getString("editdata6", "")
        val editTextValue8 = sharedPreferences.getString("editdata8", "")

        val spinnerValue3_3 = sharedPreferences.getInt("spinnerSelect3_Company", 0)
        val spinnerValue3_4 = sharedPreferences.getInt("spinnerSelect3_Model", 0)

        val spinnerValue4_3 = sharedPreferences.getInt("spinnerSelect4_Company", 0)
        val spinnerValue4_4 = sharedPreferences.getInt("spinnerSelect4_Model", 0)

        val spinnerValue5_3 = sharedPreferences.getInt("spinnerSelect5_Company", 0)
        val spinnerValue5_4 = sharedPreferences.getInt("spinnerSelect5_Model", 0)

        val spinnerValue6_3 = sharedPreferences.getInt("spinnerSelect6_Company", 0)
        val spinnerValue6_4 = sharedPreferences.getInt("spinnerSelect6_Model", 0)

        val spinnerValue7_3 = sharedPreferences.getInt("spinnerSelect7_Company", 0)
        val spinnerValue7_4 = sharedPreferences.getInt("spinnerSelect7_Model", 0)

        // 불러온 값들을 editText나 스피너
        binding2.editText1.setText(editTextValue.toString())
        binding2.editText2.setText(editTextValue2.toString())
        binding2.editText5.setText(editTextValue5.toString())
        binding2.editText6.setText(editTextValue6.toString())
        binding2.editText8.setText(editTextValue8.toString())

        binding2.spinner33.setSelection(spinnerValue3_3)
        binding2.spinner34.setSelection(spinnerValue3_4)

        binding2.spinner43.setSelection(spinnerValue4_3)
        binding2.spinner44.setSelection(spinnerValue4_4)

        binding2.spinner53.setSelection(spinnerValue5_3)
        binding2.spinner54.setSelection(spinnerValue5_4)

        binding2.spinner63.setSelection(spinnerValue6_3)
        binding2.spinner64.setSelection(spinnerValue6_4)

        binding2.spinner73.setSelection(spinnerValue7_3)
        binding2.spinner74.setSelection(spinnerValue7_4)

        // 메인 화면에서 로드하면 발생하는 기능
        val sharedPreferences2 = getSharedPreferences("LoadToF", Context.MODE_PRIVATE)
        val LoadToF = sharedPreferences2.getBoolean("btnLoadClick", false)

        // 메인2에서 load누르고 난 뒤 사용되는 프리퍼런스
        val sharedPreferencesLoad = getSharedPreferences("load$roomName", Context.MODE_PRIVATE)

        Log.d("asdf LoadToF", "${LoadToF}")

        // 메인 페이지에서 만약 load버튼을 눌렀으면 LoadToF가 true 아니면 false
        // 만약 true면 gateway에서 가져온 값을 보여주고 false면 기존에 저장된 값을 보여준다
        if (LoadToF) {
//            val editTextValue1 = sharedPreferencesLoad.getString("load_blaster_$roomName", "")
//            Log.d("asdf load_blaster", "${editTextValue1}")
//            clearEdittext()
//            binding2.editText1.setText(editTextValue1.toString())
//            Log.d("asdf edittext1 = ", "${binding2.editText1.text}")
//            val editTextValue2 = sharedPreferencesLoad.getString("load_nid_1", "")
//            binding2.editText2.setText(editTextValue2.toString())
//            val editTextValue5 = sharedPreferencesLoad.getString("load_nid_4", "")
//            binding2.editText5.setText(editTextValue5.toString())
//            val editTextValue6 = sharedPreferencesLoad.getString("load_nid_5", "")
//            binding2.editText6.setText(editTextValue6.toString())
//            val editTextValue8 = sharedPreferencesLoad.getString("load_nid_7", "")
//            binding2.editText8.setText(editTextValue8.toString())

            // load 버튼을 눌러 true로 변경되었고 gateway에서 가져온 값을 보여줬다면 값을 다시 false로 초기화해준다
            val editor : SharedPreferences.Editor = sharedPreferences2.edit()
            editor.putBoolean("btnLoadClick",false)
            editor.commit()

        }
    }

    fun clearEdittext() {
        binding2.editText1.text = null
        binding2.editText2.text = null
        binding2.editText5.text = null
        binding2.editText6.text = null
        binding2.editText8.text = null
    }

    // 값을 쉐어드 프리퍼런스로 저장하는 함수
    fun save() {
        editData1 = binding2.editText1.text.toString()
        editData2 = binding2.editText2.text.toString()
        editData5 = binding2.editText5.text.toString()
        editData6 = binding2.editText6.text.toString()
        editData8 = binding2.editText8.text.toString()

        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("editdata1",editData1)
        editor.putString("editdata2",editData2)
        editor.putString("editdata3",editData3)
        editor.putString("editdata4",editData4)
        editor.putString("editdata5",editData5)
        editor.putString("editdata6",editData6)
        editor.putString("editdata7",editData7)
        editor.putString("editdata8",editData8)

        editor.putInt("spinnerSelect3_Device",spinnerSelect3_Device)
        editor.putString("spinnerData3_Device", spinnerData3_Device)
        editor.putInt("spinnerSelect3_Company",spinnerSelect3_Company)
        editor.putString("spinnerData3_Company", spinnerData3_Company)
        editor.putInt("spinnerSelect3_Model",spinnerSelect3_Model)
        editor.putString("spinnerData3_Model", spinnerData3_Model)

        editor.putInt("spinnerSelect4_Device",spinnerSelect4_Device)
        editor.putString("spinnerData4_Device", spinnerData4_Device)
        editor.putInt("spinnerSelect4_Company",spinnerSelect4_Company)
        editor.putString("spinnerData4_Company", spinnerData4_Company)
        editor.putInt("spinnerSelect4_Model",spinnerSelect4_Model)
        editor.putString("spinnerData4_Model", spinnerData4_Model)

        editor.putInt("spinnerSelect5_Device",spinnerSelect5_Device)
        editor.putString("spinnerData5_Device", spinnerData5_Device)
        editor.putInt("spinnerSelect5_Company",spinnerSelect5_Company)
        editor.putString("spinnerData5_Company", spinnerData5_Company)
        editor.putInt("spinnerSelect5_Model",spinnerSelect5_Model)
        editor.putString("spinnerData5_Model", spinnerData5_Model)

        editor.putInt("spinnerSelect6_Device",spinnerSelect6_Device)
        editor.putString("spinnerData6_Device", spinnerData6_Device)
        editor.putInt("spinnerSelect6_Company",spinnerSelect6_Company)
        editor.putString("spinnerData6_Company", spinnerData6_Company)
        editor.putInt("spinnerSelect6_Model",spinnerSelect6_Model)
        editor.putString("spinnerData6_Model", spinnerData6_Model)

        editor.putInt("spinnerSelect7_Device",spinnerSelect7_Device)
        editor.putString("spinnerData7_Device", spinnerData7_Device)
        editor.putInt("spinnerSelect7_Company",spinnerSelect7_Company)
        editor.putString("spinnerData7_Company", spinnerData7_Company)
        editor.putInt("spinnerSelect7_Model",spinnerSelect7_Model)
        editor.putString("spinnerData7_Model", spinnerData7_Model)

        editor.commit()

        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("roomName", roomName)
        setResult(RESULT_OK, intent)
        //finish()
        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show()
        Log.d("asdf saveroomname", "$roomName")

    }

    // 뒤로가기 버튼을 누르면 쉐어드프리퍼런스에 저장된 값과 현재 editText, spinner에 있는 값을 비교하여
    // 만약 변화가 생겼을 경우 저장하겠냐고 하는 다이얼로그 생성 변화가 없으면 그대로 나가기
    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        val speakerCompanySpinner = binding2.spinner33.selectedItem.toString()
        val speakerModelSpinner = binding2.spinner34.selectedItem.toString()
        val lampCompanySpinner = binding2.spinner43.selectedItem.toString()
        val lampModelSpinner = binding2.spinner44.selectedItem.toString()
        val projectorCompanySpinner = binding2.spinner53.selectedItem.toString()
        val projectorModelSpinner = binding2.spinner54.selectedItem.toString()
        val projectorCompanySpinner2 = binding2.spinner63.selectedItem.toString()
        val projectorModelSpinner2 = binding2.spinner64.selectedItem.toString()
        val airconCompanySpinner = binding2.spinner73.selectedItem.toString()
        val airconModelSpinner = binding2.spinner74.selectedItem.toString()

        val savedSpeakerCompanySpinner = sharedPreferences.getString("spinnerData3_Company", "")
        val savedSpeakerModelSpinner = sharedPreferences.getString("spinnerData3_Model", "")
        val savedLampCompanySpinner = sharedPreferences.getString("spinnerData4_Company", "")
        val savedLampModelSpinner = sharedPreferences.getString("spinnerData4_Model", "")
        val savedProjectorCompanySpinner = sharedPreferences.getString("spinnerData5_Company", "")
        val savedProjectorModelSpinner = sharedPreferences.getString("spinnerData5_Model", "")
        val savedProjectorCompanySpinner2 = sharedPreferences.getString("spinnerData6_Company", "")
        val savedProjectorModelSpinner2 = sharedPreferences.getString("spinnerData6_Model", "")
        val savedAirconCompanySpinner = sharedPreferences.getString("spinnerData7_Company", "")
        val savedAirconModelSpinner = sharedPreferences.getString("spinnerData7_Model", "")

        Log.d("speakerCompanySpinner", "${speakerCompanySpinner}")
        Log.d("savedSpeakerCompanySpinner", "$savedSpeakerCompanySpinner")

        if (sharedPreferences.all.get("editdata1").toString() == binding2.editText1.text.toString() &&
            sharedPreferences.all.get("editdata2").toString() == binding2.editText2.text.toString() &&
            sharedPreferences.all.get("editdata5").toString() == binding2.editText5.text.toString() &&
            sharedPreferences.all.get("editdata6").toString() == binding2.editText6.text.toString() &&
            sharedPreferences.all.get("editdata8").toString() == binding2.editText8.text.toString() &&
            speakerCompanySpinner == savedSpeakerCompanySpinner && speakerModelSpinner == savedSpeakerModelSpinner &&
            lampCompanySpinner == savedLampCompanySpinner && lampModelSpinner == savedLampModelSpinner &&
            projectorCompanySpinner == savedProjectorCompanySpinner && projectorModelSpinner == savedProjectorModelSpinner &&
            projectorCompanySpinner2 == savedProjectorCompanySpinner2 && projectorModelSpinner2 == savedProjectorModelSpinner2 &&
            airconCompanySpinner == savedAirconCompanySpinner && airconModelSpinner == savedAirconModelSpinner) {
            Log.d("asdf save","최근 저장 & 값 변화 없음")
            super.onBackPressed()
        } else {
            Log.d("asdf save2","값 변화 생김")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("notice")
            builder.setMessage("저장하시겠습니까?")
            builder.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                save()
                Log.d("asdf save dialog cancle", "save")
            })
            builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
                Log.d("asdf save dialog cancle", "cancel")
                super.onBackPressed()
            })
            builder.show()
//            Toast.makeText(this,"quit",Toast.LENGTH_SHORT).show()
//            super.onBackPressed()
        }

    }

    // qr코드 함수
    fun runQRcodeReader() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("QR code load!")
        integrator.setCameraId(0)
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    // qr코드 생성하는거
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if (result.contents != null) {
                Toast.makeText(
                    this, "Scanned : ${result.contents} format: ${result.formatName}",
                    Toast.LENGTH_LONG
                ).show()

                Log.d("testt", "Scanned : ${result.contents}, format: ${result.formatName}")

                // when으로 위에서 qrButtonClickCheck에 할당한 값에 따라서 저장하도록 만듬
                when(qrButtonClickCheck) {
                    "button1" -> binding2.editText1.setText(result.contents)
                    "button2" -> binding2.editText2.setText(result.contents)
                    "button5" -> binding2.editText5.setText(result.contents)
                    "button6" -> binding2.editText6.setText(result.contents)
                    "button8" -> binding2.editText8.setText(result.contents)
                }
            }
            if(result.barcodeImagePath != null) {
                val bitmap = BitmapFactory.decodeFile(result.barcodeImagePath)
                //val imgView : ImageView = findViewById(R.id.scannedBitmap)
                //imgView.setImageBitmap(bitmap)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        //super.onActivityResult()
    }

    // 처음 실행하면 위에서 만든 speakerList에 값이 없어서 오류가 난다.
    // 그래서 여기서 처음에는 setSelection으로 값을 설정해주고
    // 저장버튼을 누르면 firstMovement 쉐어드 프리퍼런스가 바뀌면서 load함수로 저장된 값을 보여준다
    override fun onStart() {
        super.onStart()

        val sharedPreferences4 = getSharedPreferences("$roomName", Context.MODE_PRIVATE)
        val movement = sharedPreferences4.getBoolean("firstMovement", true)

        if(movement != true) {
            load()
        } else {
            binding2.spinner33.setSelection(1)
            binding2.spinner43.setSelection(1)
            binding2.spinner53.setSelection(1)
            binding2.spinner63.setSelection(1)
            binding2.spinner73.setSelection(1)
        }

    }
}















