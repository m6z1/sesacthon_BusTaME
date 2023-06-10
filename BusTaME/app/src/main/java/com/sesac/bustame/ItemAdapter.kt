package com.sesac.bustame

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(
    private val itemList: List<Item>,
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

    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    fun getSelectedBusNum(): String {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            itemList[selectedPosition].busRouteAbrv
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
        private val busType: TextView = itemView.findViewById(R.id.busType)
        val icBus: ImageView = itemView.findViewById(R.id.ic_bus)

        init {
            radioButton.setOnClickListener {
                if (adapterPosition == selectedPosition) {
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyDataSetChanged()

                    val selectedBusNum = itemList[adapterPosition].busRouteAbrv
                    val intent = Intent(itemView.context, BellActivity::class.java).apply {
                        putExtra("busNum", selectedBusNum)
                        putExtra("passengerTypeValue", passengerTypeValue)
                        putExtra("messageValue", messageValue)
                    }
                    itemView.context.startActivity(intent)
                }

            }

            itemView.setOnClickListener {
                if (adapterPosition == selectedPosition) {
                    selectedPosition = RecyclerView.NO_POSITION
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(item: Item) {
            busNum.text = item.busRouteAbrv
            nextStn.text = item.nxtStn
            busDirection.text = item.adirection
            firstBus.text = item.arrmsg1
            secondBus.text = item.arrmsg2
        }
    }

}
