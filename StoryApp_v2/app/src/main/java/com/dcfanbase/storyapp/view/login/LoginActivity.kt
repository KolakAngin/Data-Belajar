package com.dcfanbase.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dcfanbase.storyapp.databinding.ActivityLoginBinding
import com.dcfanbase.storyapp.view.ViewModelFactory
import com.dcfanbase.storyapp.view.app.MainActivityGo
import com.dcfanbase.storyapp.view.register.RegisterActivity
import com.dcfanbase.storyapp.view.showLoadingCostum

class LoginActivity : AppCompatActivity() {

    private val loginViewModel : LoginViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        playAnimation()
        loginViewModel.messageLogin?.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }

        checkRequireChar2(binding.emailEditText,binding.passwordEditText,binding.loginButton)
        binding.loginButton.setOnClickListener {
            settingLogin()
            loginViewModel.messageLogin?.observe(this){
                intent.putExtra(BAD_HTTP,it)
            }
            loginViewModel.getSession().observe(this){
                if(it.isLogin){
                    Toast.makeText(this,"GO to Main Activity",Toast.LENGTH_SHORT).show()
                    val myIntent  = Intent(this,MainActivityGo::class.java)
                    startActivity(myIntent)
                    finish()
                }
            }


        }
        binding.register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }
    }
    private fun settingLogin(){
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        loginViewModel.getLoginResponse(email,password)
        showLoadingCostum(this,this,loginViewModel.isLoading)
    }

    companion object{
        const val BAD_HTTP = "BAD_HTTP"
    }
    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA,1F).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA,1F).setDuration(500)

        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,1F).setDuration(500)
        val emailEditText = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA,1F).setDuration(500)

        val passTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,1F).setDuration(500)
        val passEditText = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA,1F).setDuration(500)


        val register = ObjectAnimator.ofFloat(binding.register,View.ALPHA,1F).setDuration(500)


        val button = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA,1F).setDuration(500)

        val email = AnimatorSet().apply {
            play(emailTextView).with(emailEditText)
        }

        val pass = AnimatorSet().apply {
            playTogether(passEditText,passTextView)
        }

        AnimatorSet().apply {
            playSequentially(title,message,email,pass,button,register)
            start()
        }


    }


}