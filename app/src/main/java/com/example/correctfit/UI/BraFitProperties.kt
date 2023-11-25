package com.example.correctfit.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentBraFitPropertiesBinding
import com.example.correctfit.response.RecyclerViewItem
import com.example.correctfit.utils.bustPosition
import com.example.correctfit.utils.characterList
import com.example.correctfit.utils.cupPosition
import com.example.correctfit.utils.digitList
import com.example.correctfit.utils.userData

class BraFitProperties : BaseFragment<AuthViewModel,FragmentBraFitPropertiesBinding,AuthRepository>() {


    override fun getViewModel()=AuthViewModel::class.java
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    )= FragmentBraFitPropertiesBinding.inflate(inflater)

    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthInterface::class.java))


    private lateinit var newRecyclerView: RecyclerView
    var current = 0
    var currentType = 0



    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()
        current = arguments?.getInt("current") ?: 0

        val array = arrayListOf(
            RecyclerViewItem.Data(
                "How does the band fit?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Bra band feels loose",
                        type = "LOOSENESS",
                        default = false,
                        image = R.drawable.band_fit_back

                    ),
                    RecyclerViewItem.Type(
                        "Bra band fits perfectly",
                        type = "FIT WELL",
                        default = true,
                        image = R.drawable.band_fit_well
                    ),
                    RecyclerViewItem.Type(
                        "Bra band feels tight",
                        type = "TIGHTNESS",
                        default = false,
                        image = R.drawable.dig_in
                    )
                )
            ),
            RecyclerViewItem.Data(
                "Which hook does your bra fit?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Bra fits in the last hook",
                        type = "TIGHTEST",
                        default = false,
                        image = R.drawable.img_hook_tight

                    ),
                    RecyclerViewItem.Type(
                        "Bra fit in the middle hook",
                        type = "MIDDLE",
                        default = true,
                        image = R.drawable.img_hook_middle
                    ),
                    RecyclerViewItem.Type(
                        "Bra fit in the first hook",
                        type = "LOOSEST",
                        default = false,
                        image = R.drawable.img_hook_loose
                    )
                )
            ),
            RecyclerViewItem.Data(
                "How does the cup fit?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast spill in the front and sides",
                        type = "SPILLAGE",
                        default = false,
                        image = R.drawable.cup_front_spill
                    ),
                    RecyclerViewItem.Type(
                        "Cup fit perfectly",
                        type = "PERFECT",
                        default = true,
                        image = R.drawable.cup_perfect
                    ),
                    RecyclerViewItem.Type(
                        "Gap between breast and bra cup",
                        type = "GAPING",
                        default = false,
                        image = R.drawable.img_cup_gupping
                    ),

                    )
            ),
            RecyclerViewItem.Data(
                "How does the strap fit?", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast have no space between them",
                        type = "DIGS IN",
                        default = false,
                        image = R.drawable.strap_dig_in
                    ),
                    RecyclerViewItem.Type(
                        "Brest having one finger space between them",
                        type = "FITS WELL",
                        default = true,
                        image = R.drawable.strap_fit
                    ),
                    RecyclerViewItem.Type(
                        "Brest having three finger space between them",
                        type = "FALLS OFF",
                        default = false,
                        image = R.drawable.strap_fall_off
                    ),

                    )
            )
        )
        currentType = array[current].Type.indexOfFirst { it.default}
        binding.seekBar.progress = if(currentType ==0) 0 else 10

        val listSize = array.size
        showFiled(array[current])
        showTypeChange(array[current].Type[currentType])
        setType(array[current].Type)
        binding.appButtonNext.setOnClickListener {
            if (current != listSize - 1) {
                when(current){
                    0->{
                        userData.bandSelectedValue = array[current].Type[currentType].type
                    }
                    1-> {
                        userData.hookSelectedValue = array[current].Type[currentType].type
                    }
                  2-> {
                      userData.cupSelectedValue = array[current].Type[currentType].type
                  }
                }
                current++
                currentType = 0
                binding.seekBar.max = (array[current].Type.size - 1) * 10
                binding.seekBar.progress = 0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            } else {
                userData.strapSelectedValue = array[current].Type[currentType].type

                when(userData.bandSelectedValue){
                    "LOOSENESS"->{
                        when(userData.hookSelectedValue){
                            "TIGHTEST"->{
                                bustPosition=bustPosition?.let {
                                    it-1
                                }

                                cupPosition=cupPosition?.let {
                                    it+1
                                }

                                when(userData.cupSelectedValue){
                                    "SPILLAGE"->{
                                        cupPosition =cupPosition?.let {
                                            it+1
                                        }
                                    }
                                    "GAPING"->{
                                        cupPosition = cupPosition?.let {
                                            it-1
                                        }
                                    }
                                    "PERFECT"->{}
                                }

                            }
                            "MIDDLE"->{
                                when(userData.cupSelectedValue){
                                    "SPILLAGE"->{
                                        cupPosition =cupPosition?.let {
                                            it+1
                                        }
                                    }
                                    "GAPING"->{
                                        cupPosition = cupPosition?.let {
                                            it-1
                                        }
                                    }
                                    "PERFECT"->{}
                                }
                            }
                            "LOOSEST"->{

                                bustPosition=bustPosition?.let {
                                    it-1
                                }

                                cupPosition=cupPosition?.let {
                                    it+1
                                }
                                when(userData.cupSelectedValue){
                                    "SPILLAGE"->{
                                        cupPosition =cupPosition?.let {
                                            it+1
                                        }
                                    }
                                    "GAPING"->{
                                        cupPosition = cupPosition?.let {
                                            it-1
                                        }
                                    }
                                    "PERFECT"->{}
                                }
                            }
                        }
                    }
                    "FIT WELL"->{
                        when(userData.cupSelectedValue){
                            "SPILLAGE"->{
                                cupPosition =cupPosition?.let {
                                    it+1
                                }
                            }
                            "GAPING"->{
                                cupPosition = cupPosition?.let {
                                    it-1
                                }
                            }
                            "PERFECT"->{}
                        }
                    }
                    "TIGHTNESS"->{

                        if(userData.hookSelectedValue == "LOOSEST") {
                            bustPosition = bustPosition?.let {
                                it + 1
                            }

                            cupPosition = cupPosition?.let {
                                it - 1
                            }
                        }
                        when(userData.cupSelectedValue){
                            "SPILLAGE"->{
                                cupPosition =cupPosition?.let {
                                    it+1
                                }
                            }
                            "GAPING"->{
                                cupPosition = cupPosition?.let {
                                    it-1
                                }
                            }
                            "PERFECT"->{}
                        }
                    }
                }
                userData.finalResult = digitList?.get(bustPosition!!)!!+ characterList!![cupPosition!!]
                viewModel.addUser(userData)
                  Log.e("final size", digitList?.get(bustPosition!!)!!+ characterList!![cupPosition!!])
                  Log.e("userData", userData.toString())

                  val bundle =Bundle()
                  bundle.putInt("current",1)
                  bundle.putString("finalSize", userData.finalResult)
                  findNavController().navigate(R.id.doYouKnowCurrentSize,bundle)
            }

        }
        binding.appButtonBack.setOnClickListener {
            if (current != 0) {
                current--
                currentType = 0
                binding.seekBar.max = (array[current].Type.size - 1) * 10
                binding.seekBar.progress = 0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            }else{

                findNavController().popBackStack()
            }

        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Log.e("array length",array[current].Type.size.toString())
//                Log.e("array length",current.toString())

                val typeSize = array[current].Type.size

                when (typeSize) {
                    3 -> {
                        currentType = if (progress < 5) {
                            0
                        } else if (progress < 15) {
                            1
                        } else {
                            2
                        }
                    }

                    3 -> {
                        currentType = if (progress < 5) {
                            0
                        } else if (progress < 15) {
                            1
                        } else {
                            2
                        }
                    }

                    3 -> {
                        currentType = if (progress < 5) {
                            0
                        } else if (progress < 15) {
                            1
                        } else {
                            2
                        }
                    }
                }

                Log.e("array length 1", typeSize.toString())
                showTypeChange(array[current].Type[currentType])

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar?.progress!! < 5){
                    binding.seekBar.progress = 0
                } else if(seekBar.progress <15){
                    binding.seekBar.progress =10
                }else {
                    binding.seekBar.progress=20
                }
            }
        })


    }



    private fun observers() {

        viewModel.addUserResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    MainActivity.progress.isVisible = false
                    val bundle = Bundle()
                    if (it.value.status) {
                        Log.e("addUser",it.value.status.toString())
                        bundle.putString("url", it.value.url)
                        findNavController().navigate(R.id.action_measureYourSelf_to_finalResult2,bundle)
                    } else {
                        bundle.putString("url", null)
                        findNavController().navigate(R.id.action_measureYourSelf_to_finalResult2,bundle)
                    }
                }

                is Resource.Failure -> {
                    MainActivity.progress.isVisible = false
                }

                else -> {}
            }
        }
    }

    private fun setType(type: List<RecyclerViewItem.Type>) {
        binding.changeType1.text = type[0].type
        binding.changeType2.text = type[1].type
        binding.changeType3.text = type[2].type
    }
    private fun showTypeChange(type: RecyclerViewItem.Type) {
        binding.TypeNames.text = type.type
        binding.discriptionNames.text = type.description
        type.image?.let { binding.BroadShoulderImg.setImageResource(it) }

        var typeface = ResourcesCompat.getFont(requireContext(), R.font.interbold)
        val typeface1 = ResourcesCompat.getFont(requireContext(), R.font.inter)

        val colorSelected = ContextCompat.getColor(requireContext(),R.color.ourBlack)
        val colorUnSelected = ContextCompat.getColor(requireContext(),R.color.gray)
        when (currentType) {
            0 -> {
                binding.changeType1.apply {
                    setTextColor(colorSelected)
                }
                binding.changeType2.apply {
                    typeface = typeface1
                    setTextColor(colorUnSelected)
                }
                binding.changeType3.apply {
                    typeface = typeface1
                    setTextColor(colorUnSelected)
                }

                binding.imageView.setImageResource(R.drawable.thump)
                binding.imageView2.setImageResource(R.drawable.un_selected_thumb)
                binding.imageView3.setImageResource(R.drawable.un_selected_thumb)
            }
            1 -> {
                binding.changeType1.apply {
                    typeface =typeface1
                    setTextColor(colorUnSelected)
                }
                binding.changeType2.apply {
                    setTextColor(colorSelected)
                }
                binding.changeType3.apply {
                    typeface =typeface1
                    setTextColor(colorUnSelected)
                }

                binding.imageView.setImageResource(R.drawable.un_selected_thumb)
                binding.imageView2.setImageResource(R.drawable.thump)
                binding.imageView3.setImageResource(R.drawable.un_selected_thumb)
            }
            2 -> {
                binding.changeType1.apply {
                    typeface =typeface1
                    setTextColor(colorUnSelected)
                }
                binding.changeType2.apply {
                    typeface =typeface1
                    setTextColor(colorUnSelected)
                }
                binding.changeType3.apply {
                    setTextColor(colorSelected)
                }

                binding.imageView.setImageResource(R.drawable.un_selected_thumb)
                binding.imageView2.setImageResource(R.drawable.un_selected_thumb)
                binding.imageView3.setImageResource(R.drawable.thump)
            }
        }
        }

    private fun showFiled(data: RecyclerViewItem.Data) {
        binding.WhtShTyp.text = data.Title
    }

    }





