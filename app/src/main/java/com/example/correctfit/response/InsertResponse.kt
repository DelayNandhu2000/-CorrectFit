package com.example.correctfit.response

import androidx.annotation.Keep

@Keep
data class InsertResponse(
    val status: Boolean,
    val url: String
)
