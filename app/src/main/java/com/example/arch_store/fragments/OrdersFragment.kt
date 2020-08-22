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
import com.example.arch_store.adapters.OrdersListAdapter
import com.example.arch_store.adapters.OrdersListListener
import com.example.arch_store.databinding.FragmentOrdersBinding
import com.example.arch_store.databinding.FragmentProfileBinding


class OrdersFragment : Fragment(), OrdersListListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOrdersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        var ordersAdapter: OrdersListAdapter = OrdersListAdapter(
            orders = arrayListOf("", "", "", "", ""),
            ctx = activity!!.applicationContext,
            ordersListListener = this
        )

        binding.orders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        return binding.root
    }

    override fun onItemClicked() {
        findNavController().navigate(R.id.action_ordersFragment_to_orderDetailsFragment)
    }

}