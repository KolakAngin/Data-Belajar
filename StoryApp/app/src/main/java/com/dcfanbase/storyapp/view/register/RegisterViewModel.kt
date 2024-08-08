package com.dcfanbase.storyapp.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dcfanbase.storyapp.data.api.ApiConfig
import com.dcfanbase.storyapp.data.api.RegisterResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Boolean?>()
    val error : LiveData<Boolean> = _isLoading

    private val _messageLogin : MutableLiveData<String> = MutableLiveData()
    val messageLogin : LiveData<String>  = _messageLogin

    fun getRegisterResponse(name : String, email : String, password : String){
        _isLoading.value = true
        Log.d("LOADING","${isLoading.value}")
        viewModelScope.launch {
            try{

                val client = ApiConfig.getServiceWithOutToken
                val resp = client.register(name, email, password)
                Log.d("REQUEST",resp.message.toString())
                Log.d("LOADING","${isLoading.value}")
                _messageLogin.postValue(resp.message.toString())
                _error.postValue(resp.error)
                _isLoading.postValue(false)
            }catch (e : HttpException){

                Log.d("LOADING","${isLoading.value}")
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                Log.d("REQUEST Error",errorResponse.message.toString())
                _messageLogin.postValue(errorResponse.message.toString())
                _isLoading.postValue(false)
            }
        }

    }

}