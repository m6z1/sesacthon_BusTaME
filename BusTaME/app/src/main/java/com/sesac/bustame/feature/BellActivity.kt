package com.sesac.bustame.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.sesac.bustame.BusRideBell
import com.sesac.bustame.R
import com.sesac.bustame.data.model.BusArriveInfo
import com.sesac.bustame.data.model.Item
import com.sesac.bustame.data.model.RideBellData
import com.sesac.bustame.data.network.RetrofitClient
import com.sesac.bustame.databinding.ActivityBellBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BellActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBellBinding
    private lateinit var busStopNum: String
    private lateinit var busStopName: String
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var passengerTypeValue: String
    private lateinit var messageValue: String
    private lateinit var busNumValue: String
    private var responseId: Long = 0

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval: Long = 30000 // 30초

    private val itemList: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomToolbar(R.layout.custom_actionbar)

        // 마커 정보 받아오기
        busStopNum = intent.getStringExtra("busStopNum").toString()
        busStopName = intent.getStringExtra("busStopName").toString()
        Log.d("markervalue", "$busStopNum $busStopName")

        // 이전 액티비티 값 받아오기
        // SharedPreferences에서 값 가져오기
        val sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        passengerTypeValue = sharedPreferences.getString("passengerType", "").toString()
        messageValue = sharedPreferences.getString("message", "").toString()

        Log.d("SharedPreferences", "passengerType: $passengerTypeValue, message: $messageValue")

        // 상단의 버스정류장 정보 박스
        binding.busStopName.text = busStopName

        binding.button.setOnClickListener {
            showPopupDialog()
        }

        binding.icBack.setOnClickListener {
            finish()
        }

        // 통신해서 버스 정보 받아오기
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
                        itemList.addAll(newItemList)
                        itemAdapter.notifyDataSetChanged()

                        Log.d("serverresponse", "success")
                        Log.d("serverresponse", itemList.toString())
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

        // 리사이클러뷰 설정
        itemAdapter = ItemAdapter(itemList, passengerTypeValue, messageValue, binding.button)
        binding.busRecyclerView.adapter = itemAdapter
        binding.busRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    // 툴바 띄우기
    private fun setCustomToolbar(layout: Int) {
        val toolbar = findViewById<Toolbar>(layout)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }

    fun goToMainActivity(view: View) {
        finish()
    }

    private fun sendUserRideBellData() {
        val rideBellService = RetrofitClient.service
        val rideBellData = RideBellData(
            passengerTypeValue,
            messageValue,
            busNumValue,
            busStopNum,
        )
        val call = rideBellService.sendUserRideBellData(
            rideBellData,
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // 성공적으로 서버로 데이터 전송이 완료됨
                if (response.isSuccessful) {
                    val responseBody = response.body()?.string()
                    val newResponseId = responseBody?.trim()?.toLongOrNull()
                    if (newResponseId != null) {
                        responseId = newResponseId
                        navigateToNextActivity(responseId)
                    }
                    Log.d("serverresponse", "성공적으로 보내졌습니다")
                    Log.d("serverresponse", responseId.toString())
                } else {
                    Log.d("serverresponse", "안 갔음 데이터")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("serverresponse", "통신아예실패")
            }
        })
    }

    private fun navigateToNextActivity(responseId: Long) {
        val intent = Intent(this, WaitBus::class.java)
        intent.putExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY, passengerTypeValue)
        intent.putExtra(BusRideBell.BUS_NUM_VALUE_KEY, busNumValue)
        intent.putExtra("busStopName", busStopName)
        intent.putExtra("responseId", responseId)
        intent.putExtra("busStopNum", busStopNum)
        Log.d("intentvalue", "$responseId")
        startActivity(intent)
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

    fun updateArriveInfo() {
        // 통신해서 버스 정보 받아오기
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
                        itemList.addAll(newItemList)
                        itemAdapter.notifyDataSetChanged()

                        Log.d("serverresponse", "success")
                        Log.d("serverresponse", itemList.toString())
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

    private fun showPopupDialog() {
        // 커스텀 다이얼로그 레이아웃 inflate
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_bus_bell, null)

        // 커스텀 다이얼로그에 보여질 버스 번호 설정
        val selectedBusNum = itemAdapter.getSelectedBusNum()
        val tvBusNum = dialogView.findViewById<TextView>(R.id.dialog_bus_num)
        tvBusNum.text = selectedBusNum

        // AlertDialog Builder를 사용하여 커스텀 다이얼로그 생성
        val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogView)
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        // 확인 버튼: sendUserRideBellData() 함수를 호출하고 다이얼로그 닫기
        val okButton = dialogView.findViewById<TextView>(R.id.dialog_tv_ok)
        okButton.setOnClickListener {
            busNumValue = selectedBusNum
            sendUserRideBellData()
            alertDialog.dismiss()
        }

        // 취소 버튼: 다이얼로그 닫기
        val cancelButton = dialogView.findViewById<TextView>(R.id.dialog_tv_cancel)
        cancelButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
