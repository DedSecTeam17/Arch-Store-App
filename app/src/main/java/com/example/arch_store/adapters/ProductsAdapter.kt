package com.example.arch_store.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.arch_store.fragments.ProductFragment
import com.example.arch_store.models.Category
import com.example.arch_store.models.Product


interface HomeListener {
    fun onItemClicked(product: Product)
}

class ProductsAdapter(
    var context: Context,
    fm: FragmentManager,
    var cats: List<Category>,
    var homeListener: HomeListener
) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return ProductFragment(
            homeListener = homeListener,
            categoryId = cats.get(position).id

        )
    }

    override fun getCount(): Int {
        return cats.size
    }


}