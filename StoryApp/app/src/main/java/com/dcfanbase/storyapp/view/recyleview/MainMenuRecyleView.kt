package com.dcfanbase.storyapp.view.recyleview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dcfanbase.storyapp.data.api.ListStoryItem
import com.dcfanbase.storyapp.databinding.RecyleViewLayoutBinding
import com.dcfanbase.storyapp.view.detail.DetailActivity

class MainMenuRecyleView :
    PagingDataAdapter<ListStoryItem, MainMenuRecyleView.Holder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = RecyleViewLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)
        val name = data?.name
        val desc = data?.description
        val urlImg = data?.photoUrl
        holder.bind(name as String,desc as String, urlImg as String)

        val trans = ActivityOptionsCompat.makeSceneTransitionAnimation(
            holder.itemView.context as Activity
        )

        holder.binding.holderData.setOnClickListener {
            val  intent = Intent(it.context,DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER,data)
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}