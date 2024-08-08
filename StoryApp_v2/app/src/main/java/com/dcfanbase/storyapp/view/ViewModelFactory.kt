package com.dcfanbase.storyapp.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dcfanbase.storyapp.data.di.Injection
import com.dcfanbase.storyapp.data.repository.UserRepository
import com.dcfanbase.storyapp.view.app.MainViewModel
import com.dcfanbase.storyapp.view.login.LoginViewModel
import com.dcfanbase.storyapp.view.posting.PostViewModel
import com.dcfanbase.storyapp.view.register.RegisterViewModel
import com.dcfanbase.storyapp.view.splash.SplashEarlyManagement

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHEKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->{
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->{
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) ->{
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(PostViewModel::class.java) ->{
                PostViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SplashEarlyManagement::class.java) -> {
                SplashEarlyManagement(repository) as T
            }
            else -> throw IllegalArgumentException("Not found for live data")
        }

    }

    companion object{
        @Volatile
        var INSTANCE : ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context : Context) : ViewModelFactory{
            if(INSTANCE == null){
                synchronized(ViewModelFactory::class.java){
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context))
                }
            }

            return  INSTANCE as ViewModelFactory
        }
    }

}