package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.Article
import com.example.newscleanarch.databinding.NewItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val differCallback= object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val items=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return NewsViewHolder(NewItemBinding.inflate(layoutInflater,parent,false))
    }

    override fun getItemCount(): Int = items.currentList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article=items.currentList.get(position)
        holder.bind(article)
    }
    inner class NewsViewHolder(val newItemBinding: NewItemBinding):
        RecyclerView.ViewHolder(newItemBinding.root) {
        fun bind(article: Article){
            newItemBinding.article=article
        }
    }
}