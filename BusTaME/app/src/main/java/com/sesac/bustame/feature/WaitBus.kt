package com.sesac.bustame.feature

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import com.sesac.bustame.BusRideBell
import com.sesac.bustame.data.model.BusArriveInfo
import com.sesac.bustame.data.model.Item
import com.sesac.bustame.data.network.RetrofitClient
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
    private lateinit var busArriveInfo: String
    private lateinit var busFull: String
    private lateinit var itemAdapter: ItemAdapter

    private var responseId: Long = 0

    private lateinit var passengerTypeValue: String
    private lateinit var messageValue: String
    private val itemList: ArrayList<Item> = ArrayList()

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 30000 // 30초

    private var isCountDownStarted = false
    private val countdownDuration = 30000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaitBusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인텐트값 받기
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
                    response: Response<ResponseBody>,
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
                                this@WaitBus.busFull = item.rerdieDiv1
                                binding.busArriveTime.text = this@WaitBus.busArriveInfo

                                // 값이 2일 경우 여유로 뜨게
                                if (this@WaitBus.busFull == "2") {
                                    binding.busFull.text = "여유"
                                }

                                // 버스가 도착했을 때 버튼 숨기기
                                if (this@WaitBus.busArriveInfo == "곧 도착") {
                                    binding.btnCancel.visibility = View.GONE
                                    startCountdonw()
                                } else {
                                    binding.btnCancel.visibility = View.VISIBLE
                                }

                                break
                            }
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

    override fun onResume() {
        super.onResume()
        // 액티비티가 화면에 표시될 때 타이머 시작
        startUpdateTimer()
    }

    override fun onPause() {
        super.onPause()
        // 액티비티가 화면에서 사라질 때 타이머 정지
        stopUpdateTimer()
    }

    private fun startUpdateTimer() {
        // 타이머 시작
        handler.postDelayed(updateRunnable, updateInterval)
    }

    private fun stopUpdateTimer() {
        // 타이머 정지
        handler.removeCallbacks(updateRunnable)
    }

    private val updateRunnable = object : Runnable {
        override fun run() {
            // 업데이트 메서드 호출
            updateArriveInfo()

            // 다음 업데이트를 위해 타이머 재시작
            handler.postDelayed(this, updateInterval)
        }
    }

    private fun startCountdonw() {
        isCountDownStarted = false

        val countdownHandler = Handler(Looper.getMainLooper())
        countdownHandler.postDelayed({
            navigateToArriveActivity()
        }, countdownDuration.toLong())
    }

    private fun navigateToArriveActivity() {
        val intent = Intent(this, ArriveActivity::class.java)
        startActivity(intent)
    }
}
