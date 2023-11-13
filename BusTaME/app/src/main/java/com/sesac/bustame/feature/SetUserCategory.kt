package com.sesac.bustame.feature

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.BusRideBell
import com.sesac.bustame.data.model.RideBellData
import com.sesac.bustame.data.network.RideBellDataManager
import com.sesac.bustame.databinding.ActivitySetUserCategoryBinding

class SetUserCategory : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserCategoryBinding
    private lateinit var passengerTypeValue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCategoryNormal.setOnClickListener {
            passengerTypeValue = "일반"
            saveRideBellData()
            navigateToNextActivity()
        }

        binding.btnCategoryDisadvantage.setOnClickListener {
            passengerTypeValue = "교통약자"
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
            busStopNumber = "",
        )
        RideBellDataManager.saveRideBellData(rideBellData, this)

        val sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("passengerType", rideBellData.passengerType)
            apply()
        }
    }
}
