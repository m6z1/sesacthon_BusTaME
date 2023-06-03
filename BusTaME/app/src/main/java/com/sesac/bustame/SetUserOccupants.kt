package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivitySetOccupantsBinding

class SetUserOccupants : AppCompatActivity() {

    private lateinit var binding: ActivitySetOccupantsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetOccupantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SetUserCategory::class.java)
            startActivity(intent)
        }
    }
}