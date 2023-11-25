package com.example.correctfit.Repository

import android.util.Log
import com.example.correctfit.Retrofit.AuthInterface
import com.example.correctfit.Retrofit.RequestClass
import com.example.correctfit.model.UserData

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
    suspend fun adUser(header:Map<String,String> ,userData: UserData)= safeApiCall{
        Log.e("userData", userData.toString())
        apiService.insert(header,userData)
    }

}