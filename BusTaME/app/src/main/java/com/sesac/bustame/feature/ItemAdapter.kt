package com.sesac.bustame.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sesac.bustame.R
import com.sesac.bustame.data.model.Item

class ItemAdapter(
    private val itemList: List<Item>,
    private val passengerTypeValue: String,
    private val messageValue: String,
    private val button: TextView
) : RecyclerView.Adapter<ItemAdapter.BusInfoViewHolder>() {

    private var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_businfo, parent, false)
        return BusInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BusInfoViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedPosition
            if (selectedPosition != position && item.arrmsg1 != "운행종료") {
                selectedPosition = position
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }

        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.color.green_E8F8EF)
            button.setBackgroundResource(R.color.green_58D78F)
        } else {
            holder.itemView.setBackgroundResource(android.R.color.white)
            button.setBackgroundResource(R.color.green_D6F1E2)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
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
        private val busType: TextView = itemView.findViewById(R.id.busType)
        private val icBus: ImageView = itemView.findViewById(R.id.ic_bus)

        fun bind(item: Item) {
            busNum.text = item.busRouteAbrv
            nextStn.text = item.nxtStn
            busDirection.text = item.adirection
            firstBus.text = item.arrmsg1

            if (item.busType1 == "0") {
                busType.text = "일반버스"
                busType.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            } else {
                busType.text = "저상버스"
                busType.setTextColor(ContextCompat.getColor(itemView.context, R.color.red_FC482F))
            }

            when (item.routeType) {
                "2", "4" -> {
                    icBus.setImageResource(R.drawable.ic_bus_green)
                }

                "3" -> {
                    icBus.setImageResource(R.drawable.ic_bus_blue)
                }

                "6", "8" -> {
                    icBus.setImageResource(R.drawable.ic_bus_red)
                }

                else -> {
                    icBus.setImageResource(R.drawable.ic_bus_black)
                }
            }
        }
    }
}
