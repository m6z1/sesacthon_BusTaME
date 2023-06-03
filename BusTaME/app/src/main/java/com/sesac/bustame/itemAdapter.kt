package com.sesac.bustame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class itemAdapter(val itemList: ArrayList<BusInfoItem>) :
    RecyclerView.Adapter<itemAdapter.busInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): busInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_businfo, parent, false)
        return busInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: busInfoViewHolder, position: Int) {
        holder.busNum.text = itemList[position].busNum
        holder.tv_title.text = itemList[position].busDirect
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class busInfoViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val busNum = itemView.findViewById<TextView>(R.id.busNum)
        val tv_title = itemView.findViewById<TextView>(R.id.busDirection)
    }
}