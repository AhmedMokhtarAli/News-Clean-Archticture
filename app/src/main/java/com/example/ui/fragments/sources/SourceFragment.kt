package com.example.ui.fragments.sources

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapter.ArticleAdapter
import com.example.adapter.NewsAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentSourceBinding
import com.example.utilis.toast


class SourceFragment : BaseFragment(R.layout.fragment_source) {
    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter by lazy {
        NewsAdapter()
    }

    private val args by navArgs<SourceFragmentArgs>()

    private val sourcesViewModel by viewModels<SourcesViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSourceBinding.bind(view)

        binding.topAppBar.pagetTitle.text = args.sourceId
        sourcesViewModel.getSourceArticles(sourceId = args.sourceId)

        setupObservers()
        setupRecyclerView()
        handelArticleClicked()
        setupClickAction()
    }

    private fun setupObservers() {
        handeSharedFlow(sourcesViewModel.sourceArticles, onSuccess = {
            when (it) {
                is List<*> -> {
                    val articles = it as List<Article>
                    newsAdapter.items.submitList(articles)
                    toast(it.size.toString())
                }
            }
        }) {
            toast(it.toString())
        }
    }

    private fun setupRecyclerView() {
        binding.sourcesArticlesRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun handelArticleClicked() {
        newsAdapter.navigateToArticle = { article ->
            navigateToArticleFragment(article)
        }
    }

    private fun setupClickAction() {
        binding.topAppBar.backImg.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    @SuppressLint("ResourceType")
    private fun navigateToArticleFragment(article: Article) {
        findNavController().navigate(
            R.id.articleFragment,
            bundleOf("article" to article, "isOpenedFromSaved" to false)
        )
    }
}