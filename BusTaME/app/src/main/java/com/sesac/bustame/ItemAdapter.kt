package com.sesac.bustame

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sesac.bustame.BusRideBell

class ItemAdapter(
    private val itemList: List<HardCodingItem>,
    private val passengerTypeValue: String,
    private val messageValue: String
) :
    RecyclerView.Adapter<ItemAdapter.BusInfoViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_businfo, parent, false)
        return BusInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusInfoViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        holder.radioButton.setOnClickListener {
            selectedPosition = holder.position
            notifyDataSetChanged()
        }

        holder.radioButton.isChecked = selectedPosition == holder.adapterPosition

        when (item.busNum) {
            "5626", "5712", "6515" -> holder.icBus.setImageResource(R.drawable.ic_bus_green)
            "5200" -> holder.icBus.setImageResource(R.drawable.ic_bus_red)
            else -> holder.icBus.setImageResource(R.drawable.ic_bus_blue)
        }

    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    fun getSelectedBusNum(): String {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            itemList[selectedPosition].busNum
        } else {
            ""
        }
    }


    inner class BusInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val busNum: TextView = itemView.findViewById(R.id.busNum)
        private val nextStn: TextView = itemView.findViewById(R.id.busDirection)
        private val busDirection: TextView = itemView.findViewById(R.id.direction)
        private val firstBus: TextView = itemView.findViewById(R.id.first_bus)
        private val secondBus: TextView = itemView.findViewById(R.id.second_bus)
        val radioButton: RadioButton = itemView.findViewById(R.id.selectBtn)
        val icBus: ImageView = itemView.findViewById(R.id.ic_bus)

        fun bind(item: HardCodingItem) {
            busNum.text = item.busNum
            nextStn.text = item.nextStn
            busDirection.text = item.busDirection
            firstBus.text = item.firstBus
            secondBus.text = item.secondBus


            // 라디오 버튼이 선택되지 않았을 때 클릭 이벤트 처리
            itemView.setOnClickListener {
                if (adapterPosition == selectedPosition) {
                    // 이미 선택된 상태이므로 해제
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyDataSetChanged()
                }
            }

            radioButton.setOnClickListener {
                if (selectedPosition != adapterPosition) {
                    selectedPosition = adapterPosition
                    notifyDataSetChanged()

                    val selectedBusNum = item.busNum
                    val intent = Intent(itemView.context, BellActivity::class.java).apply {
                        putExtra("busNum", selectedBusNum)
                        putExtra("passengerTypeValue", passengerTypeValue)
                        putExtra("messageValue", messageValue)
                    }
                    itemView.context.startActivity(intent)
                }
            }

        }

    }
}


/*
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

 */
