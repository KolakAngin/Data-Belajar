package com.dcfanbase.storyapp.data.di

import android.content.Context
import com.dcfanbase.storyapp.data.api.ApiConfig
import com.dcfanbase.storyapp.data.pref.UserPreferences
import com.dcfanbase.storyapp.data.pref.dataStore
import com.dcfanbase.storyapp.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig(user.token).getService
        return UserRepository.getInstance(pref,apiService)
    }
}