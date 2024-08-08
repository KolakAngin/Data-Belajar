package com.dcfanbase.storyapp.view.app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dcfanbase.storyapp.data.api.ApiConfig
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.data.api.StoryResponse
import com.dcfanbase.storyapp.data.pref.UserModel
import com.dcfanbase.storyapp.data.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _listStory : MutableLiveData<List<ListStoryItem>?> = MutableLiveData()
    val listStory : LiveData<List<ListStoryItem>?> =  _listStory

    init {
        getAllStory()
    }

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun getAllStory(){
        viewModelScope.launch{
            val user = runBlocking { repository.getSession().first() }
            val token = "Bearer "+user.token
            try{
                val resp = ApiConfig.getServiceWithOutToken.getStoriesKu(token)
                if(resp.error != true){
                    Log.d("RESPONSE STORY","Saya tidak error Loh")
                    _listStory.postValue(resp.listStory)
                }
                Log.d("RESPONSE STORY",resp.message.toString())
            }catch (e : HttpException){
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, StoryResponse::class.java)
                Log.d("RESPONSE STORY Error",errorResponse.message.toString())
            }
        }


    }
    fun logout(){
        viewModelScope.launch {
            repository.logout()
            repository.getSession()
        }
    }
}