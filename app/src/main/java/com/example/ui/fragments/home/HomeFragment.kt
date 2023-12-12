package com.example.ui.fragments.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.adapter.HomeAdapter
import com.example.base.BaseFragment
import com.example.data.model.Article
import com.example.data.model.NestedRecyclerModel
import com.example.data.model.Source
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentHomeBinding
import com.example.utilis.api.ConnectivtyObserver
import com.example.utilis.api.InternetConnectionObserver
import com.example.utilis.api.UiConstant.ARTICLES_ITEM
import com.example.utilis.api.UiConstant.SLIDER_ITEM
import com.example.utilis.api.UiConstant.SOURCE_ITEM
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val connectivtyObserver: ConnectivtyObserver by lazy {
        InternetConnectionObserver(
            requireContext()
        )
    }

    private var nestedRecyclerList = mutableListOf<NestedRecyclerModel>()
    private var topHeadLines: List<Article>? = null
    private var sources: List<Source>? = null

    private var homeAdapter: HomeAdapter? = null

    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)



        lifecycleScope.launch {
            connectivtyObserver.observe().collectLatest {
                when (it) {
                    ConnectivtyObserver.ConnectionState.Available -> {
                        hideProgress()
                        homeViewModel.getSources()
                        homeViewModel.getTopHeadlines()
                        setupObservers()
                    }

                    ConnectivtyObserver.ConnectionState.UnAvailable -> showProgress()
                    ConnectivtyObserver.ConnectionState.Lost -> showProgress()
                    ConnectivtyObserver.ConnectionState.Losing -> showProgress()
                }
            }
        }
    }

    private fun setupObservers() {
        handeSharedFlow(homeViewModel.topHeadLines, onSuccess = {
            when {
                it is List<*> -> {
                    val articles = it as List<Article>
                    topHeadLines = articles
                }
            }
        })
        handeSharedFlow(homeViewModel.sources, onSuccess = {
            when {
                it is List<*> -> {
                    sources = it as List<Source>
                    setupRecycler()
                }

            }
        })
    }

    private fun addNestedItems() {
        if (nestedRecyclerList.isEmpty()) {
            nestedRecyclerList.add(
                NestedRecyclerModel(
                    SLIDER_ITEM,
                    getString(R.string.top_head_lines),
                    topHeadLines,
                    null
                )
            )
            nestedRecyclerList.add(
                NestedRecyclerModel(
                    SOURCE_ITEM,
                    getString(R.string.sources),
                    null,
                    sources = sources
                )
            )
            nestedRecyclerList.add(
                NestedRecyclerModel(
                    ARTICLES_ITEM,
                    getString(R.string.everything),
                    null,
                    null
                )
            )

        }
    }

    private fun setupRecycler() {
        addNestedItems()
        homeAdapter = HomeAdapter(requireActivity())
        homeAdapter?.items?.submitList(nestedRecyclerList)
        binding.nestedRecyler.apply {
            adapter = homeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        homeAdapter?.onSourceClicked = {
            navToSource(it)
        }
    }

    @SuppressLint("ResourceType")
    private fun navToSource(sourceId: String) {
        findNavController().navigate(R.id.sourceFragment, bundleOf("sourceId" to sourceId))
    }


    private var sweetAlert: SweetAlertDialog? = null
    fun showProgress() {
        hideProgress()
        sweetAlert = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE).apply {
            progressHelper.barColor = Color.BLUE
            titleText = getString(com.example.bas.R.string.loading)
            setCancelable(true)
        }
        sweetAlert?.show()
    }

    fun hideProgress() {
        sweetAlert?.dismissWithAnimation()
        sweetAlert = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}