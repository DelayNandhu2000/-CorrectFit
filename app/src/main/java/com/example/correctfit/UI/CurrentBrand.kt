package com.example.correctfit.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.RecycleViewMangement.RecyclerViewAdapter
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentCurrentBrandBinding


class CurrentBrand : BaseFragment<AuthViewModel,FragmentCurrentBrandBinding,AuthRepository>() {


    lateinit var recyclerViewAdaptor : RecyclerViewAdapter
    var data: List<String>?= null
    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCurrentBrandBinding.inflate(inflater)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appButtonNext.setOnClickListener {
          findNavController().navigate(R.id.action_currentBrand_to_currrentSize)
        }

        initView()
        observers()
    }

    private fun observers() {
        viewModel.brandResponse.observe(viewLifecycleOwner){
            (requireActivity() as MainActivity).binding.progress.isVisible = it is Resource.Loading

            when(it){
                is Resource.Success -> {
                    if(it.value.status){
                        data = it.value.data.brands
                        binding.recyclerView.apply {
                            recyclerViewAdaptor.label = "brand"
                            recyclerViewAdaptor.item = data?: emptyList()
                            adapter = recyclerViewAdaptor
                            setHasFixedSize(true)
                        }

                        itemClickListener()
                    }
                }

                else -> {}
            }
        }
    }

    private fun itemClickListener() {
        recyclerViewAdaptor.itemClickListener = { view, item, position ->
            recyclerViewAdaptor.item = data?:emptyList()
//            Toast.makeText(requireContext(), item.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        recyclerViewAdaptor = RecyclerViewAdapter()
        invokeBrandApi()
    }

    private fun invokeBrandApi() {
        viewModel.getBrands()
    }


}