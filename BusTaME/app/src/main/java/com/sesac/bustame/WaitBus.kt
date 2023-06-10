package com.sesac.bustame

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivityWaitBusBinding
import com.mikhaellopez.circularprogressbar.CircularProgressBar

class WaitBus : AppCompatActivity() {

    private lateinit var binding: ActivityWaitBusBinding
    private lateinit var busNum: String
    private lateinit var busStopName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인텐트값 받기
        busNum = intent.getStringExtra(BusRideBell.BUS_NUM_VALUE_KEY).toString()
        busStopName = intent.getStringExtra("busStopName").toString()

        binding.busStopName.text = busStopName
        binding.busNum.text = busNum

        binding.btnCancel.setOnClickListener {
            finish()
        }

        val circularProgressBar = binding.circularProgressBar
        val rotationAnimation = ObjectAnimator.ofFloat(circularProgressBar, "rotation", 0f, 360f)
        rotationAnimation.duration = 1000
        rotationAnimation.repeatCount = ObjectAnimator.INFINITE
        rotationAnimation.start()
    }
}