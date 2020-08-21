package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.ProductListAdapter
import com.example.arch_store.adapters.SearchListAdapter
import com.example.arch_store.adapters.SearchListListener
import com.example.arch_store.databinding.FragmentProductBinding
import com.example.arch_store.databinding.FragmentSearchBinding

class SearchFragment : Fragment(), SearchListListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        var productList = binding.searchResult

        var productAdapter: SearchListAdapter = SearchListAdapter(
            ctx = activity!!.applicationContext,
            products = listOf<String>("", "", "", "", "", ""),
            searchListListener = this
        )
        productList.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(activity)

        }




        return binding.root
    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.action_search_dest_to_productDetailFragment)
    }

}