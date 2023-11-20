package com.example.correctfit.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.correctfit.R

class Effect(context : Context){
    val shake : Animation = AnimationUtils.loadAnimation(context,R.anim.shake)

}

var flager = false
var destroy = false
var exitControl = false

var androidId:String?=null
fun View.startAnimations(animation: Animation, onEndListener: () -> Unit) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) = Unit

        override fun onAnimationEnd(animation: Animation?) {
            onEndListener()
        }

        override fun onAnimationRepeat(animation: Animation?) = Unit
    })

    this.startAnimation(animation)
}

var arrayBust = arrayListOf<String>()
var arrayCup = arrayListOf("AA", "A", "B", "C", "DD", "D", "E", "F", "G","H","I","J")

val sportArray = arrayListOf("XXL", "XXXL", "XXXXL","Free Size")