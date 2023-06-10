package com.sesac.bustame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.databinding.ActivitySetUserToDeliveryBinding

class SetUserToDelivery : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserToDeliveryBinding
    private lateinit var messageValue: String
    private lateinit var passengerTypeValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserToDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //이전 버튼 클릭 시
        binding.btnPrev.setOnClickListener {
            finish()
        }

        //intent 값 가져오기
        passengerTypeValue = intent.getStringExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY).toString()

        binding.btnWait.setOnClickListener {
            messageValue = "버스 정차 시 조금 기다려주세요"
            saveRideBellData()
            navigateToNextActivity()

        }
        binding.btnChair.setOnClickListener {
            messageValue = "휠체어용 발판을 이용할게요"
            saveRideBellData()
            navigateToNextActivity()
        }
        binding.btnBabycar.setOnClickListener {
            messageValue = "유모차가 있어요"

            saveRideBellData()
            navigateToNextActivity()
        }
        binding.btnNothing.setOnClickListener {
            messageValue = "없음"
            saveRideBellData()
            navigateToNextActivity()
        }

    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, CompleteActivity::class.java)
        intent.putExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY, passengerTypeValue)
        intent.putExtra(BusRideBell.BUS_MESSAGE_KEY, messageValue)
        startActivity(intent)
    }

    private fun saveRideBellData() {
        val rideBellData = RideBellData(
            passengerType = passengerTypeValue!!,
            message = messageValue,
            busNumber = "",
            busStopNumber = ""
        )
        RideBellDataManager.saveRideBellData(rideBellData, this)
    }
}
