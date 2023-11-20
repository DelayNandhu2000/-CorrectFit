package com.example.correctfit.Repository

import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.RequestClass
import retrofit2.http.Header

class AuthRepository(private  val apiService: AuthInterface):SafeApiCall{
    suspend fun getSizes(header: Map<String,String>,value : RequestClass.MeasureRequestClass)=safeApiCall {
        apiService.getSize(header,value)
    }

    suspend fun getBrands() = safeApiCall {
        apiService.brands()
    }

    suspend fun getTotalSize()=safeApiCall {
        apiService.getTotalSize()
    }
//
//    suspend fun addUser(header:Map<String,String> ,braClass: BraClass)= safeApiCall{
//        //apiService.insert(header,braClass)
//    }
}