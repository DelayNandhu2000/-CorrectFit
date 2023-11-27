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
import com.example.correctfit.R
import com.example.correctfit.databinding.FragmentShoulderTypeBroadBinding
import com.example.correctfit.response.RecyclerViewItem
import com.example.correctfit.utils.userData


var previousSelectValue :String?=null
class ShoulderTypeBroad : Fragment() {
    private lateinit var newRecyclerView: RecyclerView
    lateinit var binding: FragmentShoulderTypeBroadBinding
    var current = 0
    var currentType = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoulderTypeBroadBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        current = arguments?.getInt("current") ?: 0

        val array = arrayListOf(
            RecyclerViewItem.Data(
                "what is your shoulder type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Shoulder broader than waist",
                        type = "BROAD",
                        default = false,
                        image = R.drawable.img_shoulder_broad
                    ),
                    RecyclerViewItem.Type(
                        "Shoulder proportionate to waist",
                        type = "REGULAR",
                        default = true,
                        image = R.drawable.img_ragular
                    ),
                    RecyclerViewItem.Type(
                        "Shoulder smaller than waist",
                        type = "NARROW",
                        default = false,
                        image = R.drawable.img_narrow
                    )
                )
            ),
            RecyclerViewItem.Data(
                "what is your Bust type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast with fullness both ath the top and bottom",
                        type = "APPLE",
                        default = true,
                        image = R.drawable.img_apple
                    ),
                    RecyclerViewItem.Type(
                        "Breast have more volume towards the bottom",
                        type = "PEAR",
                        default = false,
                        image = R.drawable.img_flat
                    ),
                    RecyclerViewItem.Type(
                        "Breast are small less volume",
                        type = "LEMON",
                        default = false,
                        image = R.drawable.img_pear
                    ),

                    )
            ),
            RecyclerViewItem.Data(
                "what is your Placement type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast have no space between them",
                        type = "CLOSE SET",
                        default = false,
                        image = R.drawable.close_set
                    ),
                    RecyclerViewItem.Type(
                        "Brest having one finger space between them",
                        type = "REGULAR",
                        default = false,
                        image = R.drawable.regular
                    ),
                    RecyclerViewItem.Type(
                        "Brest having three finger space between them",
                        type = "WIDE SET",
                        default = true,
                        image = R.drawable.wide_set
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
                    0-> userData.shoulderSelectedValue = array[current].Type[currentType].type

                    1-> userData.bustSelectedValue = array[current].Type[currentType].type
                }
                current++
                binding.seekBar.max = (array[current].Type.size - 1) * 10
                binding.seekBar.progress = 0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            } else {
                when(current) {
                    2 -> userData.placementSelectedValue = array[current].Type[currentType].type
                }
                findNavController().navigate(R.id.action_shoulderTypeBroad_to_phaseOfWomanHood)
            }

        }
        previousSelectValue = array[current].Type[currentType].type
        binding.appButtonBack.setOnClickListener {
            if (current != 0) {
                current--
                currentType = 0
                binding.seekBar.max = (array[current].Type.size - 1) * 10
                binding.seekBar.progress = 0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
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
//                Toast.makeText(requireContext(), "calledx", Toast.LENGTH_SHORT).show()
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
        binding.appButtonBack.isVisible = current != 0
    }
}


//when(current){
//    0-> selectedValue = array[current].Type[currentType].type
//    1->selectedValue = array[current].Type[currentType].type
//    2->selectedValue = array[current].Type[currentType].type
//}