package com.example.ui.fragments.Article

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentArticleBinding
import com.example.utilis.goneIf
import com.example.utilis.toast
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : BaseFragment(R.layout.fragment_article) {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ArticleFragmentArgs>()
    private val articleViewModel by viewModels<ArticleViewModel>()
    private var article: Article? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentArticleBinding.bind(view)

        article = args.article
        binding.articleWebView.webViewClient = WebViewClient()
        article?.url?.let { articleUrl ->
            binding.articleWebView.loadUrl(articleUrl)
        } ?: toast("no url fonded")

        saveArticle()
        saveButtonVisiblity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveArticle() {
        binding.saveArticleBtn.setOnClickListener {
            article?.let { article ->
                articleViewModel.saveArticle(article = article)
                changeFabBtnAfterClicked()
                Snackbar.make(binding.root, "article saved successfully", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun changeFabBtnAfterClicked() {
        binding.saveArticleBtn.setImageResource(R.drawable.done)
        binding.saveArticleBtn.isClickable = false
        binding.saveArticleBtn.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.light_green))
    }

    private fun saveButtonVisiblity() {
        binding.saveArticleBtn.goneIf(args.isOpenedFromSaved)
    }
}