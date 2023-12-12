package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newscleanarch.databinding.ArticelItemBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val items = AsyncListDiffer(this, differ)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ArticelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.currentList.size
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items.currentList.get(position))
        holder.setUpClickActions(article = items.currentList.get(position))
    }


    var navigateToArticle:((Article) -> Unit)?=null
    inner class SearchViewHolder(private val articleItemBinding: ArticelItemBinding) :
        RecyclerView.ViewHolder(articleItemBinding.root) {
        fun bind(article: Article) {
            articleItemBinding.articleTitle.text = article.title
            articleItemBinding.articleDescription.text = article.description
            Glide.with(articleItemBinding.articleImg).load(article.urlToImage)
                .into(articleItemBinding.articleImg)
        }
        fun setUpClickActions(article: Article){
            articleItemBinding.root.setOnClickListener {
                navigateToArticle?.invoke(article)
            }
        }
    }
}