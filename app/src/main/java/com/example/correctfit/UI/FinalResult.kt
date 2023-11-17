package com.example.correctfit.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentFinalResultBinding

class FinalResult : BaseFragment<AuthViewModel, FragmentFinalResultBinding, AuthRepository>() {


    override fun getViewModel()= AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentFinalResultBinding.inflate(inflater,container,false)

    override fun getFragmentRepository()= AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
            val braSize = arguments?.getString("braSize")
            val sisterSize=arguments?.getString("sisterSize")
            val pantySize =arguments?.getString("pantySize")

           binding.BraSize.text=braSize
           binding.SisterSize.text=sisterSize
           binding.PantySize.text=pantySize


        binding.StartAgain.setOnClickListener {
            while(findNavController().popBackStack()) {
              //
            }
            findNavController().navigate(R.id.shoulderTypeBroad)
        }
        binding.ShopNow.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shyaway.com/bra-online/${braSize}-bra/"))
            startActivity(browserIntent)
        }
    }

}