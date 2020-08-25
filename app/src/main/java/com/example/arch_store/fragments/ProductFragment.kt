package com.example.arch_store.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.HomeListener
import com.example.arch_store.adapters.ProductListAdapter
import com.example.arch_store.adapters.ProductListListener
import com.example.arch_store.databinding.FragmentProductBinding
import com.example.arch_store.models.Product
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.ProductStateEvent
import com.example.arch_store.view_models.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProductFragment(
    var homeListener: HomeListener,
    var categoryId: Int
) : Fragment(


), ProductListListener {
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false);

        viewModel.setStateEvent(ProductStateEvent.GetProductsByCatId, catId = categoryId)

        subscribeObservers(binding)


        return binding.root
    }

    //setUpRecyclerView
    private fun setUpRecyclerView(binding: FragmentProductBinding, products: List<Product>) {
        var productList = binding.productList

        var productAdapter: ProductListAdapter = ProductListAdapter(
            ctx = requireActivity(),
            products = products,
            productListListener = this
        )
        productList.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(activity, 2)
        }
    }

    private fun subscribeObservers(binding: FragmentProductBinding) {
        viewModel.products.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Product>> -> {
                    binding.loading.visibility = View.GONE

                    println("we have data" + dataState.data.toString())

                    setUpRecyclerView(binding, dataState.data);
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

    override fun onItemClicked(product: Product) {
        homeListener.onItemClicked(product)
    }

}