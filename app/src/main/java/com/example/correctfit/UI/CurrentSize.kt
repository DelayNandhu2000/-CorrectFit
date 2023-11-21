package com.example.correctfit.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.RecycleViewMangement.RecyclerViewAdapter
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentCurrentSizeBinding
import com.example.correctfit.utils.characterList
import com.example.correctfit.utils.digitList

var bustPosition:Int ?= null
var cupPosition:Int ?=null
class CurrentSize : BaseFragment<AuthViewModel, FragmentCurrentSizeBinding, AuthRepository>() {


    //    private var arrayBust = mutableListOf<String>()
//    private var arrayCup = mutableListOf<String>()
    var data: List<String>? = null
    private lateinit var recyclerViewBustAdaptor: RecyclerViewAdapter
    private lateinit var recyclerViewCupAdaptor: RecyclerViewAdapter

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCurrentSizeBinding.inflate(inflater)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        binding.appButtonNext.setOnClickListener {
            findNavController().navigate(R.id.action_currrentSize_to_braFitProperties,bundle)
        }
        binding.appButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }
        initView()
        observers()

    }


    private fun observers() {
        viewModel.sizeResponse.observe(viewLifecycleOwner) { it ->
            (requireActivity() as MainActivity).binding.progress.isVisible = it is Resource.Loading
            when (it) {
                is Resource.Success -> {
                    if (it.value.status) {
                        data = it.value.data.size


                         digitList = data?.map {
                            it.filter { it.isDigit() }
                        }?.distinct()

                         characterList = data?.map {
                            it.filter { it.isLetter() }
                        }?.distinct()

                        Log.e("data list", digitList.toString())
                        Log.e("data list", characterList.toString())
                        //val characterList = array?.filter { it. }

                        updateAdapter(
                            binding.braSizeRecycler,
                            digitList ?: emptyList(),
                            label = "bust",
                            recyclerViewBustAdaptor
                        )
                        updateAdapter(
                            binding.CupSizeRecycler,
                            characterList ?: emptyList(),
                            label = "cup",
                            recyclerViewCupAdaptor
                        )
                    }
                }

                else -> {}
            }
        }
    }

    private fun updateAdapter(
        recyclerView: RecyclerView,
        data: List<String>,
        label: String,
        recyclerViewAdaptor: RecyclerViewAdapter
    ) {
        recyclerViewAdaptor.item = data
        recyclerView.apply {
            recyclerViewAdaptor.label = label
            adapter = recyclerViewAdaptor
            setHasFixedSize(true)
        }
        itemClickListener()


    }

    //    private fun setData() {
//        binding.braSizeRecycler.apply {
//            recyclerViewAdapter.label = "bust"
//            recyclerViewAdapter.item = data?: emptyList()
//            adapter = recyclerViewAdapter
//            setHasFixedSize(true)
//
//        }
//
//        binding.CupSizeRecycler.apply {
//            recyclerViewCupAdaptor.label = "cup"
//            recyclerViewAdapter.item = data?: emptyList()
//            adapter = recyclerViewAdapter
//            setHasFixedSize(true)
//
//        }
//    }
    private var braSize: String?= null
    private var cupSize: String?= null
    @SuppressLint("SetTextI18n")
    private fun itemClickListener() {
        recyclerViewBustAdaptor.itemClickListener = { view, item, position ->
            braSize = item.toString()
            bustPosition =position
            updateTotalSizeText()
            if(cupSize ==null){
                Toast.makeText(requireContext(), "please select cup size", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerViewCupAdaptor.itemClickListener = { view, item, position ->
              cupSize =item.toString()
              cupPosition = position
              updateTotalSizeText()
            if(braSize == null){
                Toast.makeText(requireContext(), "Please select bra size", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateTotalSizeText() {
        if (braSize != null && cupSize != null) {
            binding.totalSize.text = "$braSize$cupSize"
        }
    }

    private fun initView() {
        recyclerViewCupAdaptor = RecyclerViewAdapter()
        recyclerViewBustAdaptor = RecyclerViewAdapter()
        invokeBrandApi()
    }

    private fun invokeBrandApi() {
        viewModel.getTotalSize()
    }


}


//                      binding.braSizeRecycler.apply {
//                          recyclerViewAdapter.label = "bust"
//                          recyclerViewAdapter.item = data?: emptyList()
//                          adapter = recyclerViewAdapter
//                          setHasFixedSize(true)
//                      }
//
//                      binding.CupSizeRecycler.apply {
//                          recyclerViewAdapter.label = "cup"
//                          recyclerViewAdapter.item = data?: emptyList()
//                          adapter = recyclerViewAdapter
//                          setHasFixedSize(true)
//
//                      }



