package com.example.correctfit.RecycleViewMangement

import android.graphics.Color
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.correctfit.R
import com.example.correctfit.databinding.DoyouknowcurrentsizeBinding
import com.example.correctfit.databinding.ItemBraSizeBinding
import com.example.correctfit.databinding.ItemBrandBinding
import com.example.correctfit.databinding.MeasureyourselfBinding
import com.example.correctfit.databinding.PhaseofwomenhoodBinding
import com.example.correctfit.response.RecyclerViewItem



var selectedName:String ?=null
var lastSelectedName:String ?=null

var currentSelected = -1
var previousSelected = -1

sealed class RecyclerViewHolder(binding : ViewBinding) : RecyclerView.ViewHolder(binding.root) {


    var itemClickListener :((view: View, item:Any, position:Int)->Unit)? = null


    class WomanHoodViewHolder(private val inflate: PhaseofwomenhoodBinding) : RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){

            inflate.BoxSubText.isVisible = type.default
            inflate.MainBoxSmallCircle.isVisible = type.default
            inflate.womenhoodmainbox.setBackgroundResource(if(type.default) R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
            inflate.MainBoxTitle1.setTextColor(if (type.default) Color.parseColor("#FF566A") else Color.parseColor("#222222"))
            inflate.BoxSubText.text = type.description
            inflate.MainBoxTitle1.text = type.type
            inflate.root.setOnClickListener {
               itemClickListener?.invoke(it,type,adapterPosition)
            }
        }
    }

    class CurrentSizeViewHolder(private val inflate: DoyouknowcurrentsizeBinding) :
        RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){
            inflate.MainBoxTitle1.text =type.type
            inflate.currentSizeBox.setBackgroundResource(if(type.default) R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
            inflate.MainBoxTitle1.setTextColor(if (type.default) Color.parseColor("#FF566A") else Color.parseColor("#222222"))
            inflate.MainBoxSmallCircle.isVisible=type.default
            inflate.root.setOnClickListener {
                itemClickListener?.invoke(it,type,adapterPosition)
            }
        }
    }
    class MeasureYourSelfViewHolder(private val inflate: MeasureyourselfBinding):
        RecyclerViewHolder(inflate){
        fun binding(type: RecyclerViewItem.Type){
            inflate.MeasureType.text=type.type
        }
    }

    class BindBrandViewHolder(
        val inflate: ItemBrandBinding,
        private val recyclerViewAdapter: RecyclerViewAdapter
    ) : RecyclerViewHolder(inflate) {

        fun binding(item: String){
            inflate.brand.text = item
            inflate.root.setBackgroundResource(if(currentSelected==adapterPosition) R.drawable.womanhoodboxselectedstyle else R.drawable.womenhoodboxnotselectedstyle)
            inflate.brand.setTextColor(if(currentSelected==adapterPosition) Color.parseColor("#FF566A") else Color.parseColor("#222222"))

            inflate.root.setOnClickListener {
                currentSelected=adapterPosition
                if(previousSelected != currentSelected){
                    recyclerViewAdapter.notifyItemChanged(previousSelected)
                    previousSelected= currentSelected
                }

                recyclerViewAdapter.notifyItemChanged(currentSelected)
               itemClickListener?.invoke(it,item,adapterPosition)
              recyclerViewAdapter.notifyItemChanged(adapterPosition)
            }
        }

    }

    class BindBraViewHolder(
        val inflate :ItemBraSizeBinding,
        private val recyclerViewAdapter: RecyclerViewAdapter
    ): RecyclerViewHolder(inflate){

        fun binding(item: String){
            inflate.BraSize1.text =  item


            inflate.root.setOnClickListener {
                currentSelected=adapterPosition
                if(previousSelected != currentSelected){
                    recyclerViewAdapter.notifyItemChanged(adapterPosition)
                    previousSelected= currentSelected
                }
                recyclerViewAdapter.notifyItemChanged(currentSelected)

                itemClickListener?.invoke(it,item,adapterPosition)
                recyclerViewAdapter.notifyItemChanged(adapterPosition)
            }

        }
    }

}



















//            if(adapterPosition== selected){
//                inflate.BrandBox.setBackgroundResource(R.drawable.womanhoodboxselectedstyle)
//                inflate.brand.setTextColor(Color.parseColor("#FF566A"))
//            }else{
//                inflate.BrandBox.setBackgroundColor(R.drawable.womenhoodboxnotselectedstyle)
//                inflate.brand.setTextColor(Color.parseColor("#222222"))
//            }