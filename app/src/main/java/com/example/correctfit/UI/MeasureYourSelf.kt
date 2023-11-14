package com.example.correctfit.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correctfit.R
import com.example.correctfit.RecyclerViewAdapter
import com.example.correctfit.databinding.FragmentMeasureYourSelfBinding
import com.example.correctfit.databinding.MeasureyourselfBinding
import com.example.correctfit.response.RecyclerViewItem
import java.lang.reflect.Type

class MeasureYourSelf : Fragment() {
     lateinit var binding: FragmentMeasureYourSelfBinding
     private lateinit var listArray: ArrayList<RecyclerViewItem.Data>
    private var recyclerViewAdaptor = RecyclerViewAdapter()

    private var current = 0
    private var currentType=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMeasureYourSelfBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdaptor = RecyclerViewAdapter()
        recyclerViewAdaptor.lis= this

        listArray = arrayListOf(
            RecyclerViewItem.Data(Title = "Measure your self", image = R.drawable.mesureself,
                Type = arrayListOf(
                RecyclerViewItem.Type(type = "Stand straight and breath normally"),
                    RecyclerViewItem.Type(type = "Bring the measuring tape around your torso ,right beneath your bust"),
                    RecyclerViewItem.Type(type ="Next, Wrap the tap at the highest point of your bust"),
                    RecyclerViewItem.Type(type ="The tap should not be held too tight or loose, it should be taut"),
                    RecyclerViewItem.Type(type = "Record the measurement in centimeter and enter them below")
            )
            ),
               RecyclerViewItem.Data(Title ="Measure your self", image = R.drawable.pantymeasure,
                   Type = arrayListOf(
                       RecyclerViewItem.Type(type ="Bring the measure tap around the fullest part of your hips"),
                       RecyclerViewItem.Type(type ="The tap must be parallel to the floor and the fit must be snug"),
                       RecyclerViewItem.Type(type ="Record the measurement in cm"),
                       RecyclerViewItem.Type(type ="Refer to shyaway's Panty Size Chart to find your correct panty size" )
                   )
               ))

        val listSize = listArray.size
        showFiled(listArray[current])
        binding.womanButtonNext.setOnClickListener {
            if(current!= listSize-1){
                current++
                showFiled(listArray[current])
                when (current){
                    1-> binding.BustEG.hint ="Hip (eg: 80cm)"
                }
                binding.BandEG.isVisible= false
                binding.BandEG.text.clear()
                binding.BustEG.text.clear()

            }
        }
    }

    private fun showFiled(data: RecyclerViewItem.Data) {
     binding.MeasureYourSelf.text= data.Title
     data.image?.let { binding.MeasureIMG.setImageResource(it) }
        binding.MeasureRecycle.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
            recyclerViewAdaptor.label = "measure"
            recyclerViewAdaptor.items=data.Type
            adapter=recyclerViewAdaptor

        }
    }


}