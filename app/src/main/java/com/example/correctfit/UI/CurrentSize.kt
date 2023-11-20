package com.example.correctfit.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import com.example.correctfit.utils.arrayBust
import com.example.correctfit.utils.arrayCup
import com.example.correctfit.utils.sportArray


class CurrentSize : BaseFragment<AuthViewModel,FragmentCurrentSizeBinding,AuthRepository>() {


//    private var arrayBust = mutableListOf<String>()
//    private var arrayCup = mutableListOf<String>()
    var data: List<String>? = null
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var recyclerViewBustAdaptor:RecyclerViewAdapter
    lateinit var recyclerViewCupAdaptor:RecyclerViewAdapter

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCurrentSizeBinding.inflate(inflater)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.appButtonNext.setOnClickListener {
            findNavController().navigate(R.id.action_currrentSize_to_braFitProperties)
        }
        binding.appButtonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        observers()
        initView()
    }


    private fun observers(){
        viewModel.sizeResponse.observe(viewLifecycleOwner){ it ->
            (requireActivity() as MainActivity).binding.progress.isVisible = it is Resource.Loading
            when (it){
              is Resource.Success-> {
                  val digitList = mutableListOf<String>()
                  val characterList = mutableListOf<String>()
//                  val array = arrayListOf<String>()
//                  val carry = arrayListOf<String>()
                  if(it.value.status){
                      data = it.value.data.size

                      for (size in data!!) {
                          val digits = StringBuilder()
                          val characters = StringBuilder()

                          for (char in size) {
                              if (char.isDigit()) {
                                  digits.append(char)
                              } else if (char.isLetter()) {
                                  characters.append(char)
                              }
                          }

                          digitList.add(digits.toString())
                          characterList.add(characters.toString())
                      }

                      updateAdapter(binding.braSizeRecycler, digitList.distinct())
                      updateAdapter(binding.CupSizeRecycler, characterList.distinct())

                      itemClickListener()


                  }
              }

                else -> {}
            }
              }
    }

    private fun updateAdapter(recyclerView: RecyclerView, data: List<String>) {
        val recyclerViewAdapter = RecyclerViewAdapter()
        recyclerViewAdapter.item = data

        recyclerView.apply {
            recyclerViewAdapter.label="bust"
            recyclerViewAdapter.label="cup"
            adapter=recyclerViewAdapter
            setHasFixedSize(true)
        }


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
    private fun itemClickListener(){
        recyclerViewAdapter.itemClickListener ={ view, item, position ->

            recyclerViewAdapter.item =data?: emptyList()
            Toast.makeText(requireContext(),item.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        recyclerViewAdapter = RecyclerViewAdapter()
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



