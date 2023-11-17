package com.example.correctfit.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.RecycleViewMangement.RecyclerViewAdapter
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.RequestClass
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentMeasureYourSelfBinding
import com.example.correctfit.response.FinalResponse
import com.example.correctfit.response.RecyclerViewItem
import com.example.correctfit.utils.Effect
import com.example.correctfit.utils.startAnimations

class MeasureYourSelf : BaseFragment<AuthViewModel, FragmentMeasureYourSelfBinding, AuthRepository>() {

    private lateinit var listArray: ArrayList<RecyclerViewItem.Data>
    private var recyclerViewAdaptor = RecyclerViewAdapter()

    override fun getViewModel()=AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentMeasureYourSelfBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))


    private var current = 0
    private var currentType = 0
    var effect: Effect? = null


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewAdaptor = RecyclerViewAdapter()
        recyclerViewAdaptor.lis = this
        effect = Effect(requireContext())
        listArray = arrayListOf(
            RecyclerViewItem.Data(
                Title = "Measure your self", image = R.drawable.measure_img,
                Type = arrayListOf(
                    RecyclerViewItem.Type(type = "Stand straight and breath normally"),
                    RecyclerViewItem.Type(type = "Bring the measuring tape around your torso ,right beneath your bust"),
                    RecyclerViewItem.Type(type = "Next, Wrap the tap at the highest point of your bust"),
                    RecyclerViewItem.Type(type = "The tap should not be held too tight or loose, it should be taut"),
                    RecyclerViewItem.Type(type = "Record the measurement in centimeter and enter them below")
                )
            ),
            RecyclerViewItem.Data(
                Title = "Measure your self", image = R.drawable.img_panty,
                Type = arrayListOf(
                    RecyclerViewItem.Type(type = "Bring the measure tap around the fullest part of your hips"),
                    RecyclerViewItem.Type(type = "The tap must be parallel to the floor and the fit must be snug"),
                    RecyclerViewItem.Type(type = "Record the measurement in cm"),
                    RecyclerViewItem.Type(type = "Refer to shyaway's Panty Size Chart to find your correct panty size")
                )
            )
        )

        val listSize = listArray.size
        showFiled(listArray[current])
        binding.womanButtonNext.setOnClickListener {
            if (current != listSize - 1){
                    if (binding.BandEG.text.trim().isEmpty()) {
                        binding.BandEG.startAnimations(effect!!.shake) {
                            binding.BandError.text = "Band size is required.." + ("\uD83D\uDE01")
                            binding.BandError.isVisible = true
                        }
                    }else if(binding.BandEG.text?.trim().toString().toInt()<40 || binding.BandEG.text?.trim().toString().toInt()>160){
                        binding.BandEG.startAnimations(effect!!.shake){
                            binding.BandError.text ="Enter valid band size.." +("\uD83D\uDE01")
                            binding.BandError.isVisible = true
                        }
                    }else if (binding.BustEG.text.trim().isEmpty()) {
                        binding.BustEG.startAnimations(effect!!.shake) {
                            binding.BustError.text = "Bust size is required.." + ("\uD83D\uDE01")
                            binding.BustError.isVisible = true
                        }
                    }else if(binding.BustEG.text.trim().toString().toInt()<55 || binding.BustEG.text.trim().toString().toInt()>150){
                        binding.BustEG.startAnimations(effect!!.shake){
                            binding.BustError.text ="Enter valid bust size.." +("\uD83D\uDE01")
                            binding.BustError.isVisible = true
                        }
                }else if((binding.BandEG.text.trim().toString().toInt())==(binding.BustEG.text.trim().toString().toInt())){
                       binding.BustEG.startAnimations(effect!!.shake){
                           binding.BustError.text="The measurement is incorrect.." +("\uD83D\uDE01")
                           binding.BustError.isVisible=true
                       }
                }
                    else {
                    current++
                    showFiled(listArray[current])
                }

            }else {
                if (binding.hip.text.trim().isEmpty()) {
                    binding.hip.startAnimations(effect!!.shake) {
                        binding.HipError.text = "Hip size is required.." + ("\uD83D\uDE01")
                        binding.HipError.isVisible = true
                    }
                }else if(binding.hip.text.trim().toString().toInt()<50 || binding.hip.text.trim().toString().toInt()>170) {
                    binding.hip.startAnimations(effect!!.shake) {
                    binding.HipError.text = "Enter valid hip size.." + ("\uD83D\uDE01")
                    binding.HipError.isVisible = true
                }
                }
                else {
                    val objectRef = RequestClass.MeasureRequestClass(
                        binding.BandEG.text.trim().toString(),
                        binding.BustEG.text.trim().toString(),
                        binding.hip.text.trim().toString()
                    )
                    val map=HashMap<String,String>()
                    map["apikey"] ="qIAygMyilfNeYfgnkNtglD2h"
                    viewModel.measureSize(map,objectRef)
                }
            }
        }
        binding.womenButtonBack.setOnClickListener {
            if(current !=0){
                current--
                showFiled(listArray[current])
            }
//            else{
//                findNavController().navigate(R.id.doYouKnowCurrentSize)
//            }
        }
       observers()
    }
    private fun showFiled(data: RecyclerViewItem.Data) {
        binding.womenButtonBack.isVisible= current !=0
        binding.Layout.isVisible = current == 0
        binding.hip.isVisible = !binding.Layout.isVisible
        binding.MeasureYourSelf.text = data.Title
        data.image.let {
            if (it != null) {
                binding.MeasureIMG.setImageResource(it)
            }
        }
        binding.MeasureRecycle.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerViewAdaptor.label = "measure"
            recyclerViewAdaptor.items = data.Type
            adapter = recyclerViewAdaptor

        }
    }

    private fun observers(){
        viewModel.measureResponse.observe(viewLifecycleOwner){result->
            (requireActivity() as MainActivity).binding.progress.isVisible = result is Resource.Loading
            when (result){
                   is Resource.Success ->{
                       if(result.value.status) {
                           val data = result.value.data
                           val bundle = Bundle()
                           bundle.putString("braSize",data.bra)
                           bundle.putString("sisterSize",if(data.sisterSize=="")"No" else data.sisterSize)
                           bundle.putString("pantySize",data.panty)
                           findNavController().navigate(R.id.action_measureYourSelf_to_finalResult2,bundle)

                       } else {
                           Toast.makeText(requireContext(), result.value.message, Toast.LENGTH_SHORT).show()
                       }
                   }

                is Resource.Failure -> Toast.makeText(requireContext(), result.isNetworkError.toString(), Toast.LENGTH_SHORT).show()
                else -> {}
            }


            }





        }
    }






