package com.sesac.bustame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.databinding.ActivitySetUserToDeliveryBinding

class SetUserToDelivery : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserToDeliveryBinding

    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserToDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttons = listOf(
            binding.btnWait,
            binding.btnChair,
            binding.btnBabycar,
            binding.btnNothing
        )

        for (button in buttons) {
            button.setOnClickListener {
                if (selectedButton != button) {
                    selectedButton?.isSelected = true
                    selectedButton?.setTextColor(getColor(R.color.white))
                    selectedButton?.setBackgroundColor(getColor(R.color.btnGreen))
                    binding.btnNext.isEnabled = true
                }
            }
        }

        binding.btnPrev.setOnClickListener {
            finish()
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, CompleteActivity::class.java)
            startActivity(intent)
        }
    }
}
