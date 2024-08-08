package com.dcfanbase.storyapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dcfanbase.storyapp.data.api.ApiService
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.data.paging.StoryPagingResource
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

    fun getEnablePostLocation() : LiveData<Boolean> {
        return  userPreferences.getEnablePostLocation().asLiveData()
    }

    suspend fun saveEnablePostLocation(isEnable : Boolean){
        userPreferences.saveEnablePostLocation(isEnable)
    }
    fun getStoryWithPagging(): LiveData<PagingData<ListStoryItem>> {
        Log.d("LOGGING STORY","LOGGING STORY WITH PAGGING DI USER REPOSITORY")
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingResource(apiService)
            }
        ).liveData
    }

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