package com.example.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.SearchAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentSearchBinding
import com.example.utilis.api.UiConstant.SEARCH_DELAY_TIME
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment(R.layout.fragment_search){

    private var _binding:FragmentSearchBinding?=null
    private val binding get()=_binding!!

    private val searchViewModel by viewModels<SearchViewModel>()
    private var searchAdapter:SearchAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentSearchBinding.bind(view)

        setupSearchRv()
        setupObservers()
        search()
    }

    private fun search() {
        var job: Job? = null
        binding.searchEditText.addTextChangedListener { editable->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_DELAY_TIME)
                editable?.let {
                    if (editable.toString().isNotBlank()) {
                        searchViewModel.searchNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun setupSearchRv(){
        searchAdapter = SearchAdapter()
        binding.searchRv.apply {
            adapter=searchAdapter
            layoutManager=LinearLayoutManager(context)
        }
    }
    private fun setupObservers(){
        handeSharedFlow(searchViewModel.searchResult, onSuccess = {
            when {
                it is List<*> -> {
                  val articles = it as List<Article>
                  searchAdapter?.items?.submitList(articles)
                }
            }
        })
    }
}