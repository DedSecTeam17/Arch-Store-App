package com.example.arch_store.fragments

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.HomeListener
import com.example.arch_store.adapters.ProductListAdapter
import com.example.arch_store.adapters.ProductListListener
import com.example.arch_store.databinding.FragmentProductBinding

class ProductFragment(
 var   homeListener : HomeListener
) : Fragment(

), ProductListListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);
        var productList = binding.productList

        var productAdapter: ProductListAdapter = ProductListAdapter(
            ctx = activity!!.applicationContext,
            products = listOf<String>("", "", "", "", "", ""),
            productListListener = this
        )
        productList.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(activity, 2)

        }




        return binding.root
    }

    override fun onItemClicked() {
        homeListener.onItemClicked()
    }

}