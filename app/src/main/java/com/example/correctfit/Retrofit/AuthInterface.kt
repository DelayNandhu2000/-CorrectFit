package com.example.correctfit.Retrofit

import com.example.correctfit.response.FinalResponse
import com.example.correctfit.response.RecyclerViewItem
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface AuthInterface {

    @POST("zxt4wUwoVipSi4x.php")
    suspend fun getSize(@HeaderMap headers :Map<String, String>,@Body value : RequestClass.MeasureRequestClass): FinalResponse

}