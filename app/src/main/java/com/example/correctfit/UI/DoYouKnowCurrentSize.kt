package com.example.correctfit.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correctfit.R
import com.example.correctfit.RecycleViewMangement.RecyclerViewAdapter
import com.example.correctfit.databinding.FragmentDoYouKnowCurrentSizeBinding
import com.example.correctfit.response.RecyclerViewItem


class DoYouKnowCurrentSize : Fragment() {
      private lateinit var listArray: ArrayList<RecyclerViewItem.Data>
      private lateinit var recyclerViewAdapter: RecyclerViewAdapter
      private lateinit var binding: FragmentDoYouKnowCurrentSizeBinding
      private var current=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDoYouKnowCurrentSizeBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerViewAdapter.listen= this
        listArray= arrayListOf(
            RecyclerViewItem.Data(
                Title = "Do you know current size?", Type = arrayListOf(
                    RecyclerViewItem.Type(type = "YES, I DO!"),
                    RecyclerViewItem.Type(type = "NO, I NEED TO GET MY BRA SIZE")
            )
            ),
            RecyclerViewItem.Data(
                Title = "Do you want to know your panty size?", Type = arrayListOf(
                    RecyclerViewItem.Type(type = "YES"),
                    RecyclerViewItem.Type(type = "NO")
                )
            )
        )


        val listSize = listArray.size
        showWomanPageField(listArray[current])
        binding.womanButtonNext.setOnClickListener {
            if(current !=listSize-1){
                current++
                showWomanPageField(listArray[current])
            }

        }
        binding.womenButtonBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("current",1)
            findNavController().navigate(R.id.phaseOfWomanHood,bundle)
        }




    }

    private fun showWomanPageField(data: RecyclerViewItem.Data) {
        binding.CurrentSizeTitle.text=data.Title
        binding.CurrentSizeRecycle.apply {
        layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        recyclerViewAdapter.items=data.Type
        adapter=recyclerViewAdapter
            recyclerViewAdapter.label = "currentSize"
        }

        recyclerViewAdapter.itemClickListener={ view, item, position ->
            when(item){
                is RecyclerViewItem.Type -> {
                    listArray[current].Type.forEach {
                        it.default=false
                    }
                    listArray[current].Type[position].default=true
                    recyclerViewAdapter.items= listArray[current].Type
                    if(arguments?.getString("page") == "phase"){
                        when(position){
                            0 -> {}
                            1 -> findNavController().navigate(R.id.action_doYouKnowCurrentSize_to_measureYourSelf)
                        }
                    }
                }
                else -> {}
            }
        }
    }


}


