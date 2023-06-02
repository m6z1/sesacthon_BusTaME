package com.sesac.bustame

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.sesac.bustame.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapView
import android.Manifest
import com.google.android.material.bottomsheet.BottomSheetBehavior
import net.daum.mf.map.api.MapPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //바텀시트
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            this.state = BottomSheetBehavior.STATE_HALF_EXPANDED
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