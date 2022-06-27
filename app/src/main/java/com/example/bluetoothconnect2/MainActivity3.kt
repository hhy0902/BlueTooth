package com.example.bluetoothconnect2

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.bluetoothconnect2.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private val binding2 by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
    }

    var isSave = 0

    var roomName = ""

    var spinnerData1 : String = ""
    var spinnerData2 : String = ""
    var spinnerData3 : String = ""
    var spinnerData4 : String = ""
    var spinnerData5 : String = ""
    var spinnerData6 : String = ""
    var spinnerData7 : String = ""

    var spinnerData4_Device : String = "lighting"
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


    //var editData1 = StringBuffer()
    var editData1 = ""
    var editData2 = ""
    var editData3 = ""
    var editData4 = ""
    var editData5 = ""
    var editData6 = ""
    var editData7 = ""


    var spinnerSelect1 = 0
    var spinnerSelect2 = 0
    var spinnerSelect3 = 0
    var spinnerSelect4 = 0
    var spinnerSelect5 = 0
    var spinnerSelect6 = 0
    var spinnerSelect7 = 0

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)

        val abcd = intent.getStringExtra("abcd")
        val deviceId = intent.getStringExtra("deviceId")
        binding2.numberText.text = "Room $abcd"
        roomName = "$abcd"

        // 스피너 값 초기 설정해주기
        Thread {
            runOnUiThread {
                binding2.spinner1.setSelection(9)
                binding2.spinner2.setSelection(1)
                binding2.spinner3.setSelection(4)
                binding2.spinner4.setSelection(8)
                binding2.spinner5.setSelection(5)
                binding2.spinner6.setSelection(6)
                binding2.spinner7.setSelection(10)
//                binding2.spinner42.setSelection(1)
//                binding2.spinner52.setSelection(1)
//                binding2.spinner62.setSelection(1)
//                binding2.spinner72.setSelection(1)
            }
        }.start()

        // 스피너 설정
        val spinnerAdapter1 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner1.adapter = spinnerAdapter1
        binding2.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData1 = binding2.spinner1.getItemAtPosition(p2).toString()
                spinnerSelect1 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spinnerAdapter2 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner2.adapter = spinnerAdapter2
        binding2.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData2 = binding2.spinner2.getItemAtPosition(p2).toString()
                spinnerSelect2 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spinnerAdapter3 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner3.adapter = spinnerAdapter3
        binding2.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData3 = binding2.spinner3.getItemAtPosition(p2).toString()
                spinnerSelect3 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val spinnerAdapter4 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner4.adapter = spinnerAdapter4
        binding2.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData4 = binding2.spinner4.getItemAtPosition(p2).toString()
                spinnerSelect4 = p2
                Log.d("asdf spinner", "$p2")

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


