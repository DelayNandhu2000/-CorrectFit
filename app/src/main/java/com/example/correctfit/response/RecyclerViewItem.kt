package com.example.correctfit.response

import androidx.annotation.Keep

sealed class RecyclerViewItem{
    data class TypeResponse(
        val Data: List<Data>,
        val Message: String,
        val Status: Boolean
    ) : RecyclerViewItem()
    @Keep
    data class Type(
        val description: String?=null,
        val image: Int?= null,
        val type: String,
        var default: Boolean = false,
    ):RecyclerViewItem()

     @Keep
    data class Data(
        val Title: String,
        val image: Int ?= null,
        val Type: ArrayList<Type>
    ):RecyclerViewItem()



    @Keep
    class BrandResponse(
        val `data`: Brand,
        val message: String,
        val status: Boolean
    ):RecyclerViewItem()

    @Keep
    class Brand(
        val brands: List<String>
    ):RecyclerViewItem()

    @Keep
    class SizeResponse(
        val `data`: SizeData,
        val message: String,
        val status: Boolean
    ):RecyclerViewItem()

    @Keep
    class SizeData(
        val size: List<String>
    ):RecyclerViewItem()

}
