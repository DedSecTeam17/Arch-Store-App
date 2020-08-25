package com.example.arch_store.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.adapters.HomeListener
import com.example.arch_store.adapters.ProductsAdapter
import com.example.arch_store.databinding.FragmentHomeBinding
import com.example.arch_store.databinding.FragmentSignInBinding
import com.example.arch_store.models.Category
import com.example.arch_store.models.Product
import com.example.arch_store.models.User
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.AuthViewModel
import com.example.arch_store.view_models.CategoryStateEvent
import com.example.arch_store.view_models.CategoryViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), HomeListener {
    private val viewModel: CategoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        viewModel.setStateEvent(CategoryStateEvent.GetCategories)
        subscribeObservers(binding)




        return binding.root
    }

    override fun onItemClicked(product: Product) {

        var action = HomeFragmentDirections.actionHomeDestToProductDetailFragment2(product.id)
        findNavController().navigate(action)
    }


    private fun setupTabs(cats: List<Category>, binding: FragmentHomeBinding) {
        val tabs = binding.cats
        var pager = binding.productsViewPager

        for (cat in cats) {
            tabs.addTab(binding.cats.newTab().setText(cat.name))

        }

        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.tabMode = TabLayout.MODE_SCROLLABLE


        val adapter = ProductsAdapter(
            requireActivity(), childFragmentManager,
            cats,
            homeListener = this
        )
        pager.adapter = adapter

        pager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                tabs
            )
        )
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }


    private fun subscribeObservers(binding: FragmentHomeBinding) {
        viewModel.categories.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Category>> -> {
                    binding.loading.visibility = View.GONE

                    println("we have data")

                    setupTabs(cats = dataState.data, binding = binding)
                }
                is DataState.Error -> {
                    binding.loading.visibility = View.GONE

                    println("Error to get cats.")


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }

}