//        val spinnerAdapter4_2 = ArrayAdapter.createFromResource(this,R.array.lighting,android.R.layout.simple_spinner_item)
//        binding2.spinner42.adapter = spinnerAdapter4_2
//        binding2.spinner42.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData4_Device = binding2.spinner42.getItemAtPosition(p2).toString()
//                spinnerSelect4_Device = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }


        val spinnerAdapter4_3 = ArrayAdapter.createFromResource(this,R.array.lighting_company,android.R.layout.simple_spinner_item)
        binding2.spinner43.adapter = spinnerAdapter4_3
        binding2.spinner43.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData4_Company = binding2.spinner43.getItemAtPosition(p2).toString()
                spinnerSelect4_Company = p2

                when(p2) {
                    1 -> {
                        val spinnerAdapter4_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.lighting_model_lg,android.R.layout.simple_spinner_item)
                        binding2.spinner44.adapter = spinnerAdapter4_4
                        binding2.spinner44.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData4_Model = binding2.spinner44.getItemAtPosition(p2).toString()
                                spinnerSelect4_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    2 -> {
                        val spinnerAdapter4_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.lighting_model_kt,android.R.layout.simple_spinner_item)
                        binding2.spinner44.adapter = spinnerAdapter4_4
                        binding2.spinner44.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData4_Model = binding2.spinner44.getItemAtPosition(p2).toString()
                                spinnerSelect4_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    3 -> {
                        val spinnerAdapter4_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.lighting_model_skt,android.R.layout.simple_spinner_item)
                        binding2.spinner44.adapter = spinnerAdapter4_4
                        binding2.spinner44.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData4_Model = binding2.spinner44.getItemAtPosition(p2).toString()
                                spinnerSelect4_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    else -> {}
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter4_4 = ArrayAdapter.createFromResource(this,R.array.lighting_model,android.R.layout.simple_spinner_item)
//        binding2.spinner44.adapter = spinnerAdapter4_4
//        binding2.spinner44.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData4_Model = binding2.spinner44.getItemAtPosition(p2).toString()
//                spinnerSelect4_Model = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        val spinnerAdapter5 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner5.adapter = spinnerAdapter5
        binding2.spinner5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5 = binding2.spinner5.getItemAtPosition(p2).toString()
                spinnerSelect5 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter5_2 = ArrayAdapter.createFromResource(this,R.array.projector,android.R.layout.simple_spinner_item)
//        binding2.spinner52.adapter = spinnerAdapter5_2
//        binding2.spinner52.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData5_Device = binding2.spinner52.getItemAtPosition(p2).toString()
//                spinnerSelect5_Device = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }



        val spinnerAdapter5_3 = ArrayAdapter.createFromResource(this,R.array.projector_company,android.R.layout.simple_spinner_item)
        binding2.spinner53.adapter = spinnerAdapter5_3
        binding2.spinner53.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5_Company = binding2.spinner53.getItemAtPosition(p2).toString()
                spinnerSelect5_Company = p2

                when(p2) {
                    1 -> {
                        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model,android.R.layout.simple_spinner_item)
                        binding2.spinner54.adapter = spinnerAdapter5_4
                        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                                spinnerSelect5_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    2 -> {
                        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_aaa,android.R.layout.simple_spinner_item)
                        binding2.spinner54.adapter = spinnerAdapter5_4
                        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                                spinnerSelect5_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    3 -> {
                        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_bbb,android.R.layout.simple_spinner_item)
                        binding2.spinner54.adapter = spinnerAdapter5_4
                        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                                spinnerSelect5_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    4 -> {
                        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_ccc,android.R.layout.simple_spinner_item)
                        binding2.spinner54.adapter = spinnerAdapter5_4
                        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                                spinnerSelect5_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this,R.array.projector_model,android.R.layout.simple_spinner_item)
//        binding2.spinner54.adapter = spinnerAdapter5_4
//        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
//                spinnerSelect5_Model = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        val spinnerAdapter6 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner6.adapter = spinnerAdapter6
        binding2.spinner6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6 = binding2.spinner6.getItemAtPosition(p2).toString()
                spinnerSelect6 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter6_2 = ArrayAdapter.createFromResource(this,R.array.projector,android.R.layout.simple_spinner_item)
//        binding2.spinner62.adapter = spinnerAdapter6_2
//        binding2.spinner62.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData6_Device = binding2.spinner62.getItemAtPosition(p2).toString()
//                spinnerSelect6_Device = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        val spinnerAdapter6_3 = ArrayAdapter.createFromResource(this,R.array.projector_company,android.R.layout.simple_spinner_item)
        binding2.spinner63.adapter = spinnerAdapter6_3
        binding2.spinner63.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6_Company = binding2.spinner63.getItemAtPosition(p2).toString()
                spinnerSelect6_Company = p2

                when(p2) {
                    1 -> {
                        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model,android.R.layout.simple_spinner_item)
                        binding2.spinner64.adapter = spinnerAdapter6_4
                        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                                spinnerSelect6_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    2 -> {
                        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_aaa,android.R.layout.simple_spinner_item)
                        binding2.spinner64.adapter = spinnerAdapter6_4
                        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                                spinnerSelect6_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    3 -> {
                        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_bbb,android.R.layout.simple_spinner_item)
                        binding2.spinner64.adapter = spinnerAdapter6_4
                        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                                spinnerSelect6_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    4 -> {
                        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.projector_model_ccc,android.R.layout.simple_spinner_item)
                        binding2.spinner64.adapter = spinnerAdapter6_4
                        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                                spinnerSelect6_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this,R.array.projector_model,android.R.layout.simple_spinner_item)
//        binding2.spinner64.adapter = spinnerAdapter6_4
//        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
//                spinnerSelect6_Model = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        val spinnerAdapter7 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner7.adapter = spinnerAdapter7
        binding2.spinner7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData7 = binding2.spinner7.getItemAtPosition(p2).toString()
                spinnerSelect7 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter7_2 = ArrayAdapter.createFromResource(this,R.array.air_conditioner,android.R.layout.simple_spinner_item)
//        binding2.spinner72.adapter = spinnerAdapter7_2
//        binding2.spinner72.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData7_Device = binding2.spinner72.getItemAtPosition(p2).toString()
//                spinnerSelect7_Device = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        val spinnerAdapter7_3 = ArrayAdapter.createFromResource(this,R.array.air_conditioner_company,android.R.layout.simple_spinner_item)
        binding2.spinner73.adapter = spinnerAdapter7_3
        binding2.spinner73.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData7_Company = binding2.spinner73.getItemAtPosition(p2).toString()
                spinnerSelect7_Company = p2

                when(p2) {
                    1 -> {
                        val spinnerAdapter7_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.air_conditioner_model_lg,android.R.layout.simple_spinner_item)
                        binding2.spinner74.adapter = spinnerAdapter7_4
                        binding2.spinner74.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData7_Model = binding2.spinner74.getItemAtPosition(p2).toString()
                                spinnerSelect7_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                    2 -> {
                        val spinnerAdapter7_4 = ArrayAdapter.createFromResource(this@MainActivity3,R.array.air_conditioner_model_samsung,android.R.layout.simple_spinner_item)
                        binding2.spinner74.adapter = spinnerAdapter7_4
                        binding2.spinner74.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                                spinnerData7_Model = binding2.spinner74.getItemAtPosition(p2).toString()
                                spinnerSelect7_Model = p2
                            }
                            override fun onNothingSelected(p0: AdapterView<*>?) {

                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

//        val spinnerAdapter7_4 = ArrayAdapter.createFromResource(this,R.array.air_conditioner_model,android.R.layout.simple_spinner_item)
//        binding2.spinner74.adapter = spinnerAdapter7_4
//        binding2.spinner74.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                spinnerData7_Model = binding2.spinner74.getItemAtPosition(p2).toString()
//                spinnerSelect7_Model = p2
//            }
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

        // 클리어 버튼 에디트 텍스트 값을 초기화 해줌
        binding2.btnClear.setOnClickListener {
            binding2.editText1.text = null
            binding2.editText2.text = null
            binding2.editText3.text = null
            binding2.editText4.text = null
            binding2.editText5.text = null
            binding2.editText6.text = null
            binding2.editText7.text = null

        }

        // 세이브 버튼
        binding2.btnSave.setOnClickListener {
            isSave++
            save()
        }

        // 저장된 값을 불러오는 로드
        load()

        val sharedPreferences2 = getSharedPreferences("LoadToF", Context.MODE_PRIVATE)
        val LoadToF = sharedPreferences2.getBoolean("btnLoadClick", false)

        // 메인 페이지에서 만약 load버튼을 눌렀으면 LoadToF가 true 아니면 false
        // 만약 true면 gateway에서 가져온 값을 보여주고 false면 기존에 저장된 값을 보여준다
        if (LoadToF) {
            val sharedPreferences = getSharedPreferences("load$roomName", Context.MODE_PRIVATE)

            val editTextValue1 = sharedPreferences.getString("load_blaster_$roomName", "")
            binding2.editText1.setText(editTextValue1.toString())

            val editTextValue2 = sharedPreferences.getString("load_nid_1", "")
            binding2.editText2.setText(editTextValue2.toString())
            val editTextValue3 = sharedPreferences.getString("load_nid_2", "")
            binding2.editText3.setText(editTextValue3.toString())
            val editTextValue4 = sharedPreferences.getString("load_nid_3", "")
            binding2.editText4.setText(editTextValue4.toString())
            val editTextValue5 = sharedPreferences.getString("load_nid_4", "")
            binding2.editText5.setText(editTextValue5.toString())
            val editTextValue6 = sharedPreferences.getString("load_nid_5", "")
            binding2.editText6.setText(editTextValue6.toString())
            val editTextValue7 = sharedPreferences.getString("load_nid_6", "")
            binding2.editText7.setText(editTextValue7.toString())

            // load 버튼을 눌러 true로 변경되었고 gateway에서 가져온 값을 보여줬다면 값을 다시 false로 초기화해준다
            val editor : SharedPreferences.Editor = sharedPreferences2.edit()
            editor.putBoolean("btnLoadClick",false)
            editor.commit()

        }

        // 저장된 값을 불러오는 load 버튼
        binding2.btnLoad.setOnClickListener {
            load()
        }
    }

    // 저장된 값을 불러온다
    fun load() {
        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        val spinnerValue = sharedPreferences.getInt("spinnerSelect1", 0)
        val editTextValue = sharedPreferences.getString("editdata1", "")
        val spinnerValue2 = sharedPreferences.getInt("spinnerSelect2", 0)
        val editTextValue2 = sharedPreferences.getString("editdata2", "")
        val spinnerValue3 = sharedPreferences.getInt("spinnerSelect3", 0)
        val editTextValue3 = sharedPreferences.getString("editdata3", "")
        val spinnerValue4 = sharedPreferences.getInt("spinnerSelect4", 0)
        val editTextValue4 = sharedPreferences.getString("editdata4", "")
        val spinnerValue5 = sharedPreferences.getInt("spinnerSelect5", 0)
        val editTextValue5 = sharedPreferences.getString("editdata5", "")
        val spinnerValue6 = sharedPreferences.getInt("spinnerSelect6", 0)
        val editTextValue6 = sharedPreferences.getString("editdata6", "")
        val spinnerValue7 = sharedPreferences.getInt("spinnerSelect7", 0)
        val editTextValue7 = sharedPreferences.getString("editdata7", "")

        val spinnerValue5_2 = sharedPreferences.getInt("spinnerSelect5_Device", 0)
        val spinnerValue5_3 = sharedPreferences.getInt("spinnerSelect5_Company", 0)
        val spinnerValue5_4 = sharedPreferences.getInt("spinnerSelect5_Model", 0)

        val spinnerValue6_2 = sharedPreferences.getInt("spinnerSelect6_Device", 0)
        val spinnerValue6_3 = sharedPreferences.getInt("spinnerSelect6_Company", 0)
        val spinnerValue6_4 = sharedPreferences.getInt("spinnerSelect6_Model", 0)

        val spinnerValue4_2 = sharedPreferences.getInt("spinnerSelect4_Device", 0)
        val spinnerValue4_3 = sharedPreferences.getInt("spinnerSelect4_Company", 0)
        val spinnerValue4_4 = sharedPreferences.getInt("spinnerSelect4_Model", 0)

        val spinnerValue7_2 = sharedPreferences.getInt("spinnerSelect7_Device", 0)
        val spinnerValue7_3 = sharedPreferences.getInt("spinnerSelect7_Company", 0)
        val spinnerValue7_4 = sharedPreferences.getInt("spinnerSelect7_Model", 0)

        binding2.editText1.setText(editTextValue.toString())
        binding2.spinner1.setSelection(spinnerValue)
        binding2.editText2.setText(editTextValue2.toString())
        binding2.spinner2.setSelection(spinnerValue2)
        binding2.editText3.setText(editTextValue3.toString())
        binding2.spinner3.setSelection(spinnerValue3)
        binding2.editText4.setText(editTextValue4.toString())
        binding2.spinner4.setSelection(spinnerValue4)
        binding2.editText5.setText(editTextValue5.toString())
        binding2.spinner5.setSelection(spinnerValue5)
        binding2.editText6.setText(editTextValue6.toString())
        binding2.spinner6.setSelection(spinnerValue6)

        binding2.editText7.setText(editTextValue7.toString())
        binding2.spinner7.setSelection(spinnerValue7)

        //binding2.spinner42.setSelection(spinnerValue4_2)
        binding2.spinner43.setSelection(spinnerValue4_3)
        binding2.spinner44.setSelection(spinnerValue4_4)

        //binding2.spinner52.setSelection(spinnerValue5_2)
        binding2.spinner53.setSelection(spinnerValue5_3)
        binding2.spinner54.setSelection(spinnerValue5_4)

        //binding2.spinner62.setSelection(spinnerValue6_2)
        binding2.spinner63.setSelection(spinnerValue6_3)
        binding2.spinner64.setSelection(spinnerValue6_4)

        //binding2.spinner72.setSelection(spinnerValue7_2)
        binding2.spinner73.setSelection(spinnerValue7_3)
        binding2.spinner74.setSelection(spinnerValue7_4)

    }

    // 값을 쉐어드 프리퍼런스로 저장하는 함수
    fun save() {
        editData1 = binding2.editText1.text.toString()
        editData2 = binding2.editText2.text.toString()
        editData3 = binding2.editText3.text.toString()
        editData4 = binding2.editText4.text.toString()
        editData5 = binding2.editText5.text.toString()
        editData6 = binding2.editText6.text.toString()
        editData7 = binding2.editText7.text.toString()


        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt("spinnerSelect1",spinnerSelect1)
        editor.putString("editdata1",editData1)
        editor.putInt("spinnerSelect2",spinnerSelect2)
        editor.putString("editdata2",editData2)
        editor.putInt("spinnerSelect3",spinnerSelect3)
        editor.putString("editdata3",editData3)
        editor.putInt("spinnerSelect4",spinnerSelect4)
        editor.putString("editdata4",editData4)
        editor.putInt("spinnerSelect5",spinnerSelect5)
        editor.putString("editdata5",editData5)
        editor.putInt("spinnerSelect6",spinnerSelect6)
        editor.putString("editdata6",editData6)
        editor.putInt("spinnerSelect7",spinnerSelect7)
        editor.putString("editdata7",editData7)

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
        intent.putExtra("spinner1","$spinnerData1")
        intent.putExtra("editdata1","$editData1")
        intent.putExtra("spinner2","$spinnerData2")
        intent.putExtra("editdata2","$editData2")
        intent.putExtra("spinner3","$spinnerData3")
        intent.putExtra("editdata3","$editData3")
        intent.putExtra("spinner4","$spinnerData4")
        intent.putExtra("editdata4","$editData4")
        intent.putExtra("spinner5","$spinnerData5")
        intent.putExtra("editdata5","$editData5")
        intent.putExtra("spinner6","$spinnerData6")
        intent.putExtra("editdata6","$editData6")
        intent.putExtra("spinner7","$spinnerData7")
        intent.putExtra("editdata7","$editData7")


        intent.putExtra("roomName", roomName)

        setResult(RESULT_OK, intent)
        //finish()
        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show()
        Log.d("asdf saveroomname", "$roomName")
//        val editTextValue = sharedPreferences.getString("editdata1", "")
//        Log.d("asdf value", "${editTextValue}")
    }

    // 뒤로가기 버튼을 누르면 저장된 값과 현재 editText에 있는 값을 비교하여
    // 만약 변화가 생겼을 경우 저장하겠냐고 하는 다이얼로그 생성 변화가 없으면 그대로 나가기
    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        if(sharedPreferences.all.get("editdata1").toString() == binding2.editText1.text.toString() &&
            sharedPreferences.all.get("editdata2").toString() == binding2.editText2.text.toString() &&
            sharedPreferences.all.get("editdata3").toString() == binding2.editText3.text.toString() &&
            sharedPreferences.all.get("editdata4").toString() == binding2.editText4.text.toString() &&
            sharedPreferences.all.get("editdata5").toString() == binding2.editText5.text.toString() &&
            sharedPreferences.all.get("editdata6").toString() == binding2.editText6.text.toString() &&
            sharedPreferences.all.get("editdata7").toString() == binding2.editText7.text.toString()) {
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
                //isSave = 0
            })
            builder.show()
//            Toast.makeText(this,"quit",Toast.LENGTH_SHORT).show()
//            super.onBackPressed()
        }

    }
}















