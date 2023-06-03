package com.sesac.bustame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

class BellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bell)
        setCustomToolbar(R.layout.custom_actionbar)
    }

    //툴바 띄우기
    fun setCustomToolbar(layout: Int) {
        val toolbar = findViewById<Toolbar>(layout)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }
}