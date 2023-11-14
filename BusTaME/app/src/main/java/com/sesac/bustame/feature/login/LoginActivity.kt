package com.sesac.bustame.feature.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sesac.bustame.R
import com.sesac.bustame.databinding.ActivityLoginBinding
import com.sesac.bustame.feature.StartActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToStart()
    }

    private fun navigateToStart() = with(binding) {
        btnLoginGoogle.setOnClickListener {
            val intent = Intent(this@LoginActivity, StartActivity::class.java)
            startActivity(intent)
        }

        btnLoginKakao.setOnClickListener {
            val fragment = LoginFragment()
            fragment.arguments = Bundle().apply {
                putString(
                    "url",
                    "https://kauth.kakao.com/oauth/authorize?client_id=a2d378893f840248afdf3c484a6fb5fd&redirect_uri=http://localhost:8080/kakao/callback&response_type=code",
                )
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.ly_login, fragment)
                .commit()
        }
    }
}
