package com.sesac.bustame.feature

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.databinding.ActivitySplashBinding
import com.sesac.bustame.feature.login.LoginActivity
import com.sesac.bustame.feature.onboarding.OnBoardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed({
            checkFirstRun()
        }, DURATION)
    }

    private fun checkFirstRun() {
        val sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        val isFirstRun = sharedPreferences.getBoolean("isFirstRun", true) // 온보딩 여부
        val isUserRegistered = sharedPreferences.getBoolean("isUserRegistered", false) // 사용자 등록 여부

        if (isFirstRun) {
            // 온보딩 화면으로 이동
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()

            // 온보딩 화면 표시 완료 저장
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply()
        } else if (!isUserRegistered) {
            // 사용자 등록 화면으로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // 메인 화면으로 이동
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val DURATION: Long = 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
