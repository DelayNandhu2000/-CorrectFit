package com.example.correctfit.Retrofit

import androidx.annotation.Keep
import com.example.correctfit.model.UserData
import com.example.correctfit.response.FinalResponse
import com.example.correctfit.response.InsertResponse
import com.example.correctfit.response.RecyclerViewItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthInterface {

    @POST("zxt4wUwoVipSi4x.php")
    suspend fun getSize(@HeaderMap headers :Map<String, String>,@Body value : RequestClass.MeasureRequestClass): FinalResponse

    @Keep
    @GET("nhY3DZK37sid41U2.php")
    suspend fun brands(@Query("key") key: String?="xe2tHcXtjzzSSVvaRWsh"): RecyclerViewItem.BrandResponse

    @Keep
    @GET("nhY3DZK37sid41U2.php")
    suspend fun getTotalSize(@Query("key") key: String?="YhIHUbkRHwEA29C6") :RecyclerViewItem.SizeResponse

    @Keep
    @POST("TozAaCvn7QS3VgnS.php")
    suspend fun insert(@HeaderMap headers: Map<String, String>,@Body userData: UserData): InsertResponse




}