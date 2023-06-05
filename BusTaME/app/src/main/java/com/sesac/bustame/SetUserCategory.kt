package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivitySetUserCategoryBinding

class SetUserCategory : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrev.setOnClickListener {
            // 이전 액티비티로
            finish()
        }
        
        binding.btnNext.setOnClickListener {
            val intent = Intent(this, SetUserToDelivery::class.java)
            startActivity(intent)
        }
    }
}