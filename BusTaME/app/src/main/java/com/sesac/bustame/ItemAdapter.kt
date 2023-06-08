package com.sesac.bustame

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList: List<Item>) :
    RecyclerView.Adapter<ItemAdapter.BusInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_businfo, parent, false)
        return BusInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusInfoViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class BusInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val busNum: TextView = itemView.findViewById(R.id.busNum)
        private val nextStn: TextView = itemView.findViewById(R.id.busDirection)
        private val busDirection: TextView = itemView.findViewById(R.id.direction)
        private val firstBus: TextView = itemView.findViewById(R.id.first_bus)
        private val secondBus: TextView = itemView.findViewById(R.id.second_bus)
        private val radioButton : RadioButton = itemView.findViewById(R.id.selectBtn)

        fun bind(item: Item) {
            busNum.text = item.busRouteAbrv
            nextStn.text = item.nxtStn
            busDirection.text = item.adirection
            firstBus.text = item.arrmsg1
            secondBus.text = item.arrmsg2

        }

    }
}
