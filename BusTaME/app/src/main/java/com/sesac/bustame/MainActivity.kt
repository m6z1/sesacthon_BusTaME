package com.sesac.bustame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import net.daum.mf.map.api.MapView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapView = MapView(this)

        val mapViewContainer = findViewById<ViewGroup>(R.id.mapView)
        mapViewContainer.addView(mapView)

    }
}