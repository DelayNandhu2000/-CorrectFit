package com.example.correctfit

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.correctfit.databinding.DoyouknowcurrentsizeBinding
import com.example.correctfit.databinding.MeasureyourselfBinding
import com.example.correctfit.databinding.PhaseofwomenhoodBinding
import com.example.correctfit.response.RecyclerViewItem

sealed class RecyclerViewHolder(binding : ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    var itemClickListener :((view: View, item:RecyclerViewItem, position:Int)->Unit)? = null


    class WomanHoodViewHolder(private val inflate: PhaseofwomenhoodBinding) : RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){

            inflate.BoxSubText.isVisible = type.default
            inflate.MainBoxSmallCircle.isVisible = type.default
            inflate.womenhoodmainbox.setBackgroundResource(if(type.default)R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
            inflate.MainBoxTitle1.setTextColor(if (type.default) Color.parseColor("#FF566A") else Color.parseColor("#222222"))
            inflate.BoxSubText.text = type.description
            inflate.MainBoxTitle1.text = type.type
            inflate.root.setOnClickListener {
               itemClickListener?.invoke(it,type,adapterPosition)
            }
        }
    }

    class CurrentSizeViewHolder(private val inflate: DoyouknowcurrentsizeBinding) :RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){
            inflate.MainBoxTitle1.text =type.type
            inflate.currentSizeBox.setBackgroundResource(if(type.default)R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
            inflate.MainBoxSmallCircle.isVisible=type.default
            inflate.root.setOnClickListener {
                itemClickListener?.invoke(it,type,adapterPosition)
            }
        }
    }
    class MeasureYourSelfViewHolder(private val inflate: MeasureyourselfBinding):RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){
            inflate.MeasureType.text=type.type
        }
    }

}