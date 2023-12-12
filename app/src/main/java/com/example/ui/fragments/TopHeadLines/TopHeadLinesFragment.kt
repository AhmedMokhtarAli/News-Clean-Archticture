package com.example.ui.fragments.TopHeadLines

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.NewsAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentTopHeadlinesBinding
import com.example.utilis.toast
import dagger.hilt.android.AndroidEntryPoint

const val TAG: String = "News State"

@AndroidEntryPoint
class TopHeadLinesFragment :
    BaseFragment(R.layout.fragment_top_headlines) {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    private val topHeadLinesViewModel by viewModels<TopHeadLinesViewModel>()

    private var topHeadLinesAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTopHeadlinesBinding.bind(view)

        topHeadLinesAdapter = NewsAdapter()


        topHeadLinesViewModel.getNews("us", 1)

//        binding.searchEt.setOnClickListener { findNavController() }

        setupObserver()
        setupRecyclerView()
    }


    private fun setupObserver() {
        handeSharedFlow(topHeadLinesViewModel.newsFromApi, onSuccess = {
            when {
                it is List<*> -> {
                    val articles = it as List<Article>
                    topHeadLinesAdapter?.items?.submitList(articles)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        handelArticleClickcked()
        binding.topHeadLlinesRecyclerView.apply {
            adapter = topHeadLinesAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handelArticleClickcked() {
        topHeadLinesAdapter?.saveState = { article ->
            savedArticle(article)
        }
        topHeadLinesAdapter?.navigateToArticle = { article ->
            navigateToArticleFragment(article)
        }
    }

    private fun savedArticle(article: Article) {
        topHeadLinesViewModel.saveToLocal(article)
    }

    @SuppressLint("ResourceType")
    private fun navigateToArticleFragment(article: Article) {
        findNavController().navigate(R.id.articleFragment, bundleOf("article" to article,"isOpenedFromSaved" to false))
    }
}