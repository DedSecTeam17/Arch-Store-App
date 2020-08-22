package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.HorizontalProductsListAdapter
import com.example.arch_store.adapters.HorizontalProductsListListener
import com.example.arch_store.databinding.FragmentEditProfileBinding
import com.example.arch_store.databinding.FragmentOrderDetailsBinding

class OrderDetailsFragment : Fragment(), HorizontalProductsListListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentOrderDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)


        var orderProductsAdapter: HorizontalProductsListAdapter = HorizontalProductsListAdapter(
            products = arrayListOf("", "", "", "", ""),
            ctx = activity!!.applicationContext,
            horizontalProductsListListener = this

        )
        binding.productsH.apply {
            adapter = orderProductsAdapter
            layoutManager =  LinearLayoutManager(
                activity!!.applicationContext, LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        return binding.root
    }

    override fun onItemClicked() {
       findNavController().navigate(R.id.action_orderDetailsFragment_to_productDetailFragment)
    }


}