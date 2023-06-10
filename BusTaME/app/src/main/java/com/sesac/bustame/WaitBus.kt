package com.sesac.bustame

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sesac.bustame.databinding.ActivityWaitBusBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitBus : AppCompatActivity() {

    private lateinit var binding: ActivityWaitBusBinding
    private lateinit var busNum: String
    private lateinit var busStopName: String
    private var responseId: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인텐트값 받기
        busNum = intent.getStringExtra(BusRideBell.BUS_NUM_VALUE_KEY).toString()
        busStopName = intent.getStringExtra("busStopName").toString()
        responseId = intent.getLongExtra("responseId", 0)

        binding.busStopName.text = busStopName
        binding.busNum.text = busNum

        binding.btnCancel.setOnClickListener {
            Log.d("deleteData", "Delete button clicked. responseId: $responseId")
            // Retrofit을 사용하여 DELETE 요청 보내기
            val service = RetrofitClient.service
            service.deleteUserRideBellData(responseId).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        finish()
                        Log.d("deleteData", "success")
                    } else {
                        Log.d("deleteData", "fail")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("deleteData", "아예 통신 안 됨")
                }
            })
        }

        val circularProgressBar = binding.circularProgressBar
        val rotationAnimation = ObjectAnimator.ofFloat(circularProgressBar, "rotation", 0f, 360f)
        rotationAnimation.duration = 1000
        rotationAnimation.repeatCount = ObjectAnimator.INFINITE
        rotationAnimation.start()
    }
}