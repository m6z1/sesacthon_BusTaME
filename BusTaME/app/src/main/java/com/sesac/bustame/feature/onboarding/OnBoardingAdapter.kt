package com.sesac.bustame.feature.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sesac.bustame.R

class OnBoardingAdapter(private val onBoardingItems: List<OnBoardingItem>) :
    RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    inner class OnBoardingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgOnBoarding = view.findViewById<ImageView>(R.id.iv_on_boarding)

        fun bind(onBoardingItem: OnBoardingItem) {
            imgOnBoarding.setImageResource(onBoardingItem.onBoardImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_on_boarding_container,
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardingItems[position])
    }
}
