package com.dcfanbase.storyapp.data.repository

import com.dcfanbase.storyapp.data.api.ApiService
import com.dcfanbase.storyapp.data.pref.UserModel
import com.dcfanbase.storyapp.data.pref.UserPreferences
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreferences: UserPreferences,
    private val apiService: ApiService
){
    suspend fun saveSession(userModel: UserModel){
        userPreferences.saveSession(userModel)
    }

     fun getSession() :Flow<UserModel>{
        return userPreferences.getSession()
    }
    suspend fun logout(){
        userPreferences.logout()
    }

    fun getApiService() = apiService


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreferences,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}