package com.example.arch_store.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.arch_store.R
import com.example.arch_store.adapters.SearchListAdapter
import com.example.arch_store.adapters.SearchListListener
import com.example.arch_store.databinding.FragmentSearchBinding
import com.example.arch_store.models.Product
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.ProductStateEvent
import com.example.arch_store.view_models.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment(), SearchListListener {
    private val viewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding: FragmentSearchBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);


        subscribeObservers(binding);
        viewModel.setStateEvent(ProductStateEvent.SearchForProduct, word = "");
        binding.searchFiled.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                viewModel.setStateEvent(ProductStateEvent.SearchForProduct, word = s.toString());
            }
        })


        return binding.root
    }

    private fun subscribeObservers(binding: FragmentSearchBinding) {
        viewModel.searchProducts.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<Product>> -> {
                    binding.loading.visibility = View.GONE
                    setSearchList(binding , dataState.data)

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

    private fun setSearchList(binding: FragmentSearchBinding, products: List<Product>) {
        var productList = binding.searchResult

        var productAdapter: SearchListAdapter = SearchListAdapter(
            ctx = requireActivity(),
            products = products,
            searchListListener = this
        )
        productList.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }

    override fun onItemClicked(product: Product) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchDestToProductDetailFragment(
                product.id
            )
        )
    }

}