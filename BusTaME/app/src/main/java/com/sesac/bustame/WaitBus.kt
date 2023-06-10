package com.sesac.bustame

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.JsonObject
import com.sesac.bustame.databinding.ActivityWaitBusBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WaitBus : AppCompatActivity() {

    private lateinit var binding: ActivityWaitBusBinding
    private lateinit var busNum: String
    private lateinit var busStopName: String
    private lateinit var busStopNum: String
    private var responseId: Long = 0
    private lateinit var busArriveInfo: String
    private lateinit var itemAdapter: ItemAdapter

    private lateinit var passengerTypeValue: String
    private lateinit var messageValue: String
    private val itemList: ArrayList<Item> = ArrayList()
    


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인텐트값 받기
        busNum = intent.getStringExtra(BusRideBell.BUS_NUM_VALUE_KEY).toString()
        busStopName = intent.getStringExtra("busStopName").toString()
        responseId = intent.getLongExtra("responseId", 0)
        busStopNum = intent.getStringExtra("busStopNum").toString()
        passengerTypeValue =
            intent.getStringExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY).toString()
        messageValue = intent.getStringExtra(BusRideBell.BUS_MESSAGE_KEY).toString()

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
        // 프로그레스 바 애니메이션 설정
        val animator = ValueAnimator.ofFloat(0f, 360f)
        animator.duration = 2000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            circularProgressBar.progress = animatedValue
        }
        animator.start()

        updateArriveInfo()
    }

    fun updateArriveInfo() {
        val busInfoData = JsonObject()
        busInfoData.addProperty("busStopId", busStopNum)

        val call = RetrofitClient.service.sendBusStopIdData(busInfoData)
        call.enqueue(object : Callback<BusArriveInfo> {
            override fun onResponse(call: Call<BusArriveInfo>, response: Response<BusArriveInfo>) {
                if (response.isSuccessful) {
                    val busArriveInfo = response.body()
                    val newItemList = busArriveInfo?.itemList

                    // itemList를 활용하여 받아온 버스 정보 처리
                    if (newItemList != null) {
                        itemList.clear()

                        for (item in newItemList) {
                            if (busNum == item.busRouteAbrv) {
                                this@WaitBus.busArriveInfo = item.arrmsg1

                                binding.busArriveTime.text = this@WaitBus.busArriveInfo
                                break
                            }
                        }

                        if (!::itemAdapter.isInitialized) {
                            itemAdapter = ItemAdapter(itemList, messageValue, passengerTypeValue) // itemAdapter 초기화
                        } else {
                            itemAdapter.notifyDataSetChanged()
                        }

                        Log.d("serverresponse", "success")
                    } else {
                        Log.d("serverresponse", "리스트가 비어있어요")
                    }
                } else {
                    // 서버로부터 실패 응답을 받은 경우 처리
                    Log.d("serverresponse", "FailFailResponse")
                    Log.d("serverresponsecode", response.code().toString())
                }
            }

            override fun onFailure(call: Call<BusArriveInfo>, t: Throwable) {
                // 통신 실패 처리
                Log.d("serverresponse", "fail $t")
            }
        })
    }

}