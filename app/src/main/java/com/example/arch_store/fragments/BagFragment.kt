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
import com.example.arch_store.adapters.CartListAdapter
import com.example.arch_store.adapters.CartListListener
import com.example.arch_store.databinding.FragmentBagBinding

class BagFragment : Fragment(), CartListListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var binding: FragmentBagBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bag, container, false)

        val cartList = binding.cartItems
        var cartAdapter = CartListAdapter(
            products = arrayListOf<String>("", "", "", "", "", ""),
            ctx = activity!!.applicationContext,
            cartListListener = this
        )
        cartList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        return binding.root
    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.action_cart_dest_to_productDetailFragment);
    }


}