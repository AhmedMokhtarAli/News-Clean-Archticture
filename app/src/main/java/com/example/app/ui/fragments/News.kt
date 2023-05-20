package com.example.app.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.app.adapter.NewsAdapter
import com.example.app.viewmodel.NewsViewModel
import com.example.domain.utilis.NewsResource
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentNewsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG:String="News State"
class News : Fragment() {

    private val newsViewModel : NewsViewModel by viewModels()
    lateinit var binding:FragmentNewsBinding

    private lateinit var newsAdapter:NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentNewsBinding.inflate(layoutInflater)

        newsAdapter=NewsAdapter()
        lifecycleScope.launch {
            newsViewModel.news.collect{newsResource ->
                when(newsResource) {
                    is NewsResource.Success -> {
                        newsResource.data?.let { newsResponse ->
                        newsAdapter.newsDiffer.submitList(newsResponse.articles.toList())
                    }
                    }
                    is NewsResource.Erorr-> {
                        newsResource.message?.let {message ->
                            Toast.makeText(activity,"$message",Toast.LENGTH_SHORT).show()
                        }
                    }
                    is NewsResource.Loadding -> {
                        Log.d(TAG,"loading")
                    }

                    else -> {
                        Log.d(TAG, "can`t access data")}
                }
            }
        }

        setupRecyclerView()

        return binding.root
    }

    fun setupRecyclerView(){
        binding.newsRecyclerView.apply {
            adapter= newsAdapter
            layoutManager=LinearLayoutManager(context)
        }
    }

}