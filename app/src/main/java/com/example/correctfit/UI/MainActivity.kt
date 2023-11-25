package com.example.correctfit.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.correctfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var pressedTime: Long = 0
    lateinit var binding: ActivityMainBinding
    var lastBackPressedTime = 0L
    val doublePressInterval = 2000
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val callback = object : OnBackPressedCallback(true) {
           override fun handleOnBackPressed() {
               val currentTime = System.currentTimeMillis()
               if(currentTime - lastBackPressedTime< doublePressInterval){
                   finish()
               }else {
                   Toast.makeText(this@MainActivity, "press back again to exit", Toast.LENGTH_SHORT).show()
                   lastBackPressedTime = currentTime
               }
            }
        }
      onBackPressedDispatcher.addCallback(this,callback)
    }

    companion object{
        lateinit var progress : ConstraintLayout
        @SuppressLint("StaticFieldLeak")
        var contexts: Context? = null

    }
}