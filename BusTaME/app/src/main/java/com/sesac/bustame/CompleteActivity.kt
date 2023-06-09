package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivityCompleteBinding

class CompleteActivity : AppCompatActivity() {

    private lateinit var messageValue: String
    private lateinit var passengerTypeValue: String

    private lateinit var binding: ActivityCompleteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트에서 값을 가져옴
        messageValue = intent.getStringExtra(BusRideBell.BUS_MESSAGE_KEY).toString()
        passengerTypeValue = intent.getStringExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY).toString()

        binding.btnFinal.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY, passengerTypeValue)
            intent.putExtra(BusRideBell.BUS_MESSAGE_KEY, messageValue)
            startActivity(intent)
            startActivity(intent)
        }
    }
}