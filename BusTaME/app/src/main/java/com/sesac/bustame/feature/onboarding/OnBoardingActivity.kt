package com.sesac.bustame.feature.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.sesac.bustame.R
import com.sesac.bustame.feature.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var bindingAdapter: OnBoardingAdapter
    private lateinit var indicatorsContainer: LinearLayout
    private lateinit var onboardingViewPager: ViewPager2
    private lateinit var btnSplash: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        setOnBoardingItems()
        setupIndicators()
        setCurrentIndicator(0)
        pushSplashBtn()
        changeBtnText()
    }

    private fun setOnBoardingItems() {
        bindingAdapter = OnBoardingAdapter(
            listOf(
                OnBoardingItem(
                    onBoardImage = R.drawable.splash_first,
                    description = "스플래시 첫 화면입니다.",
                ),
                OnBoardingItem(
                    onBoardImage = R.drawable.splash_second,
                    description = "스플래시 두번째 화면입니다.",
                ),
                OnBoardingItem(
                    onBoardImage = R.drawable.splash_third,
                    description = "스플래시 마짐가 화면입니다.",
                ),
            ),
        )
        onboardingViewPager = findViewById(R.id.viewPager_onBoarding)
        onboardingViewPager.adapter = bindingAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.dots_indicator)
        val indicators = arrayOfNulls<ImageView>(bindingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background,
                    ),
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val img = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                img.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicatoer_active_background,
                    ),
                )
            } else {
                img.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background,
                    ),
                )
            }
        }
    }

    private fun pushSplashBtn() {
        btnSplash = findViewById(R.id.btnSplash)

        btnSplash.setOnClickListener {
            if (onboardingViewPager.currentItem < bindingAdapter.itemCount - 1) {
                onboardingViewPager.currentItem += 1
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun changeBtnText() {
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                if (position == bindingAdapter.itemCount - 1) {
                    btnSplash.text = "시작하기"
                } else {
                    btnSplash.text = "다음"
                }
            }
        })
    }
}
