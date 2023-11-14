package com.example.correctfit

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.UI.PhaseOfWomanHood
import com.example.correctfit.response.RecyclerViewItem
var selectedPosition = -1
class MyAdapter() :RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var listItems: List<RecyclerViewItem.Type> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field = value
            notifyDataSetChanged()
        }
    var listener: WomanHood ?= null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.phaseofwomenhood,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {

        val currentItem = listItems[position]


        holder.boxSubText.isVisible = currentItem.default
        holder.markCircle.isVisible = currentItem.default
        holder.mainLayout.setBackgroundResource(if(currentItem.default)R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
        holder.boxText.setTextColor(if (currentItem.default) Color.parseColor("#FF566A") else Color.parseColor("#222222"))

        holder.boxText.text =currentItem.type
        holder.boxSubText.text=currentItem.description

        holder.item.setOnClickListener{
            listener?.boxClicked(position)
        }


    }

    override fun getItemCount()= listItems.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val boxText : TextView = itemView.findViewById(R.id.MainBoxTitle1)
        val boxSubText: TextView = itemView.findViewById(R.id.BoxSubText)
        val markCircle: View = itemView.findViewById(R.id.MainBoxSmallCircle)
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.womenhoodmainbox)
        val item  = itemView
    }


}


interface WomanHood{
     fun boxClicked(position: Int)
 }
