package com.example.correctfit.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.R
import com.example.correctfit.RecycleViewMangement.RecyclerViewAdapter
//import com.example.correctfit.WomanHood
import com.example.correctfit.databinding.FragmentPhaseOfWomanHoodBinding
import com.example.correctfit.response.RecyclerViewItem


class PhaseOfWomanHood : Fragment(){
      private lateinit var newRecyclerView: RecyclerView
      private lateinit var newArrayList: ArrayList<RecyclerViewItem.Type>
      private lateinit var listArray: ArrayList<RecyclerViewItem.Data>
      private lateinit var binding: FragmentPhaseOfWomanHoodBinding
      private var recyclerViewAdaptor = RecyclerViewAdapter()

      private var current =0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
       binding= FragmentPhaseOfWomanHoodBinding.inflate(inflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        current = arguments?.getInt("current")?:0
        recyclerViewAdaptor = RecyclerViewAdapter()
        recyclerViewAdaptor.listener = this
        listArray = arrayListOf(
            RecyclerViewItem.Data(
                Title = "Which phase of womanhood are you in?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        type = "TEENAGE",
                        description = "Initial stage of bust development after puberty"
                    ),
                    RecyclerViewItem.Type(
                        type = "PRIME ADULTS",
                        description = "Woman between 18-35 years of age"
                    ),
                    RecyclerViewItem.Type(
                        type = "MATERNITY / NURSING",
                        description = "Pregnant and breast feeding woman"
                    ),
                    RecyclerViewItem.Type(
                        type = "PRE-MENOPAUSE / POST-MENOPAUSE",
                        description = "Woman undergoing stopping of menstrual cycle"
                    ),
                    RecyclerViewItem.Type(type = "SENIORS", description = "Woman aged 50 above")
                )
            ),
            RecyclerViewItem.Data(
                Title = "Which phase of menstrual cycle are you in?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        type = "15 DAYS BEFORE PERIODS",
                        description = "Menstruation Coming up in 15 days"
                    ),
                    RecyclerViewItem.Type(
                        type = "PERIODS",
                        description = "Currently in menstruation"
                    ),
                    RecyclerViewItem.Type(
                        type = "15 DAYS AFTER PERIODS",
                        description = "15 days after completing menstruation"
                    ),
                    RecyclerViewItem.Type(
                        type = "PCOD / IRREGULAR PERIODS / PCOS",
                        description = "Irregular menstruation"
                    ),
                    RecyclerViewItem.Type(type = "NO PERIODS", description = "No menstrual cycle")
                )
            )
        )

        val listSize = listArray.size
        showWomanPageField(listArray[current])
        binding.womanButtonNext.setOnClickListener {
            if(current !=listSize-1){
                val selectedItem=getSelectedItem()
                if(selectedItem !=null) {
                    current++
                    showWomanPageField(listArray[current])
                }else{
                    Toast.makeText(requireContext(),"please select your womanhood",Toast.LENGTH_SHORT).show()
                }
            }else{
                val selectedItem=getSelectedItem()
                if(selectedItem !=null) {
                    val bundle = Bundle()
                    bundle.putString("page", "phase")
                    findNavController().navigate(
                        R.id.action_phaseOfWomanHood_to_doYouKnowCurrentSize,
                        bundle
                    )
                }else{
                    Toast.makeText(requireContext(),"please select your menstrual cycle",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.womenButtonBack.setOnClickListener {
            if(current !=0){
                current--
                showWomanPageField(listArray[current])
            } else {
                val bundle =Bundle()
                bundle.putInt("current",2)
                findNavController().navigate(R.id.shoulderTypeBroad,bundle)
            }
        }


    }

    private fun getSelectedItem(): RecyclerViewItem.Type?  {
        return listArray[current].Type.find { it.default }
    }

    private fun showWomanPageField(data: RecyclerViewItem.Data) {
        binding.WomenHoodmainTiltle.text =data.Title
        binding.womanHoodRecycle.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            recyclerViewAdaptor.items = data.Type
            adapter = recyclerViewAdaptor
        }

      recyclerViewAdaptor.itemClickListener={ view, item, position ->
          when(item){
              is RecyclerViewItem.Type -> {
                  listArray[current].Type.forEach {
                       it.default=false
                   }
                   listArray[current].Type[position].default=true
                   recyclerViewAdaptor.items = listArray[current].Type
              }
              else ->{}
          }
      }
    }



//   override fun boxClicked(position:Int){
//       listArray[current].Type.forEach {
//           it.default=false
//       }
//       listArray[current].Type[position].default=true
//       recyclerViewAdaptor.listItems = listArray[current].Type
//   }


}