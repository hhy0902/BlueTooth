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
import androidx.core.view.get
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

    //var editData1 = StringBuffer()
    var editData1 : String = ""
    var editData2 : String = ""
    var editData3 : String = ""
    var editData4 : String = ""
    var editData5 : String = ""
    var editData6 : String = ""
    var editData7 : String = ""
    var editData8 : String = ""
    var editData9 : String = ""
    var editData10 : String = ""

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)

        val abcd = intent.getStringExtra("abcd")
        val deviceId = intent.getStringExtra("deviceId")
        binding2.numberText.text = "Room $abcd"
        roomName = "$abcd"

        val roomSize = intent.getIntExtra("roomsize",0)

        Thread {
            runOnUiThread {
                binding2.spinner1.setSelection(9)
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
                spinnerSelect5 = p2
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

        binding2.editText1.setText(editTextValue)
        binding2.spinner1.setSelection(spinnerValue)
        binding2.editText2.setText(editTextValue2)
        binding2.spinner2.setSelection(spinnerValue2)
        binding2.editText3.setText(editTextValue3)
        binding2.spinner3.setSelection(spinnerValue3)
        binding2.editText4.setText(editTextValue4)
        binding2.spinner4.setSelection(spinnerValue4)
        binding2.editText5.setText(editTextValue5)
        binding2.spinner5.setSelection(spinnerValue5)
        binding2.editText6.setText(editTextValue6)
        binding2.spinner6.setSelection(spinnerValue6)

        binding2.editText7.setText(editTextValue7)
        binding2.spinner7.setSelection(spinnerValue7)
        binding2.editText8.setText(editTextValue8)
        binding2.spinner8.setSelection(spinnerValue8)
        binding2.editText9.setText(editTextValue9)
        binding2.spinner9.setSelection(spinnerValue9)
        binding2.editText10.setText(editTextValue10)
        binding2.spinner10.setSelection(spinnerValue10)

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
        val editTextValue = sharedPreferences.getString("editdata1", "")
        Log.d("asdf value", "${editTextValue}")
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















