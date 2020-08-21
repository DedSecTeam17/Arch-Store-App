package com.example.arch_store.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.arch_store.fragments.ProductFragment


interface HomeListener {
    fun onItemClicked()
}

class ProductsAdapter(
    var context: Context,
    fm: FragmentManager,
    var totalTabs: Int,
    var homeListener: HomeListener
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return ProductFragment(
            homeListener = homeListener
        )
    }

    override fun getCount(): Int {
        return totalTabs
    }


}