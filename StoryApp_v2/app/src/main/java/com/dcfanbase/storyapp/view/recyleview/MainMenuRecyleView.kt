package com.dcfanbase.storyapp.view.recyleview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.databinding.RecyleViewLayoutBinding
import com.dcfanbase.storyapp.view.detail.DetailActivity

class MainMenuRecyleView(private val listData : List<ListStoryItem>) : RecyclerView.Adapter<MainMenuRecyleView.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyleViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int  = listData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val name = listData[position].name
        val desc = listData[position].description
        val urlImg = listData[position].photoUrl
        holder.bind(name as String,desc as String, urlImg as String)

        val trans = ActivityOptionsCompat.makeSceneTransitionAnimation(
            holder.itemView.context as Activity
        )

        holder.binding.holderData.setOnClickListener {
            val  intent = Intent(it.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,listData[position])
            holder.binding.root.context.startActivity(intent,trans.toBundle())
        }

    }

    class Holder(val binding : RecyleViewLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(name : String, desc : String, urlImage : String){
            binding.itemName.text = name
            binding.itemDescription.text = desc
            Glide.with(binding.root).load(urlImage).into(binding.itemImg)
        }
    }
}