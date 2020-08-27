package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.OrdersListAdapter
import com.example.arch_store.adapters.OrdersListListener
import com.example.arch_store.databinding.FragmentOrdersBinding
import com.example.arch_store.databinding.FragmentProfileBinding
import com.example.arch_store.databinding.FragmentSignInBinding
import com.example.arch_store.models.Order
import com.example.arch_store.models.User
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.MoneyFormatter
import com.example.arch_store.utils.UserSession
import com.example.arch_store.view_models.AuthViewModel
import com.example.arch_store.view_models.OrderStateEvent
import com.example.arch_store.view_models.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrdersFragment : Fragment(), OrdersListListener {
    private val viewModel: OrderViewModel by viewModels()

    @Inject
    lateinit var session: UserSession

    @Inject
    lateinit var moneyFormatter: MoneyFormatter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOrdersBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)



        viewModel.setStateEvent(OrderStateEvent.GetOrders, session = session, networkOrder = null)
        subscribeObserversToGetOrders(binding);

        return binding.root
    }

    private fun setupOrderList(orders: List<Order>, binding: FragmentOrdersBinding) {
        var ordersAdapter: OrdersListAdapter = OrdersListAdapter(
            orders = orders,
            ctx = requireActivity(),
            ordersListListener = this,
            formatter = moneyFormatter
        )

        binding.orders.apply {
            adapter = ordersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun subscribeObserversToGetOrders(binding: FragmentOrdersBinding) {
        viewModel.getOrders.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Order>> -> {
                    binding.loading.visibility = View.GONE

                    if (dataState.data.isEmpty())
                        binding.noOrders.visibility = View.VISIBLE

                    setupOrderList(dataState.data, binding);
                }
                is DataState.Error -> {
                    println("Error login.")
                    binding.loading.visibility = View.GONE

                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }


    override fun onItemClicked(order: Order) {
        findNavController().navigate(
            OrdersFragmentDirections.actionOrdersFragmentToOrderDetailsFragment(
                orderId = order.orderId
            )
        )
    }

}