package com.example.ui.fragments.saved

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adapter.SavedAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentSavedBinding
import com.example.utilis.printToLogD
import com.example.utilis.toast
import com.example.viewModels.ManageArticlesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : BaseFragment(R.layout.fragment_saved) {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val savedViewModel by viewModels<SavedViewModel>()
    private val manageArticlesViewModel by viewModels<ManageArticlesViewModel>()

    private var savedAdapter: SavedAdapter? = null

    private var articles: List<Article>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSavedBinding.bind(view)


        getSavedArticles()
        setupSavedRv()
        swipeToDelete()

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
                    savedViewModel.removeFromSaved(
                        article = savedAdapter!!.items.currentList.get(position)
                    )
//                    savedAdapter?.items?.submitList(articles)
//                    savedAdapter?.notifyItemRemoved(position)
                }

            }
        }).attachToRecyclerView(binding.savedRv)
    }
}