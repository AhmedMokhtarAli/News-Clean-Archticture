package com.example.ui.fragments.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.NewsAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.data.model.NewsResponse
import com.example.newscleanarch.databinding.FragmentNewsBinding
import com.example.utilis.printToLogD
import com.example.utilis.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG: String = "News State"

@AndroidEntryPoint
class NewsFragment :
    BaseFragment<FragmentNewsBinding>(FragmentNewsBinding::inflate) {

    private val newsViewModel by viewModels<NewsViewModel>()

    private var newsAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsAdapter = NewsAdapter()


        newsViewModel.getNews("us", 1)


        setupObserver()
        setupRecyclerView()

    }


    private fun setupObserver() {
        handeSharedFlow(newsViewModel.newsFromApi, onSuccess = {
            when  {
                it is List<*> -> {
                    val articles = it as List<Article>
                    newsAdapter?.items?.submitList(articles)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}