package com.example.ui.fragments.TopHeadLines

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private val newsViewModel by viewModels<TopHeadLinesViewModel>()

    private var newsAdapter: NewsAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTopHeadlinesBinding.bind(view)

        newsAdapter = NewsAdapter()


        newsViewModel.getNews("us", 1)

//        binding.searchEt.setOnClickListener { findNavController() }

        setupObserver()
        setupRecyclerView()
//        collapseSearch()
    }


    private fun setupObserver() {
        handeSharedFlow(newsViewModel.newsFromApi, onSuccess = {
            when {
                it is List<*> -> {
                    val articles = it as List<Article>
                    newsAdapter?.items?.submitList(articles)
                }
            }
        })
    }

    private fun setupRecyclerView() {
        newsAdapter?.saveState = {
            if (it) {
                toast(it.toString())
            } else {
                toast(it.toString())
            }
        }
        binding.topHeadLlinesRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
    /*    private fun collapseSearch(){
            binding.topHeadLlinesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy>0){
                        binding.searchEt.animate().translationY(-1*(binding.searchEt.y+16)).setDuration(200)
                        binding.topHeadLlinesRecyclerView.animate().translationY(-1*(binding.searchEt.y+16)).setDuration(200)
                        binding.searchEt.animate().alpha(0f).setDuration(200)

                    } else if(dy<=0){
                        binding.topHeadLlinesRecyclerView.animate().translationY(0f).setDuration(200)
                        binding.searchEt.animate().translationY(0f).setDuration(200)
                        binding.searchEt.animate().alpha(1f).setDuration(200)
                    }
                }
            })
        }*/
}