package com.sesac.bustame.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sesac.bustame.databinding.ActivityBusArriveBinding

class BusArriveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBusArriveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBusArriveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            startStartActivity()
        }, DURATION)
    }

    private fun startStartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val DURATION: Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}