package com.example.correctfit.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.correctfit.R
import com.example.correctfit.model.UserData

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

var digitList: List<String>? = null
var characterList : ArrayList<String>? = null


fun String.getSisterSize(): String {
    val band = filter { it.isDigit() }.toIntOrNull()?.plus(2) ?: 0
    val cup1 = filter { it.isLetter() }

    val cup = if (cup1.length == 1) {
        singleOrNull { !it.isDigit() }?.let { char ->
            if (char.isLetter() && (char != 'A')) (char - 1).toString() else "AA"
        } ?: ""
    } else {
        when (cup1) {
            "AA" -> "AAA"
            "DD" -> "D"
            else -> ""
        }
    }

    return "$band$cup"
}
var userData = UserData()

//for bust & cup calculation
var bustPosition:Int ?= null
var cupPosition:Int ?=null

//var calculateBustPosition:Int ?= null
//var calculateCupPosition:Int ?=null


var currentSelected = -1
var previousSelected = -1
var currentSelected1 = -1
var previousSelected1 = -1
var currentSelectedCup = -1
var previousSelectedCup = -1

