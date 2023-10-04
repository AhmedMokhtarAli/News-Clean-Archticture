package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newscleanarch.databinding.SavedItemBinding

class SavedAdapter() : RecyclerView.Adapter<SavedAdapter.SavedViewHolder>() {

    private val differ = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val items = AsyncListDiffer(this, differ)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        return SavedViewHolder(
            SavedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.currentList.size
    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.bind(items.currentList.get(position))
    }


    var saveState: ((Article) -> Unit)? = null

    inner class SavedViewHolder(private val savedItemBinding: SavedItemBinding) :
        RecyclerView.ViewHolder(savedItemBinding.root) {
        fun bind(article: Article) {
            savedItemBinding.savedArticleTitle.text = article.title.toString()
            savedItemBinding.savedArticleDescription.text = article.description
            Glide.with(savedItemBinding.savedArticleImg).load(article.urlToImage)
                .into(savedItemBinding.savedArticleImg)

        }
    }
}