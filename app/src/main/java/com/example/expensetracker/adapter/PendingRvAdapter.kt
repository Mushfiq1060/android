package com.example.expensetracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.expensetracker.R
import com.example.expensetracker.model.Data

class PendingRvAdapter(
    var listener: OnItemClickListener
): RecyclerView.Adapter<PendingRvAdapter.MyViewHolder>() {

    val allData = ArrayList<Data>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pending_rv_list, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            titleText.setText(allData.get(position).title)
            timeStamp.setText(allData.get(position).time)
            amountText.setText(allData.get(position).amount)
        }
    }
    override fun getItemCount(): Int {
        return allData.size
    }
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var titleText = itemView.findViewById<TextView>(R.id.pendingExpenseText)
        var timeStamp = itemView.findViewById<TextView>(R.id.pendingTimeStamp)
        var amountText = itemView.findViewById<TextView>(R.id.pendingCostText)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(allData.get(position))
            }
        }
    }
    fun updateList(newList: List<Data>) {
        allData.clear()
        allData.addAll(newList)
        notifyDataSetChanged()
    }
    interface OnItemClickListener {
        fun onItemClick(obj: Data)
    }
}

