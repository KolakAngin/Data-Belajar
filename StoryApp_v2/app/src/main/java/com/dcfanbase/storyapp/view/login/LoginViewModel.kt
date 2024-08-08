package com.dcfanbase.storyapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dcfanbase.storyapp.data.api.ApiConfig
import com.dcfanbase.storyapp.data.api.LoginResponse
import com.dcfanbase.storyapp.data.pref.UserModel
import com.dcfanbase.storyapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel(){

    private val _asUser : MutableLiveData<Boolean> = MutableLiveData()
    val asUser : LiveData<Boolean> = _asUser

    private val _messageLogin : MutableLiveData<String> = MutableLiveData()
    val messageLogin : LiveData<String>? = _messageLogin


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getSession() : LiveData<UserModel>{
        _asUser.value = false
        return repository.getSession().asLiveData()
    }
    fun getLoginResponse(email : String, password : String){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val client = ApiConfig.getServiceWithOutToken.login(email, password)
                val response = client.loginResult
                _isLoading.postValue(false)
                Log.d("REQUEST LOGIN","$response")
                val userModel = UserModel(response?.name as String,email, response?.token as String,true)
                Log.d("REQUEST LOGIN MODEL","$userModel")
                _asUser.postValue(true)
                repository.logout()
                repository.saveSession(userModel)
                _asUser.postValue(true)
                _messageLogin.postValue(client.message as String)
                Log.d("REQUEST LOGIN Message","${messageLogin?.value}")

            }catch (e : HttpException){
                _isLoading.postValue(false)
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)
                _asUser.postValue(false)
                _messageLogin.postValue(errorResponse.message.toString())
                Log.d("REQUEST Error",errorResponse.message.toString())
            }
        }
    }
}