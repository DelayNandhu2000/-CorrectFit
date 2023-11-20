package com.example.correctfit.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.Base.BaseFragment
import com.example.correctfit.R
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.ViewModel.AuthViewModel
import com.example.correctfit.databinding.FragmentBraFitPropertiesBinding
import com.example.correctfit.databinding.FragmentShoulderTypeBroadBinding
import com.example.correctfit.response.RecyclerViewItem


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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
                "what is your Placement type", Type = arrayListOf(
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

        val listSize = array.size
        showFiled(array[current])
        showTypeChange(array[current].Type[currentType])
        setType(array[current].Type)

        binding.appButtonNext.setOnClickListener {
            if (current != listSize - 1) {
                current++
                currentType = 0
                binding.seekBar.max = (array[current].Type.size - 1) * 10
                binding.seekBar.progress = 0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            } else {
                  val bundle =Bundle()
                  bundle.putInt("current",1)
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
                        currentType = if (progress < 9) {
                            0
                        } else if (progress < 19) {
                            1
                        } else {
                            2
                        }
                    }

                    4 -> {
                        currentType = if (progress < 5) {
                            0
                        } else if (progress < 8) {
                            1
                        } else if (progress < 16) {
                            2
                        } else {
                            3
                        }
                    }

                    5 -> {
                        currentType = if (progress < 5) {
                            0
                        } else if (progress < 20) {
                            1
                        } else if (progress < 30) {
                            2
                        } else if (progress < 40) {
                            3
                        } else {
                            4
                        }
                    }
                }

                Log.e("array length 1", typeSize.toString())
                showTypeChange(array[current].Type[currentType])

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Handle touch stop if needed
            }
        })


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
        val typeface1 = ResourcesCompat.getFont(requireContext(), R.font.ingter)

        val colorSelected = ContextCompat.getColor(requireContext(),R.color.ourBlack)
        val colorUnSelected = ContextCompat.getColor(requireContext(),R.color.gray)
        when (currentType) {
            0 -> {
                binding.changeType1.apply {
                    typeface = typeface
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
                    typeface =typeface
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
                    typeface=typeface
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





