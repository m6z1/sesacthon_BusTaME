package com.sesac.bustame.feature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sesac.bustame.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {

            navigateToNextActivity()
            /*
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("title").setMessage("시작메뉴")
                .setPositiveButton("Start", DialogInterface.OnClickListener { dialog, id ->
                    navigateToNextActivity()
                })
            alertDialog.create()
            alertDialog.show()
        }
             */

        }

    }

    fun navigateToNextActivity() {
        val intent = Intent(this, SetUserCategory::class.java)
        startActivity(intent)
    }
}