package com.dcfanbase.storyapp.view.detail

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private  var dataDetail : ListStoryItem? = null
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataDetail = if(Build.VERSION.SDK_INT >= 33){
            intent.getParcelableExtra(EXTRA_USER,ListStoryItem::class.java)
        }else{
            intent.getParcelableExtra(EXTRA_USER)
        }



        Glide.with(this).load(dataDetail?.photoUrl).into(binding.imgDetail)
        binding.detailName.text = dataDetail?.name
        binding.detailDescribe.text = dataDetail?.description
    }
    companion object{
        const val EXTRA_USER = "EXTRA_USER"
    }
}