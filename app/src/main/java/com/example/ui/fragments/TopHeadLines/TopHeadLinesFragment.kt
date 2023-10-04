package com.example.ui.fragments.TopHeadLines

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.TopHeadLinesAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentTopHeadlinesBinding
import dagger.hilt.android.AndroidEntryPoint

const val TAG: String = "News State"

@AndroidEntryPoint
class TopHeadLinesFragment :
    BaseFragment(R.layout.fragment_top_headlines) {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    private val topHeadLinesViewModel by viewModels<TopHeadLinesViewModel>()

    private var topHeadLinesAdapter: TopHeadLinesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTopHeadlinesBinding.bind(view)

        topHeadLinesAdapter = TopHeadLinesAdapter()


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
            /*
            if (article.isSaved) {
                savedArticle(article)
//                toast(article!!.isSaved.toString())
            } else {
                removeArticle(article)
//                toast(article!!.isSaved.toString())
            }*/
        }
    }

    private fun savedArticle(article: Article) {
        topHeadLinesViewModel.saveToLocal(article)
    }

}