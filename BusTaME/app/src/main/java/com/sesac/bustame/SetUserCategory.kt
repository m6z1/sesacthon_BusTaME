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
            val intent = Intent(this, SetUserOccupants::class.java)
            startActivity(intent)

            // 현재 액티비티 종료
            finish()
        }
    }
}