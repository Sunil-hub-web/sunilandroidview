package com.surya.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    var age = ""
    var name = ""
    var gender = ""

    lateinit var tv_name : TextView
    lateinit var tv_age : TextView
    lateinit var tv_gender1 : TextView
    lateinit var tv_gender : SwitchCompat
    lateinit var btn_save : Button

    lateinit var et_name : EditText
    lateinit var et_age : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get reference to our userManager class
        userManager = UserManager(this)

        tv_gender = findViewById(R.id.switch_gender)
        tv_gender1 = findViewById(R.id.tv_gender)
        tv_age = findViewById(R.id.tv_age)
        tv_name = findViewById(R.id.tv_name)
        btn_save = findViewById(R.id.btn_save)

        et_name = findViewById(R.id.et_name)
        et_age = findViewById(R.id.et_age)

        buttonSave()

        observeData()
    }

    fun buttonSave(){

        btn_save.setOnClickListener{

            name = et_name.text.toString().trim()
            age = et_age.text.toString().trim()
            val isMail = tv_gender.isChecked

            GlobalScope.launch {
                userManager.storeUser(name,age,isMail)
            }

        }

    }

    fun observeData(){

        userManager.userNameFlow.asLiveData().observe(this,{

            name = it
            tv_name.text = name.toString()
        })

        userManager.userAgeFlow.asLiveData().observe(this,{

            age = it
            tv_age.text = age.toString()
        })

        userManager.userGenderFlow.asLiveData().observe(this,{

            gender = if(it) "Male" else "FeMale"
            tv_gender1.text = gender.toString()
        })
    }
}