package com.sesac.bustame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.sesac.bustame.databinding.ActivityBellBinding

class BellActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBellBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBellBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCustomToolbar(R.layout.custom_actionbar)


        //리사이클러뷰 버스 정보들 띄우기
        val busView = binding.busRecyclerView
        val itemList = ArrayList<BusInfoItem>()

        itemList.add(BusInfoItem("160", "구일역, 제일제당 (중) 방면"))
        itemList.add(BusInfoItem("5626", "구일역, 제일제당 (중) 방면"))
        itemList.add(BusInfoItem("5200", "구일역, 제일제당 (중) 방면"))

        val itemAdapter = itemAdapter(itemList)
        itemAdapter.notifyDataSetChanged()

        busView.adapter = itemAdapter
        busView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    //툴바 띄우기
    fun setCustomToolbar(layout: Int) {
        val toolbar = findViewById<Toolbar>(layout)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
    }
}