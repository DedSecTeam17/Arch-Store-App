package com.example.arch_store.fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.arch_store.R
import com.example.arch_store.adapters.HomeListener
import com.example.arch_store.adapters.ProductsAdapter
import com.example.arch_store.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment(), HomeListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        var binding: FragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        val tabs = binding.cats
        var pager = binding.productsViewPager

        tabs.addTab(binding.cats.newTab().setText("Kids"))
        tabs.addTab(binding.cats.newTab().setText("Women"))
        tabs.addTab(binding.cats.newTab().setText("Sports"))
        tabs.addTab(binding.cats.newTab().setText("Running"))

        tabs.tabGravity = TabLayout.GRAVITY_FILL


        val adapter = ProductsAdapter(
            activity!!.applicationContext, childFragmentManager,
            tabs.tabCount,
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



        return binding.root
    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.action_home_dest_to_productDetailFragment2)
    }


}