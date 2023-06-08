package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivitySetUserCategoryBinding

class SetUserCategory : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserCategoryBinding
    private lateinit var passengerTypeValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이전 버튼을 눌렀을 때
        binding.btnPrev.setOnClickListener {
            finish()
        }

        binding.btnDisabled.setOnClickListener {
            passengerTypeValue = "장애인"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnOld.setOnClickListener {
            passengerTypeValue = "고령자"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnPregnant.setOnClickListener {
            passengerTypeValue = "임산부"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnWithBaby.setOnClickListener {
            passengerTypeValue = "영유아를 동반한 사람"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnWounded.setOnClickListener {
            passengerTypeValue = "부상자"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnElse.setOnClickListener {
            passengerTypeValue = "기타"
            saveRideBellData()
            navigateToNextActivity()
        }
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, SetUserToDelivery::class.java)
        intent.putExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY, passengerTypeValue)
        startActivity(intent)
    }

    private fun saveRideBellData() {
        val rideBellData = RideBellData(
            passengerType = passengerTypeValue,
            message = "",
            busNumber = "",
            busStopName = ""
        )
        RideBellDataManager.saveRideBellData(rideBellData, this)
    }
}