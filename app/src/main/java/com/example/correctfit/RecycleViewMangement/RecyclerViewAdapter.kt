package com.example.correctfit.RecycleViewMangement

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.R
import com.example.correctfit.UI.DoYouKnowCurrentSize
import com.example.correctfit.UI.MeasureYourSelf
import com.example.correctfit.UI.PhaseOfWomanHood
import com.example.correctfit.databinding.DoyouknowcurrentsizeBinding
import com.example.correctfit.databinding.MeasureyourselfBinding
import com.example.correctfit.databinding.PhaseofwomenhoodBinding
import com.example.correctfit.response.RecyclerViewItem

class RecyclerViewAdapter :RecyclerView.Adapter<RecyclerViewHolder>(){
    lateinit var listener: PhaseOfWomanHood
    lateinit var listen:DoYouKnowCurrentSize
    lateinit var lis: MeasureYourSelf
     var items = listOf<RecyclerViewItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value){
            field =value
            notifyDataSetChanged()
        }

    var label = ""

    var itemClickListener :((view: View,item:RecyclerViewItem,position:Int)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return when(viewType){
            R.layout.phaseofwomenhood -> RecyclerViewHolder.WomanHoodViewHolder(
                PhaseofwomenhoodBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.doyouknowcurrentsize -> RecyclerViewHolder.CurrentSizeViewHolder(
                DoyouknowcurrentsizeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.measureyourself -> RecyclerViewHolder.MeasureYourSelfViewHolder(
                MeasureyourselfBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided")
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemClickListener= itemClickListener
        when(holder){
            is RecyclerViewHolder.WomanHoodViewHolder ->holder.binding(items[position] as RecyclerViewItem.Type)
            is RecyclerViewHolder.CurrentSizeViewHolder ->holder.binding(items[position] as RecyclerViewItem.Type)
            is RecyclerViewHolder.MeasureYourSelfViewHolder -> holder.binding(items[position] as RecyclerViewItem.Type)
        }
    }

    override fun getItemCount() =items.size

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is RecyclerViewItem.Type -> if(label == "measure" ) R.layout.measureyourself else if(label == "currentSize") R.layout.doyouknowcurrentsize else R.layout.phaseofwomenhood

            else -> {throw IllegalArgumentException("Invalid data Type")}
        }
    }


}