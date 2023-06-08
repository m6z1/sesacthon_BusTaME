package com.sesac.bustame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.sesac.bustame.databinding.ActivityBellBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BellActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBellBinding
    private lateinit var busStopNum: String
    private lateinit var busStopName: String
    private lateinit var itemAdapter: ItemAdapter
    private val itemList: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomToolbar(R.layout.custom_actionbar)

        // 버스 아이디 받아오기
        busStopNum = intent.getStringExtra("busStopNum").toString()
        busStopName = intent.getStringExtra("busStopName").toString()

        // 리사이클러뷰 설정
        itemAdapter = ItemAdapter(itemList)
        binding.busRecyclerView.adapter = itemAdapter
        binding.busRecyclerView.layoutManager = LinearLayoutManager(this)

        itemList.clear()

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
                        itemList.addAll(newItemList)
                        itemAdapter.notifyDataSetChanged()
                    }
                    Log.d("serverresponse","success")
                    Log.d("serverresponse", busStopNum)
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

    // 툴바 띄우기
    private fun setCustomToolbar(layout: Int) {
        val toolbar = findViewById<Toolbar>(layout)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }

    fun goToMainActivity(view : View) {
        finish()
    }
}
