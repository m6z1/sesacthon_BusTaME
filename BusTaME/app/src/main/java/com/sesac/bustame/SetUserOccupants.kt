package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivitySetUserOccupantsBinding

class SetUserOccupants : AppCompatActivity() {

    private lateinit var binding: ActivitySetUserOccupantsBinding
    private lateinit var seatTypeValue: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetUserOccupantsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNormal.setOnClickListener {
            seatTypeValue = "일반 좌석"
            saveRideBellData()
            navigateToNextActivity()

        }

        binding.btnOccupant.setOnClickListener {
            seatTypeValue = "교통약자 좌석"
            saveRideBellData()
            navigateToNextActivity()


        }
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, SetUserCategory::class.java)
        intent.putExtra(BusRideBell.BUS_SEAT_TYPE_KEY, seatTypeValue)
        startActivity(intent)
    }

    private fun saveRideBellData() {
        val rideBellData = RideBellData(
            seatType = seatTypeValue,
            passengerType = "",
            message = "",
            busNumber = "",
            busStopName = ""
        )
        RideBellDataManager.saveRideBellData(rideBellData, this)
    }
}