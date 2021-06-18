package com.example.bottommenudialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

class Adapter(var itemList:ArrayList<Item>, var context: FragmentActivity?): BaseAdapter() {

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val recyclerItem = itemList[position]

        val inflator = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val itView = inflator.inflate(R.layout.item, null)


        itView.findViewById<TextView>(R.id.effect_name).text = recyclerItem.name

        return itView
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

}

