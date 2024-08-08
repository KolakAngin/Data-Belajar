package com.dcfanbase.storyapp.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dcfanbase.storyapp.databinding.ActivitySplashBinding
import com.dcfanbase.storyapp.view.ViewModelFactory
import com.dcfanbase.storyapp.view.app.MainActivityGo
import com.dcfanbase.storyapp.view.isAppOnline
import com.dcfanbase.storyapp.view.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private val splashEarlyManagement : SplashEarlyManagement by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(isAppOnline(this)){
            Handler(Looper.getMainLooper()).postDelayed({
                splashEarlyManagement.getSession().observe(this){
                    if(it.isLogin){
                        val myIntent = Intent(this,MainActivityGo::class.java)
                        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(myIntent)
                    }else{
                        val myIntent = Intent(this,LoginActivity::class.java)
                        myIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(myIntent)
                    }
                }
                finish()
            },1000L)
        }
    }
}