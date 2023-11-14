package com.example.correctfit.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.correctfit.R
import com.example.correctfit.databinding.FragmentShoulderTypeBroadBinding
import com.example.correctfit.response.RecyclerViewItem


class ShoulderTypeBroad : Fragment() {
     private lateinit var newRecyclerView: RecyclerView
     lateinit var binding : FragmentShoulderTypeBroadBinding
    var current = 0
    var currentType = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoulderTypeBroadBinding.inflate(inflater)
        return  binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val array = arrayListOf(
            RecyclerViewItem.Data(
                "what is your Shulder type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Shoulder broader than waist",
                        type = "BROAD",
                        default = false,
                        image = R.drawable.broadshoulder

                    ),
                    RecyclerViewItem.Type(
                        "Shoulder proportionate to waist",
                        type = "REGULAR",
                        default = true,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Shoulder smaller than waist",
                        type = "NARROW",
                        default = false,
                        image = R.drawable.broadshoulder
                    )
                )
            ),
            RecyclerViewItem.Data(
                "what is your Bust type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast with fullness both ath the top and bottom",
                        type = "APPLE",
                        default = false,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Breast have more volume towards the bottom",
                        type = "PEAR",
                        default = true,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Breast are small less volume",
                        type = "LEMON",
                        default = false,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Breast are small less",
                        type = "BSW",
                        default = false,
                        image = R.drawable.broadshoulder
                    )
                )
            ),
            RecyclerViewItem.Data(
                "what is your Placement type", Type = arrayListOf(
                    RecyclerViewItem.Type(
                        "Breast have no space between them",
                        type = "CLOSE SET",
                        default = false,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Brest having one finger space between them",
                        type = "REGULAR",
                        default = true,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Brest having three finger space between them",
                        type = "WIDE SET",
                        default = false,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Shoulder smaller than waist",
                        type = "WIDE",
                        default = false,
                        image = R.drawable.broadshoulder
                    ),
                    RecyclerViewItem.Type(
                        "Shoulder smaller than waist",
                        type = "SET",
                        default = false,
                        image = R.drawable.broadshoulder
                    )
                )
            )
        )

        val listSize = array.size
        showFiled(array[current])
        showTypeChange(array[current].Type[currentType])
        setType(array[current].Type)

        binding.appButtonNext.setOnClickListener {
            if (current != listSize-1) {
                current++
                currentType = 0
                binding.seekBar.max= (array[current].Type.size-1)*10
                binding.seekBar.progress=0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            }else {
                findNavController().navigate(R.id.action_shoulderTypeBroad_to_phaseOfWomanHood)
            }

        }
        binding.appButtonBack.setOnClickListener {
            if (current != 0) {
                current--
                currentType = 0
                binding.seekBar.max= (array[current].Type.size-1)*10
                binding.seekBar.progress =0
                showFiled(array[current])
                showTypeChange(array[current].Type[currentType])
                setType(array[current].Type)
            }

        }
        binding.seekBar.setOnSeekBarChangeListener(object  :SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                Log.e("array length",array[current].Type.size.toString())
//                Log.e("array length",current.toString())

                val typeSize = array[current].Type.size

                if (typeSize == 3) {
                    currentType = if (progress < 9) {
                        0
                    } else if (progress < 19) {
                        1
                    } else {
                        2
                    }
                } else if (typeSize == 4) {
                    currentType = if (progress < 5) {
                        0
                    } else if (progress < 8) {
                        1
                    } else if (progress < 16) {
                        2
                    } else {
                        3
                    }
                } else if (typeSize == 5) {
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
        binding.changeType1.text= type[0].type
        binding.changeType2.text=type[1].type
        binding.changeType3.text=type[2].type
    }

    private fun showTypeChange(type: RecyclerViewItem.Type) {
        binding.TypeNames.text = type.type
        binding.discriptionNames.text = type.description
        type.image?.let { binding.BroadShoulderImg.setImageResource(it) }

    }

    private fun showFiled(data: RecyclerViewItem.Data) {
     binding.WhtShTyp.text =data.Title
    }
}
