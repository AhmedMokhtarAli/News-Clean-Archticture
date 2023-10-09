package com.example.ui.fragments.saved

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.SavedAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentSavedBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : BaseFragment(R.layout.fragment_saved) {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val savedViewModel by viewModels<SavedViewModel>()

    private var savedAdapter: SavedAdapter? = null

    private var articles: List<Article>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedBinding.bind(view)


        getSavedArticles()
        setupSavedRv()
        swipeToDelete()

        setupClickActions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSavedRv() {
        savedAdapter = SavedAdapter()
        savedAdapter!!.items.submitList(articles)
        binding.savedRv.apply {
            adapter = savedAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupClickActions() {
        savedAdapter?.navigateToArticle = { article -> navigateToArticleFragment(article) }
    }

    private fun getSavedArticles() {
        savedViewModel.getSavedArticles()
        lifecycleScope.launch {
            savedViewModel.savedNews.collectLatest {
                articles = it
                savedAdapter?.items?.submitList(it)
            }
        }
    }

    private fun swipeToDelete() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position >= 0 && position < savedAdapter!!.items.currentList.size) {
                    val article = savedAdapter!!.items.currentList.get(position)
                    savedViewModel.removeFromSaved(
                        article = article
                    )
                    Snackbar.make(
                        binding.root,
                        "article successfully deleted",
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction("Undo") {
                            savedViewModel.restoreArticle(
                                article =article
                            )
                        }.show()
                    }
                }

            }
        }).attachToRecyclerView(binding.savedRv)
    }

    @SuppressLint("ResourceType")
    private fun navigateToArticleFragment(article: Article) {
        findNavController().navigate(R.id.articleFragment, bundleOf("article" to article,"isOpenedFromSaved" to true))
    }
}