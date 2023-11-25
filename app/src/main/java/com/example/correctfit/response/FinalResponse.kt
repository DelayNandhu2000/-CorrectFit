package com.example.correctfit.response

import androidx.annotation.Keep

@Keep
data class FinalResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)