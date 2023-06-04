package com.sesac.bustame

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.sesac.bustame.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapView
import android.Manifest
import android.content.ClipData
import android.content.Intent
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView.CurrentLocationEventListener
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.function.LongToIntFunction

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    private var isInitialLocationTracked = false
    private lateinit var locationJson: JsonObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationJson = JsonObject()

        //바텀시트
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_EXPANDED
        }

        //권한 ID 선언
        val InternetPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.INTERNET
        )

        val FineLocaPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        val CoarseLocaPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // 권한이 열려있는지 확인
        if (InternetPermission == PackageManager.PERMISSION_DENIED || FineLocaPermission == PackageManager.PERMISSION_DENIED || CoarseLocaPermission == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                    arrayOf(
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            }
        }

        //지도 출력
        mapView = MapView(this)
        binding.mapView.addView(mapView)
        //현재 위치로 지도 이동
        mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        mapView.setShowCurrentLocationMarker(true)

        //주변 정류장 확인하기 눌렀을 때
        binding.imgIcCheckBJ.setOnClickListener {
            // 현재 위치 기준으로 정류장 확인을 위해 위치 추적 중지
            if (!isInitialLocationTracked) {
                isInitialLocationTracked = true
                mapView.currentLocationTrackingMode =
                    MapView.CurrentLocationTrackingMode.TrackingModeOff
            }

            //현재 위치 좌표 서버로 보내기
            val call = RetrofitClient.service.sendUserPosData(locationJson)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()?.string()
                        val itemType = object : TypeToken<List<aroundBusStop>>() {}.type
                        val itemList: List<aroundBusStop> = Gson().fromJson(responseData, itemType)

                        // itemList를 처리하고 지도 위에 마커를 표시합니다
                        for (item in itemList) {
                            val latitude = item.gpsY.toDouble()
                            val longitude = item.gpsX.toDouble()

                            // 지도 위에 마커 표시
                            val marker = MapPOIItem()
                            marker.itemName = item.stationNm
                            marker.mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
                            // 원하는 대로 마커의 모양을 커스터마이징할 수 있습니다
                            // ...

                            // 마커를 지도에 추가합니다
                            mapView.addPOIItem(marker)

                        }
                    } else {
                        // 서버로부터 실패 응답을 받은 경우 처리
                        Log.d("Fail Response", "FailFailFailFailFailFailFailFail")
                        Log.d("responsecode", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // 통신 실패 처리
                    Log.d("Failure", "FailFailFailFailFailFailFailFail")
                }
            })
        }


        //현재 위치 이벤트 리스너
        mapView.setCurrentLocationEventListener(object : CurrentLocationEventListener {

            override fun onCurrentLocationUpdate(
                mapView: MapView,
                mapPoint: MapPoint,
                accuracyInMeters: Float
            ) {
                val latitude = mapPoint.mapPointGeoCoord.latitude //위도
                val longitude = mapPoint.mapPointGeoCoord.longitude //경도
                val radius = 300

                val locationJson = JsonObject()

                locationJson.apply {
                    addProperty("tmX", latitude)
                    addProperty("tmY", longitude)
                    addProperty("radius", radius)
                }

                this@MainActivity.locationJson = locationJson
            }

            override fun onCurrentLocationDeviceHeadingUpdate(mapView: MapView, v: Float) {
                // 현재 위치 디바이스 방향 업데이트 이벤트 처리
            }

            override fun onCurrentLocationUpdateFailed(mapView: MapView) {
                // 현재 위치 업데이트 실패 처리
            }

            override fun onCurrentLocationUpdateCancelled(mapView: MapView) {
                // 현재 위치 업데이트 취소 처리
            }
        })
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            var checkResult = true

            // 모든 퍼미션을 허용했는지 체크
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }

            // 권한 체크에 동의를 하지 않으면 안드로이드 종료
            if (!checkResult) {
                finish()
            }
        }
    }

}