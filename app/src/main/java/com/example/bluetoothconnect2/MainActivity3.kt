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
    var spinnerData8 : String = ""
    var spinnerData9 : String = ""
    var spinnerData10 : String = ""

    var spinnerData5_Device : String = ""
    var spinnerData5_Company : String = ""
    var spinnerData5_Model : String = ""

    var spinnerData6_Device : String = ""
    var spinnerData6_Company : String = ""
    var spinnerData6_Model : String = ""

    var spinnerData8_Device : String = ""
    var spinnerData8_Company : String = ""
    var spinnerData8_Model : String = ""

    var spinnerData9_Device : String = ""
    var spinnerData9_Company : String = ""
    var spinnerData9_Model : String = ""

    //var editData1 = StringBuffer()
    var editData1 = ""
    var editData2 = ""
    var editData3 = ""
    var editData4 = ""
    var editData5 = ""
    var editData6 = ""
    var editData7 = ""
    var editData8 = ""
    var editData9 = ""
    var editData10 = ""

    var spinnerSelect1 = 0
    var spinnerSelect2 = 0
    var spinnerSelect3 = 0
    var spinnerSelect4 = 0
    var spinnerSelect5 = 0
    var spinnerSelect6 = 0
    var spinnerSelect7 = 0
    var spinnerSelect8 = 0
    var spinnerSelect9 = 0
    var spinnerSelect10 = 0

    var spinnerSelect5_Device = 0
    var spinnerSelect5_Company = 0
    var spinnerSelect5_Model = 0

    var spinnerSelect6_Device = 0
    var spinnerSelect6_Company = 0
    var spinnerSelect6_Model = 0

    var spinnerSelect8_Device = 0
    var spinnerSelect8_Company = 0
    var spinnerSelect8_Model = 0

    var spinnerSelect9_Device = 0
    var spinnerSelect9_Company = 0
    var spinnerSelect9_Model = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)

        val abcd = intent.getStringExtra("abcd")
        val deviceId = intent.getStringExtra("deviceId")
        binding2.numberText.text = "Room $abcd"
        roomName = "$abcd"

        Thread {
            runOnUiThread {
                binding2.spinner1.setSelection(9)
                binding2.spinner2.setSelection(1)
                binding2.spinner3.setSelection(2)
                binding2.spinner4.setSelection(4)
                binding2.spinner5.setSelection(5)
                binding2.spinner6.setSelection(6)
                binding2.spinner7.setSelection(7)
                binding2.spinner8.setSelection(10)
                binding2.spinner9.setSelection(8)
                binding2.spinner10.setSelection(3)
            }
        }.start()

        val spinnerAdapter1 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner1.adapter = spinnerAdapter1
        binding2.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData1 = binding2.spinner1.getItemAtPosition(p2).toString()
                spinnerSelect1 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
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
                TODO("Not yet implemented")
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
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter4 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner4.adapter = spinnerAdapter4
        binding2.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData4 = binding2.spinner4.getItemAtPosition(p2).toString()
                spinnerSelect4 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter5 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner5.adapter = spinnerAdapter5
        binding2.spinner5.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5 = binding2.spinner5.getItemAtPosition(p2).toString()
                Log.d("asdf spinner5","$spinnerData5")
                Log.d("asdf spinner p0","$p0")
                Log.d("asdf spinner p1","$p1")
                Log.d("asdf spinner p2","$p2")
                Log.d("asdf spinner p3","$p3")
                spinnerSelect5 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter5_2 = ArrayAdapter.createFromResource(this,R.array.device,android.R.layout.simple_spinner_item)
        binding2.spinner52.adapter = spinnerAdapter5_2
        binding2.spinner52.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5_Device = binding2.spinner52.getItemAtPosition(p2).toString()
                Log.d("asdf spinnerData5_2","$spinnerData5_Device")
                spinnerSelect5_Device = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter5_3 = ArrayAdapter.createFromResource(this,R.array.company,android.R.layout.simple_spinner_item)
        binding2.spinner53.adapter = spinnerAdapter5_3
        binding2.spinner53.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5_Company = binding2.spinner53.getItemAtPosition(p2).toString()
                spinnerSelect5_Company = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter5_4 = ArrayAdapter.createFromResource(this,R.array.model,android.R.layout.simple_spinner_item)
        binding2.spinner54.adapter = spinnerAdapter5_4
        binding2.spinner54.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData5_Model = binding2.spinner54.getItemAtPosition(p2).toString()
                spinnerSelect5_Model = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter6 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner6.adapter = spinnerAdapter6
        binding2.spinner6.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6 = binding2.spinner6.getItemAtPosition(p2).toString()
                spinnerSelect6 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter6_2 = ArrayAdapter.createFromResource(this,R.array.device,android.R.layout.simple_spinner_item)
        binding2.spinner62.adapter = spinnerAdapter6_2
        binding2.spinner62.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6_Device = binding2.spinner62.getItemAtPosition(p2).toString()
                spinnerSelect6_Device = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter6_3 = ArrayAdapter.createFromResource(this,R.array.company,android.R.layout.simple_spinner_item)
        binding2.spinner63.adapter = spinnerAdapter6_3
        binding2.spinner63.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6_Company = binding2.spinner63.getItemAtPosition(p2).toString()
                spinnerSelect6_Company = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter6_4 = ArrayAdapter.createFromResource(this,R.array.model,android.R.layout.simple_spinner_item)
        binding2.spinner64.adapter = spinnerAdapter6_4
        binding2.spinner64.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData6_Model = binding2.spinner64.getItemAtPosition(p2).toString()
                spinnerSelect6_Model = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter7 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner7.adapter = spinnerAdapter7
        binding2.spinner7.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData7 = binding2.spinner7.getItemAtPosition(p2).toString()
                spinnerSelect7 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter8 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner8.adapter = spinnerAdapter8
        binding2.spinner8.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData8 = binding2.spinner8.getItemAtPosition(p2).toString()
                spinnerSelect8 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter8_2 = ArrayAdapter.createFromResource(this,R.array.device,android.R.layout.simple_spinner_item)
        binding2.spinner82.adapter = spinnerAdapter8_2
        binding2.spinner82.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData8_Device = binding2.spinner82.getItemAtPosition(p2).toString()
                spinnerSelect8_Device = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter8_3 = ArrayAdapter.createFromResource(this,R.array.company,android.R.layout.simple_spinner_item)
        binding2.spinner83.adapter = spinnerAdapter8_3
        binding2.spinner83.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData8_Company = binding2.spinner83.getItemAtPosition(p2).toString()
                spinnerSelect8_Company = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter8_4 = ArrayAdapter.createFromResource(this,R.array.model,android.R.layout.simple_spinner_item)
        binding2.spinner84.adapter = spinnerAdapter8_4
        binding2.spinner84.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData8_Model = binding2.spinner84.getItemAtPosition(p2).toString()
                spinnerSelect8_Model = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter9 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner9.adapter = spinnerAdapter9
        binding2.spinner9.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData9 = binding2.spinner9.getItemAtPosition(p2).toString()
                spinnerSelect9 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter9_2 = ArrayAdapter.createFromResource(this,R.array.device,android.R.layout.simple_spinner_item)
        binding2.spinner92.adapter = spinnerAdapter9_2
        binding2.spinner92.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData9_Device = binding2.spinner92.getItemAtPosition(p2).toString()
                spinnerSelect9_Device = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter9_3 = ArrayAdapter.createFromResource(this,R.array.company,android.R.layout.simple_spinner_item)
        binding2.spinner93.adapter = spinnerAdapter9_3
        binding2.spinner93.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData9_Company = binding2.spinner93.getItemAtPosition(p2).toString()
                spinnerSelect9_Company = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter9_4 = ArrayAdapter.createFromResource(this,R.array.model,android.R.layout.simple_spinner_item)
        binding2.spinner94.adapter = spinnerAdapter9_4
        binding2.spinner94.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData9_Model = binding2.spinner94.getItemAtPosition(p2).toString()
                spinnerSelect9_Model = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter10 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner10.adapter = spinnerAdapter10
        binding2.spinner10.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData10 = binding2.spinner10.getItemAtPosition(p2).toString()
                spinnerSelect10 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding2.btnClear.setOnClickListener {
            binding2.editText1.text = null
            binding2.editText2.text = null
            binding2.editText3.text = null
            binding2.editText4.text = null
            binding2.editText5.text = null
            binding2.editText6.text = null
            binding2.editText7.text = null
            binding2.editText8.text = null
            binding2.editText9.text = null
            binding2.editText10.text = null
        }

        binding2.btnSave.setOnClickListener {
            isSave++
            save()
        }

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
        val editTextValue10 = sharedPreferences.getString("load_nid_9", "")
        binding2.editText10.setText(editTextValue10.toString())

        val irkeyValue = sharedPreferences.getString("load_ir_key_4","")
        binding2.irKey1.setText(irkeyValue?.split("/")?.get(0))
        binding2.irKey2.setText(irkeyValue?.split("/")?.get(1))
        binding2.irKey3.setText(irkeyValue?.split("/")?.get(2))

        val irkeyValue2 = sharedPreferences.getString("load_ir_key_5","")
        binding2.irKey4.setText(irkeyValue2?.split("/")?.get(0))
        binding2.irKey5.setText(irkeyValue2?.split("/")?.get(1))
        binding2.irKey6.setText(irkeyValue2?.split("/")?.get(2))

        val irkeyValue3 = sharedPreferences.getString("load_ir_key_7","")
        binding2.irKey7.setText(irkeyValue3?.split("/")?.get(0))
        binding2.irKey8.setText(irkeyValue3?.split("/")?.get(1))
        binding2.irKey9.setText(irkeyValue3?.split("/")?.get(2))

        val irkeyValue4 = sharedPreferences.getString("load_ir_key_8","")
        binding2.irKey10.setText(irkeyValue4?.split("/")?.get(0))
        binding2.irKey11.setText(irkeyValue4?.split("/")?.get(1))
        binding2.irKey12.setText(irkeyValue4?.split("/")?.get(2))


        binding2.btnLoad.setOnClickListener {

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
            val spinnerValue8 = sharedPreferences.getInt("spinnerSelect8", 0)
            val editTextValue8 = sharedPreferences.getString("editdata8", "")
            val spinnerValue9 = sharedPreferences.getInt("spinnerSelect9", 0)
            val editTextValue9 = sharedPreferences.getString("editdata9", "")
            val spinnerValue10 = sharedPreferences.getInt("spinnerSelect10", 0)
            val editTextValue10 = sharedPreferences.getString("editdata10", "")

            val spinnerValue5_2 = sharedPreferences.getInt("spinnerSelect5_Device", 0)
            val spinnerValue5_3 = sharedPreferences.getInt("spinnerSelect5_Company", 0)
            val spinnerValue5_4 = sharedPreferences.getInt("spinnerSelect5_Model", 0)

            val spinnerValue6_2 = sharedPreferences.getInt("spinnerSelect6_Device", 0)
            val spinnerValue6_3 = sharedPreferences.getInt("spinnerSelect6_Company", 0)
            val spinnerValue6_4 = sharedPreferences.getInt("spinnerSelect6_Model", 0)

            val spinnerValue8_2 = sharedPreferences.getInt("spinnerSelect8_Device", 0)
            val spinnerValue8_3 = sharedPreferences.getInt("spinnerSelect8_Company", 0)
            val spinnerValue8_4 = sharedPreferences.getInt("spinnerSelect8_Model", 0)

            val spinnerValue9_2 = sharedPreferences.getInt("spinnerSelect9_Device", 0)
            val spinnerValue9_3 = sharedPreferences.getInt("spinnerSelect9_Company", 0)
            val spinnerValue9_4 = sharedPreferences.getInt("spinnerSelect9_Model", 0)

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
            binding2.editText8.setText(editTextValue8.toString())
            binding2.spinner8.setSelection(spinnerValue8)
            binding2.editText9.setText(editTextValue9.toString())
            binding2.spinner9.setSelection(spinnerValue9)
            binding2.editText10.setText(editTextValue10.toString())
            binding2.spinner10.setSelection(spinnerValue10)

            binding2.spinner52.setSelection(spinnerValue5_2)
            binding2.spinner53.setSelection(spinnerValue5_3)
            binding2.spinner54.setSelection(spinnerValue5_4)

            binding2.spinner62.setSelection(spinnerValue6_2)
            binding2.spinner63.setSelection(spinnerValue6_3)
            binding2.spinner64.setSelection(spinnerValue6_4)

            binding2.spinner82.setSelection(spinnerValue8_2)
            binding2.spinner83.setSelection(spinnerValue8_3)
            binding2.spinner84.setSelection(spinnerValue8_4)

            binding2.spinner92.setSelection(spinnerValue9_2)
            binding2.spinner93.setSelection(spinnerValue9_3)
            binding2.spinner94.setSelection(spinnerValue9_4)
        }


    }

    fun save() {
        editData1 = binding2.editText1.text.toString()
        editData2 = binding2.editText2.text.toString()
        editData3 = binding2.editText3.text.toString()
        editData4 = binding2.editText4.text.toString()
        editData5 = binding2.editText5.text.toString()
        editData6 = binding2.editText6.text.toString()
        editData7 = binding2.editText7.text.toString()
        editData8 = binding2.editText8.text.toString()
        editData9 = binding2.editText9.text.toString()
        editData10 = binding2.editText10.text.toString()

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
        editor.putInt("spinnerSelect8",spinnerSelect8)
        editor.putString("editdata8",editData8)
        editor.putInt("spinnerSelect9",spinnerSelect9)
        editor.putString("editdata9",editData9)
        editor.putInt("spinnerSelect10",spinnerSelect10)
        editor.putString("editdata10",editData10)

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

        editor.putInt("spinnerSelect8_Device",spinnerSelect8_Device)
        editor.putString("spinnerData8_Device", spinnerData8_Device)
        editor.putInt("spinnerSelect8_Company",spinnerSelect8_Company)
        editor.putString("spinnerData8_Company", spinnerData8_Company)
        editor.putInt("spinnerSelect8_Model",spinnerSelect8_Model)
        editor.putString("spinnerData8_Model", spinnerData8_Model)

        editor.putInt("spinnerSelect9_Device",spinnerSelect9_Device)
        editor.putString("spinnerData9_Device", spinnerData9_Device)
        editor.putInt("spinnerSelect9_Company",spinnerSelect9_Company)
        editor.putString("spinnerData9_Company", spinnerData9_Company)
        editor.putInt("spinnerSelect9_Model",spinnerSelect9_Model)
        editor.putString("spinnerData9_Model", spinnerData9_Model)


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
        intent.putExtra("spinner8","$spinnerData8")
        intent.putExtra("editdata8","$editData8")
        intent.putExtra("spinner9","$spinnerData9")
        intent.putExtra("editdata9","$editData9")
        intent.putExtra("spinner10","$spinnerData10")
        intent.putExtra("editdata10","$editData10")

        intent.putExtra("roomName", roomName)

        setResult(RESULT_OK, intent)
        //finish()
        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show()
        Log.d("asdf saveroomname", "$roomName")
//        val editTextValue = sharedPreferences.getString("editdata1", "")
//        Log.d("asdf value", "${editTextValue}")
    }

    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences(roomName, Context.MODE_PRIVATE)

        if(sharedPreferences.all.get("editdata1").toString() == binding2.editText1.text.toString() &&
            sharedPreferences.all.get("editdata2").toString() == binding2.editText2.text.toString() &&
            sharedPreferences.all.get("editdata3").toString() == binding2.editText3.text.toString() &&
            sharedPreferences.all.get("editdata4").toString() == binding2.editText4.text.toString() &&
            sharedPreferences.all.get("editdata5").toString() == binding2.editText5.text.toString() &&
            sharedPreferences.all.get("editdata6").toString() == binding2.editText6.text.toString() &&
            sharedPreferences.all.get("editdata7").toString() == binding2.editText7.text.toString() &&
            sharedPreferences.all.get("editdata8").toString() == binding2.editText8.text.toString() &&
            sharedPreferences.all.get("editdata9").toString() == binding2.editText9.text.toString() &&
            sharedPreferences.all.get("editdata10").toString() == binding2.editText10.text.toString()) {
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















