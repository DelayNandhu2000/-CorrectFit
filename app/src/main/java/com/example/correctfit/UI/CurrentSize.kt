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
import com.example.correctfit.utils.bustPosition
import com.example.correctfit.utils.characterList
import com.example.correctfit.utils.cupPosition
import com.example.correctfit.utils.digitList
import com.example.correctfit.utils.userData


class CurrentSize : BaseFragment<AuthViewModel, FragmentCurrentSizeBinding, AuthRepository>() {
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
            if(braSize==null){
                Toast.makeText(requireContext(), "Please select your bra size", Toast.LENGTH_SHORT).show()
            }else if(cupSize == null){
                Toast.makeText(requireContext(), "please select you cup size", Toast.LENGTH_SHORT).show()
            }
            else {

                userData.currentBandSize = bustPosition
                userData.currentCupSize = cupPosition
                findNavController().navigate(R.id.action_currrentSize_to_braFitProperties, bundle)
                 }
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
                         }?.distinct() as ArrayList<String>?

                        characterList?.apply {
                            remove("AA")
                            add(0,"AA")
                        }

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


    @SuppressLint("SetTextI18n")
    private var braSize: String?= null
    private var cupSize: String?= null
    private fun itemClickListener() {
        recyclerViewBustAdaptor.itemClickListener = { view, item, position ->
            braSize = item.toString()
           bustPosition = position
            updateTotalSizeText()
        }
        recyclerViewCupAdaptor.itemClickListener = { view, item, position ->
              cupSize =item.toString()
              cupPosition = position
              updateTotalSizeText()

        }
    }
    @SuppressLint("SetTextI18n")
    private fun updateTotalSizeText() {
        if (braSize != null && cupSize != null){
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





