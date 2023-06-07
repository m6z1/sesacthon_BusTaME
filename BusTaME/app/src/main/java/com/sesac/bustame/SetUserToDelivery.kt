package com.sesac.bustame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.databinding.ActivitySetUserToDeliveryBinding

class SetUserToDelivery : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserToDeliveryBinding
    private lateinit var messageValue: String
    private lateinit var seatTypeValue: String
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
        seatTypeValue = intent.getStringExtra(BusRideBell.BUS_SEAT_TYPE_KEY).toString()
        passengerTypeValue =
            intent.getStringExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY).toString()

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
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(BusRideBell.BUS_SEAT_TYPE_KEY, seatTypeValue)
        intent.putExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY, passengerTypeValue)
        intent.putExtra(BusRideBell.BUS_MESSAGE_KEY, messageValue)
        startActivity(intent)
    }

    private fun saveRideBellData() {
        val rideBellData = RideBellData(
            seatType = seatTypeValue!!,
            passengerType = passengerTypeValue!!,
            message = messageValue,
            busNumber = "",
            busStopName = ""
        )
        RideBellDataManager.saveRideBellData(rideBellData, this)
    }
}
