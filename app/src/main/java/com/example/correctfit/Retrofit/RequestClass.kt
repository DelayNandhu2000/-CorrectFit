package com.example.correctfit.Retrofit

sealed class RequestClass {
    class MeasureRequestClass(var band:String, var bust:String , var hip: String) :RequestClass()
}
