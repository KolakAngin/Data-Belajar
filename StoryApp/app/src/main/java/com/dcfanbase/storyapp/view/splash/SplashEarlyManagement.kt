package com.dcfanbase.storyapp.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dcfanbase.storyapp.data.pref.UserModel
import com.dcfanbase.storyapp.data.repository.UserRepository

class SplashEarlyManagement(private val repository: UserRepository) : ViewModel(){

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}