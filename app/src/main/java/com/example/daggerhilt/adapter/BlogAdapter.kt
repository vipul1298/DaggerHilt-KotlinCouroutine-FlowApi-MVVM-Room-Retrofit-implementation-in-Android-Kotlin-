package com.example.daggerhilt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.daggerhilt.R
import com.example.daggerhilt.models.Blog

class BlogAdapter(private val context:Context,private var blogList:List<Blog> ) : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    inner class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val img:ImageView = itemView.findViewById(R.id.image)
        val titleText:TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
          return BlogViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
          val blog = blogList[position]
          holder.titleText.text=blog.title;

        Glide.with(context).load(blog.image).into(holder.img)
    }

    override fun getItemCount(): Int = blogList.size

    fun setData(blogList:List<Blog>){
        this.blogList=blogList
        notifyDataSetChanged()
    }
}