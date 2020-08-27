package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.HorizontalProductsListAdapter
import com.example.arch_store.adapters.HorizontalProductsListListener
import com.example.arch_store.databinding.FragmentEditProfileBinding
import com.example.arch_store.databinding.FragmentOrderDetailsBinding
import com.example.arch_store.databinding.FragmentOrdersBinding
import com.example.arch_store.models.Order
import com.example.arch_store.models.Product
import com.example.arch_store.models.Shipment
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.MoneyFormatter
import com.example.arch_store.utils.UserSession
import com.example.arch_store.view_models.OrderStateEvent
import com.example.arch_store.view_models.OrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class OrderDetailsFragment : Fragment(), HorizontalProductsListListener {

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

        val binding: FragmentOrderDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false)


        val args: OrderDetailsFragmentArgs by navArgs()


        subscribeObserversToGetOrders(binding)

        viewModel.setStateEvent(
            OrderStateEvent.GetOrderLines,
            session = session,
            orderId = args.orderId,
            networkOrder = null
        )

        return binding.root
    }

    private fun setupProductList(products: List<Product>, binding: FragmentOrderDetailsBinding) {
        var orderProductsAdapter: HorizontalProductsListAdapter = HorizontalProductsListAdapter(
            products = products,
            ctx = requireActivity(),
            horizontalProductsListListener = this,
            moneyFormatter = moneyFormatter
        )
        binding.productsH.apply {
            adapter = orderProductsAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(), LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun setupBasicInfo(shipment: Shipment, binding: FragmentOrderDetailsBinding) {

        binding.firstName.text = shipment.firstName
        binding.lastName.text = shipment.lastName
        binding.phone.text = shipment.phone
        binding.address.text = shipment.address


    }

    private fun subscribeObserversToGetOrders(binding: FragmentOrderDetailsBinding) {
        viewModel.getShipment.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Shipment> -> {
                    binding.loading.visibility = View.GONE
                    binding.detail.visibility = View.VISIBLE

                    setupProductList(dataState.data.product, binding);
                    setupBasicInfo(dataState.data, binding)
                }
                is DataState.Error -> {
                    println("Error login.")
                    binding.loading.visibility = View.GONE
                    binding.detail.visibility = View.GONE


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    binding.detail.visibility = View.GONE


                    println("Loading...")
                }
            }
        })
    }


    override fun onItemClicked(product: Product) {
        findNavController().navigate(
            OrderDetailsFragmentDirections.actionOrderDetailsFragmentToProductDetailFragment(
                productId = product.id
            )
        )
    }


}