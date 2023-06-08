package com.sesac.bustame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.sesac.bustame.databinding.ActivityArriveBinding

class ArriveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArriveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrive)

        Handler().postDelayed({
            startStartActivity()
        }, ArriveActivity.DURATION)
    }

    private fun startStartActivity() {
        val intent = Intent(this, WaitBus::class.java)
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
