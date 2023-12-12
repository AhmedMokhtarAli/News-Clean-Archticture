package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newscleanarch.databinding.TopheadlineItemBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.TopHeadlinesViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val items = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopHeadlinesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TopHeadlinesViewHolder(TopheadlineItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = items.currentList.size

    var navigateToArticle: ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: TopHeadlinesViewHolder, position: Int) {
        val article = items.currentList.get(position)
        holder.bind(article)
        holder.setUpClickActions(article)
    }

    inner class TopHeadlinesViewHolder(val topheadlinsItemBinding: TopheadlineItemBinding) :
        RecyclerView.ViewHolder(topheadlinsItemBinding.root) {
        fun bind(article: Article) {
            topheadlinsItemBinding.titleTopHeadlinesTv.text = article.title
            Glide.with(topheadlinsItemBinding.topheadlineImg).load(article.urlToImage)
                .into(topheadlinsItemBinding.topheadlineImg)
        }

        fun setUpClickActions(article: Article) {
            topheadlinsItemBinding.root.setOnClickListener {
                navigateToArticle?.invoke(article)
            }
        }
    }
}