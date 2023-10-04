package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.ArticelItemBinding

class TopHeadLinesAdapter : RecyclerView.Adapter<TopHeadLinesAdapter.NewsViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.urlToImage == newItem.urlToImage
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val items = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder(ArticelItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = items.currentList.size

    var saveState: ((Article) -> Unit)? = null
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = items.currentList.get(position)
        holder.bind(article)
    }

    inner class NewsViewHolder(val articleItemBinding: ArticelItemBinding) :
        RecyclerView.ViewHolder(articleItemBinding.root) {
        fun bind(article: Article) {
            articleItemBinding.articleTitle.text = article.title
            articleItemBinding.articleDescription.text = article.description
            Glide.with(articleItemBinding.articleImg).load(article.urlToImage)
                .into(articleItemBinding.articleImg)
            articleItemBinding.savedImg.setOnClickListener {
//                article.isSaved = article.isSaved.not()
                articleItemBinding.savedImg.setImageResource(R.drawable.saved_item)
                saveState?.invoke(article)

            /* when {
                    !article.isSaved -> {
                        article.isSaved = article.isSaved.not()
                        articleItemBinding.savedImg.setImageResource(R.drawable.saved_item)
                        saveState?.invoke(article)
                    }

                    else -> {
                        article.isSaved = article.isSaved.not()
                        articleItemBinding.savedImg.setImageResource(R.drawable.un_saved_item)
                        saveState?.invoke(article)
                    }*/

                }
            }
        }
    }