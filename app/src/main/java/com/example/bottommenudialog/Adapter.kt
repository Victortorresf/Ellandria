package com.example.bottommenudialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val itemList: ArrayList<Item>, private val onClickListener: OnClickListener): RecyclerView.Adapter<Adapter.CustomViewHolder>() {

    interface OnClickListener{
        fun onItemClick(position: Int)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        //var ct: TextView = itemView.findViewById<TextView>(R.id.content)
        var tx: TextView = itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return CustomViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.tx.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}

