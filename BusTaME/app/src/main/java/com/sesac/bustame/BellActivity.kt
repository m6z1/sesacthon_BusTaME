package com.sesac.bustame

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomToolbar(R.layout.custom_actionbar)



        // 마커 정보 받아오기
        busStopNum = intent.getStringExtra("busStopNum").toString()
        busStopName = intent.getStringExtra("busStopName").toString()

        // 이전 액티비티 값 받아오기
        passengerTypeValue = intent.getStringExtra(BusRideBell.BUS_PASSENGER_TYPE_VALUE_KEY).toString()
        messageValue = intent.getStringExtra(BusRideBell.BUS_MESSAGE_KEY).toString()
        Log.d("intentvalue", "$passengerTypeValue $messageValue")


        //하드코딩 on ..
        val itemList = listOf(
            HardCodingItem("160", "개봉역.영화아파트", "온수동종점", "4분0초후[3번째 전]", "9분11초후[5번째 전]"),
            HardCodingItem("600", "고척중학교", "온수동", "2분43초후[2번째 전]", "11분40초후[6번째 전]"),
            HardCodingItem("660", "개봉역.영화아파트", "온수동", "곧도착", "5분11초후[3번째 전]"),
            HardCodingItem("662", "개봉역.영화아파트", "외발산동기점", "4분37초후[3번째 전]", "9분16초후[번째 전]"),
            HardCodingItem("5626", "개봉역.영화아파트", "온수동종점", "곧도착", "6분11초후[4번째 전]"),
            HardCodingItem("5712", "고척중학교", "홍대입구역", "3분12초후[2번째 전]", "8분43초후[4번째 전]"),
            HardCodingItem("6515", "고척중학교", "양천공영차고지", "곧도착", "4분02초후[3번째 전]"),
            HardCodingItem("5200", "개봉역.영화아파트", "시흥우체국.센트럴병원", "곧도착", "정보없음")
        )


        // 리사이클러뷰 설정
        itemAdapter = ItemAdapter(itemList, passengerTypeValue, messageValue)
        binding.busRecyclerView.adapter = itemAdapter
        binding.busRecyclerView.layoutManager = LinearLayoutManager(this)


        //상단의 버스정류장 정보 박스
        binding.busStopNum.text = busStopNum
        binding.busStopName.text = busStopName

        binding.button.setOnClickListener {
            showPopupDialog()
        }

    }

    private fun showPopupDialog() {
        Log.d("popupvalue","$passengerTypeValue $messageValue")
        val selectedBusNum = itemAdapter.getSelectedBusNum()

        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("${selectedBusNum}번 버스 승차벨을 울릴까요?")
        alertDialogBuilder.setMessage("승객 유형: $passengerTypeValue\n요청: $messageValue\n")
        alertDialogBuilder.setPositiveButton("예") { dialog: DialogInterface, _: Int ->
            busNumValue = selectedBusNum
            sendUserRideBellData()
            dialog.dismiss() // 다이얼로그 닫기
        }
        alertDialogBuilder.setNegativeButton("취소") { dialog: DialogInterface, _: Int ->
            dialog.dismiss() // 다이얼로그 닫기
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
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
        val call = rideBellService.sendUserRideBellData(RideBellData(passengerTypeValue, messageValue, busNumValue, busStopName))
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                // 성공적으로 서버로 데이터 전송이 완료됨
                if (response.isSuccessful) {
                    Log.d("serverresponse","성공적으로 보내졌습니다")
                } else {
                    Log.d("serverresponse","안 갔음 데이터")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("serverresponse","통신아예실패")
            }
        })
    }
}


/* 원래 코드 주석처리

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
import java.util.Timer
import java.util.TimerTask

class BellActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBellBinding
    private lateinit var busStopNum: String
    private lateinit var busStopName: String
    private lateinit var itemAdapter: ItemAdapter
    private val timer = Timer()
    private val itemList: ArrayList<Item> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomToolbar(R.layout.custom_actionbar)

        // 마커 정보 받아오기
        busStopNum = intent.getStringExtra("busStopNum").toString()
        busStopName = intent.getStringExtra("busStopName").toString()
        Log.d("busNum", busStopNum)

        // 리사이클러뷰 설정
        itemAdapter = ItemAdapter(itemList)
        binding.busRecyclerView.adapter = itemAdapter
        binding.busRecyclerView.layoutManager = LinearLayoutManager(this)

        itemList.clear()

        //상단의 버스정류장 정보 박스
        binding.busStopNum.text = busStopNum
        binding.busStopName.text = busStopName

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


val updateInterval = 100
timer.scheduleAtFixedRate(object : TimerTask() {
    override fun run() {
        updateBusInfo()
    }
}, 0, updateInterval.toLong())

}

override fun onDestroy() {
super.onDestroy()
timer.cancel()
}



private fun updateBusInfo() {
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
            }
            Log.d("serverresponse", "success")
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

}

 */
