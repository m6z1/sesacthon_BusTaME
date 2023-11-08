package com.sesac.bustame.feature

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sesac.bustame.data.model.Item
import com.sesac.bustame.R

class ItemAdapter(
    private val itemList: List<Item>,
    private val passengerTypeValue: String,
    private val messageValue: String,
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

        val isBusEnd = item.arrmsg1 == "운행종료"
        holder.radioButton.isEnabled = !isBusEnd

        holder.radioButton.setOnClickListener {
            if (!isBusEnd) {
                selectedPosition = holder.adapterPosition
                notifyDataSetChanged()
            }
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
        val radioButton: RadioButton = itemView.findViewById(R.id.selectBtn)
        private val busType: TextView = itemView.findViewById(R.id.busType)
        val icBus: ImageView = itemView.findViewById(R.id.ic_bus)

        init {
            radioButton.setOnClickListener {
                if (adapterPosition == selectedPosition) {
                    val item = itemList[adapterPosition]
                    val isLast = item.isLast1

                    if (item.isLast1 == "1") {
                        radioButton.isEnabled = false
                        notifyDataSetChanged()
                    } else {
                        val selectedBusNum = itemList[adapterPosition].busRouteAbrv
                        val intent = Intent(itemView.context, BellActivity::class.java).apply {
                            putExtra("busNum", selectedBusNum)
                            putExtra("passengerTypeValue", passengerTypeValue)
                            putExtra("messageValue", messageValue)
                        }
                        itemView.context.startActivity(intent)
                    }
                    selectedPosition = RecyclerView.NO_POSITION
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
