package com.example.correctfit.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.RequestClass
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.model.UserData
import com.example.correctfit.response.FinalResponse
import com.example.correctfit.response.InsertResponse
import com.example.correctfit.response.RecyclerViewItem
import kotlinx.coroutines.launch

class AuthViewModel(private val repository:AuthRepository) :ViewModel(){
    private val _measureResponse : MutableLiveData<Resource<FinalResponse>> = MutableLiveData()
    val measureResponse:LiveData<Resource<FinalResponse>> get() = _measureResponse

    fun measureSize(header : Map<String,String>,value: RequestClass.MeasureRequestClass){
        _measureResponse.value = Resource.Loading
        viewModelScope.launch {
            _measureResponse.value = repository.getSizes(header,value)
        }
    }


    private val _brandResponse : MutableLiveData<Resource<RecyclerViewItem.BrandResponse>> = MutableLiveData()
    val brandResponse : LiveData<Resource<RecyclerViewItem.BrandResponse>>
        get() = _brandResponse


    fun getBrands() {
        _brandResponse.value = Resource.Loading

        viewModelScope.launch {
            _brandResponse.value = repository.getBrands()
        }
    }

    private val _sizeResponse : MutableLiveData<Resource<RecyclerViewItem.SizeResponse>> = MutableLiveData()
    val  sizeResponse : LiveData<Resource<RecyclerViewItem.SizeResponse>> get()= _sizeResponse

    fun getTotalSize(){
        _sizeResponse.value =Resource.Loading

        viewModelScope.launch {
            _sizeResponse.value =repository.getTotalSize()
        }
    }

    private val _addUserResponse :MutableLiveData<Resource<InsertResponse>> = MutableLiveData()
    val addUserResponse :LiveData<Resource<InsertResponse>> get() =_addUserResponse

       fun addUser(userData: UserData)=viewModelScope.launch{
           Log.e("userData", userData.toString())
        val map = HashMap<String,String>()
        map["Apikey"] = "Djh1ubi0sE9x3hLkqwVTfp1ru"
        _addUserResponse.value =Resource.Loading
        _addUserResponse.value=repository.adUser(map,userData)
    }

}