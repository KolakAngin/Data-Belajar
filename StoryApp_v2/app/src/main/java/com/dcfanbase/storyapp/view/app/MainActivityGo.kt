package com.dcfanbase.storyapp.view.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dcfanbase.storyapp.R
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.databinding.ActivityMainGoBinding
import com.dcfanbase.storyapp.view.ViewModelFactory
import com.dcfanbase.storyapp.view.login.LoginActivity
import com.dcfanbase.storyapp.view.posting.PostActivity
import com.dcfanbase.storyapp.view.recyleview.MainMenuRecyleView

class MainActivityGo : AppCompatActivity() {
    private lateinit var binding : ActivityMainGoBinding
    private val mainViewModel : MainViewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainGoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rViewHolder.layoutManager = LinearLayoutManager(this)
        mainViewModel.listStory.observe(this){
            setRecyleView(it)
        }
        mainViewModel.getSession().observe(this){
            binding.toolbar.title = resources.getString(R.string.wellcome,it.name)
        }

        mainViewModel.getSession().observe(this){
            Log.d("OUTSIDE EMAIL",it.email)
            Log.d("OUTSIDE TOKEN",it.token)
        }


        binding.btnAddStory.setOnClickListener {
            val trans = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this
            )
            startActivity(Intent(this, PostActivity::class.java),trans.toBundle())
        }

        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout ->{
                    mainViewModel.logout()
                    val myIntent = Intent(this, LoginActivity::class.java)
                    startActivity(myIntent)
                    finish()
                    false
                }else -> {
                Toast.makeText(this,"Tidak tersedia", Toast.LENGTH_SHORT).show()
                false
            }

            }
        }
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.getAllStory()
    }
    private fun setRecyleView(list: List<ListStoryItem>?) {
        binding.rViewHolder.adapter = MainMenuRecyleView(list as List<ListStoryItem>)
    }
}