package com.example.ui.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adapter.ViewPagerAdapter
import com.example.base.BaseFragment
import com.example.newscleanarch.R
import com.example.newscleanarch.databinding.FragmentHomeBinding
import com.example.ui.fragments.TopHeadLines.TopHeadLinesFragment
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var fragments: List<Fragment>? = null
    private var titles: List<String>? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        addingFragments()
        setupViewPagerWithTabLayout()
    }

    private fun addingFragments() {
        fragments = listOf(TopHeadLinesFragment())
        titles = listOf(getString(R.string.top_head_lines))
    }

    private fun setupViewPagerWithTabLayout() {
        binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), fragments!!, titles!!)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, pos ->
            tab.text = titles?.get(0)
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}