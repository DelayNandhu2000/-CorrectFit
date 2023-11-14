package com.example.correctfit.response

sealed class RecyclerViewItem{
    data class TypeResponse(
        val Data: List<Data>,
        val Message: String,
        val Status: Boolean
    ) : RecyclerViewItem()

    data class Type(
        val description: String?=null,
        val image: Int?= null,
        val type: String,
        var default: Boolean = false,
    ):RecyclerViewItem()


    data class Data(
        val Title: String,
        val image: Int ?= null,
        val Type: ArrayList<Type>
    ):RecyclerViewItem()
}
