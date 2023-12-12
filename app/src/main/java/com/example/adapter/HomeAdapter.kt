package com.example.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.data.model.Article
import com.example.data.model.NestedRecyclerModel
import com.example.data.model.Source
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.HomeSliderItemBinding
import com.example.newscleanarch.databinding.HomeSourcesItemBinding
import com.example.newscleanarch.databinding.HomeTabsItemBinding
import com.example.ui.fragments.TopHeadLines.TopHeadLinesFragment
import com.example.utilis.api.UiConstant.ARTICLES_ITEM
import com.example.utilis.api.UiConstant.SLIDER_ITEM
import com.example.utilis.api.UiConstant.SLIDER_SCROLL_TIME
import com.example.utilis.api.UiConstant.SOURCE_ITEM
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class HomeAdapter(val fragmentActivity: FragmentActivity) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<NestedRecyclerModel>() {
        override fun areItemsTheSame(
            oldItem: NestedRecyclerModel,
            newItem: NestedRecyclerModel
        ): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(
            oldItem: NestedRecyclerModel,
            newItem: NestedRecyclerModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val items = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            SLIDER_ITEM -> SliderViewHolder(
                HomeSliderItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            SOURCE_ITEM -> SourceViewHolder(
                HomeSourcesItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )

            else -> ArticlesViewHolder(
                HomeTabsItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SliderViewHolder -> {
                holder.bind(items.currentList.get(position))
            }

            is SourceViewHolder -> {
                items.currentList.get(position).sources?.let { holder.bind(it) }
                holder.handelSourceClicked()
            }

            is ArticlesViewHolder -> holder.bind()
        }
    }

    override fun getItemCount(): Int = items.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (items.currentList.get(position).key) {
            SLIDER_ITEM -> SLIDER_ITEM
            SOURCE_ITEM -> SOURCE_ITEM
            ARTICLES_ITEM -> ARTICLES_ITEM
            else -> super.getItemViewType(position)
        }
    }

    var navigateToArticle: ((Article) -> Unit)? = null

    inner class SliderViewHolder(val homeSliderItemBinding:HomeSliderItemBinding) :
        RecyclerView.ViewHolder(homeSliderItemBinding.root) {
        private var topHeadLinesAdapter: ArticleAdapter? = null
        fun bind(sliderItem: NestedRecyclerModel) {
            homeSliderItemBinding.sliderTitleTv.text = sliderItem.title
            topHeadLinesAdapter = ArticleAdapter()
            topHeadLinesAdapter?.items?.submitList(sliderItem.Articles)
            homeSliderItemBinding.topheadlineSlider.apply {
                adapter = topHeadLinesAdapter
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
            }
            GlobalScope.launch { sliderItem.Articles?.let { autoScroll(it.size) } }
            TabLayoutMediator(
                homeSliderItemBinding.sliderIndicator,
                homeSliderItemBinding.topheadlineSlider
            ) { indicator, slider -> }.attach()
        }

        private suspend fun autoScroll(size: Int) {
            while (true) {
                delay(SLIDER_SCROLL_TIME)
                withContext(Dispatchers.Main) {
                    if (homeSliderItemBinding.topheadlineSlider.currentItem + 1 < size) homeSliderItemBinding.topheadlineSlider.currentItem += 1
                    else homeSliderItemBinding.topheadlineSlider.currentItem = 0
                }
            }
        }

        fun setUpClickActions(article: Article) {
            homeSliderItemBinding.root.setOnClickListener {
                navigateToArticle?.invoke(article)
            }
        }
    }

    var onSourceClicked: ((String) -> Unit)? = null

    inner class SourceViewHolder(val homeSourcesItemBinding: HomeSourcesItemBinding) :
        RecyclerView.ViewHolder(homeSourcesItemBinding.root) {
        private var sourceAdapter: SourceAdapter? = null
        fun bind(sourcesList: List<Source>) {
            sourceAdapter = SourceAdapter()
            sourceAdapter?.items?.submitList(sourcesList)
            homeSourcesItemBinding.sourceRecycler.apply {
                adapter = sourceAdapter
                layoutManager = LinearLayoutManager(
                    fragmentActivity.applicationContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }

        }

        fun handelSourceClicked() {
            sourceAdapter?.onSourceClicked = {
                onSourceClicked?.invoke(it)
            }
        }
    }

    inner class ArticlesViewHolder(val homeTabsItemBinding: HomeTabsItemBinding) :
        RecyclerView.ViewHolder(homeTabsItemBinding.root) {
        private var fragments: List<Fragment>? = null
        private var titles: List<String>? = null
        fun bind() {
            addingFragments()
            setupViewPagerWithTabLayout()
        }

        private fun addingFragments() {
            fragments = listOf(TopHeadLinesFragment())
            titles = listOf(getString(fragmentActivity.applicationContext, R.string.all))
        }

        private fun setupViewPagerWithTabLayout() {
            homeTabsItemBinding.viewPager.adapter =
                ViewPagerAdapter(fragmentActivity, fragments!!, titles!!)
            TabLayoutMediator(
                homeTabsItemBinding.tabLayout,
                homeTabsItemBinding.viewPager
            ) { tab, pos ->
                tab.text = titles?.get(0)
            }.attach()
        }
    }
}