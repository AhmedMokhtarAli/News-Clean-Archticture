package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.model.ArticleSource
import com.example.data.model.Source
import com.example.newscleanarch.databinding.SourceItemBinding

class SourceAdapter : RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {
    private val differ = object : DiffUtil.ItemCallback<Source>() {
        override fun areItemsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Source, newItem: Source): Boolean {
            return oldItem == newItem
        }
    }
    val items = AsyncListDiffer(this, differ)

    var onSourceClicked: ((String) -> Unit)? = null
    inner class SourceViewHolder(val sourceItemBinding: SourceItemBinding) :
        RecyclerView.ViewHolder(sourceItemBinding.root) {
        fun bind(source: Source) {
            sourceItemBinding.sourceTextLogo.text = source.name
        }
        fun handelSourceClicked(source: Source) {
            sourceItemBinding.root.setOnClickListener {
                onSourceClicked?.invoke(source.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SourceViewHolder(SourceItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int = items.currentList.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = items.currentList.get(position)
        holder.bind(source = source)
        holder.handelSourceClicked(source = source)
    }
}