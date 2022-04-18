package com.example.bluetoothconnect2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.bluetoothconnect2.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private val binding2 by lazy {
        ActivityMain3Binding.inflate(layoutInflater)
    }


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
    var spinnerData11 : String = ""
    var spinnerData12 : String = ""


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
    var editData11 : String = ""
    var editData12 : String = ""

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
    var spinnerSelect11 = 0
    var spinnerSelect12 = 0

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding2.root)


        val abcd = intent.getStringExtra("abcd")
        binding2.numberText.text = abcd
        roomName = abcd.toString()

        val roomSize = intent.getIntExtra("roomsize",0)
        Toast.makeText(this,"$roomSize",Toast.LENGTH_SHORT).show()

        val spinnerAdapter1 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner1.adapter = spinnerAdapter1
        binding2.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData1 = binding2.spinner1.getItemAtPosition(p2).toString()
                spinnerSelect1 = p2
                //spinnerData1.append(binding2.spinner1.getItemAtPosition(p2).toString())
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

        val spinnerAdapter11 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner11.adapter = spinnerAdapter11
        binding2.spinner11.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData11 = binding2.spinner11.getItemAtPosition(p2).toString()
                spinnerSelect11 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val spinnerAdapter12 = ArrayAdapter.createFromResource(this,R.array.planets_array,android.R.layout.simple_spinner_item)
        binding2.spinner12.adapter = spinnerAdapter12
        binding2.spinner12.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerData12 = binding2.spinner12.getItemAtPosition(p2).toString()
                spinnerSelect12 = p2
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding2.btnSave.setOnClickListener {
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
            editData11 = binding2.editText11.text.toString()
            editData12 = binding2.editText12.text.toString()

            val sharedPreferences = getSharedPreferences(abcd.toString(), Context.MODE_PRIVATE)
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
            editor.putInt("spinnerSelect11",spinnerSelect11)
            editor.putString("editdata11",editData11)
            editor.putInt("spinnerSelect12",spinnerSelect12)
            editor.putString("editdata12",editData12)
            editor.commit()

            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("spinner1","$spinnerData1, $spinnerData2, $spinnerData3, $spinnerData4, $spinnerData5, $spinnerData6")
            intent.putExtra("editdata1","$editData1, $editData2, $editData3, $editData4, $editData5, $editData6")
            intent.putExtra("roomSizeValue",roomSize)

            setResult(RESULT_OK, intent)
            finish()

        }

        val sharedPreferences = getSharedPreferences(abcd.toString(), Context.MODE_PRIVATE)

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
        val spinnerValue11 = sharedPreferences.getInt("spinnerSelect11", 0)
        val editTextValue11 = sharedPreferences.getString("editdata11", "")
        val spinnerValue12 = sharedPreferences.getInt("spinnerSelect12", 0)
        val editTextValue12 = sharedPreferences.getString("editdata12", "")


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
        binding2.editText11.setText(editTextValue11)
        binding2.spinner11.setSelection(spinnerValue11)
        binding2.editText12.setText(editTextValue12)
        binding2.spinner12.setSelection(spinnerValue12)

        Log.d("asdf share", spinnerValue.toString())
        Log.d("asdf share", editTextValue.toString())


    }

    override fun onBackPressed() {

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
        editData11 = binding2.editText11.text.toString()
        editData12 = binding2.editText12.text.toString()

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
        editor.putInt("spinnerSelect11",spinnerSelect11)
        editor.putString("editdata11",editData11)
        editor.putInt("spinnerSelect12",spinnerSelect12)
        editor.putString("editdata12",editData12)
        editor.commit()

        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("spinner1","$spinnerData1 $spinnerData2 $spinnerData3 $spinnerData4 $spinnerData5 $spinnerData6 $spinnerData7 $spinnerData8 $spinnerData9 $spinnerData10 $spinnerData11 $spinnerData12")
        intent.putExtra("editdata1","$editData1 $editData2 $editData3 $editData4 $editData5 $editData6 $editData7 $editData8 $editData9 $editData10 $editData11 $editData12")
        setResult(RESULT_OK, intent)
        finish()

        Toast.makeText(this,"save",Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }
}















