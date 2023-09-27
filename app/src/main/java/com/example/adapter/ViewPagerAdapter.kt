package com.example.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val fa: FragmentActivity,
    private val fragments: List<Fragment>,
    private val titles: List<String>
) : FragmentStateAdapter(fa) {
    /*    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }*/
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments.get(position)
    
}