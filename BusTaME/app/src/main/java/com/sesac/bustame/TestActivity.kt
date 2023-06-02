package com.sesac.bustame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.sesac.bustame.databinding.ActivityTestBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val textValue = binding.textValue.text.toString()
            val jsonData = JsonObject().apply {
                addProperty("name", textValue)
            }
            RetrofitClient.service.sendBusStopData(jsonData)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            val responseData = response.body()?.toString()
                            binding.resultValue.text = "요청성공"
                        } else {
                            binding.resultValue.text = "요청실패"
                            var errorBody = response.errorBody()!!.string()
                            Log.d("log", errorBody)
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("log", t.message.toString())
                    }
                })

        }

    }
}