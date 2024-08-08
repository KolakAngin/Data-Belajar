package com.dcfanbase.storyapp.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dcfanbase.storyapp.databinding.ActivityRegisterBinding
import com.dcfanbase.storyapp.view.ViewModelFactory
import com.dcfanbase.storyapp.view.login.LoginActivity
import com.dcfanbase.storyapp.view.login.checkRequireChar2
import com.dcfanbase.storyapp.view.showLoadingCostum

class RegisterActivity : AppCompatActivity() {
    private val registerViewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkRequireChar2(binding.emailEditText,binding.passwordEditText,binding.signupButton)
        playAnimation()


        binding.signupButton.setOnClickListener {
            setupRegister()
        }
    }


    private fun setupRegister(){
        val name  = binding.nameEditText.text.toString()
        val email  = binding.emailEditText.text.toString()
        val password  = binding.passwordEditText.text.toString()
        registerViewModel.getRegisterResponse(name, email, password)
        showLoadingCostum(this,this,registerViewModel.isLoading)
        registerViewModel.messageLogin.observe(this){
            if(it.equals("User created")){
                Toast.makeText(this, "berhasil membuat akun",Toast.LENGTH_SHORT).show()
                val myIntent = Intent(this,LoginActivity::class.java)
                startActivity(myIntent)
            }else{
                Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun playAnimation() {
        // make animation for banner image
        val titleTextView = ObjectAnimator.ofFloat(binding.titleTextView,View.ALPHA,1F).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA,1F).setDuration(500)
        val nameEditText = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA,1F).setDuration(500)

        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA,1F).setDuration(500)
        val emailEditText = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA,1F).setDuration(500)

        val passTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA,1F).setDuration(500)
        val passEditText = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA,1F).setDuration(500)

        val button = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA,1F).setDuration(500)

        val name = AnimatorSet().apply {
            playTogether(nameTextView,nameEditText)
        }
        val email = AnimatorSet().apply {
            playTogether(emailTextView,emailEditText)
        }
        val pass = AnimatorSet().apply {
            playTogether(passTextView,passEditText)
        }


        AnimatorSet().apply {
            playSequentially(titleTextView,name,email,pass,button)
            start()
        }
    }


}