package com.example.correctfit.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correctfit.Repository.AuthRepository
import com.example.correctfit.Retrofit.RequestClass
import com.example.correctfit.Retrofit.Resource
import com.example.correctfit.response.FinalResponse
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

}