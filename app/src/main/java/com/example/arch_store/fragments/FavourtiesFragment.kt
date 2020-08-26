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
import com.example.arch_store.adapters.FavListAdapter
import com.example.arch_store.adapters.FavListListener
import com.example.arch_store.databinding.FragmentBagBinding
import com.example.arch_store.databinding.FragmentEditProfileBinding
import com.example.arch_store.databinding.FragmentFavourtiesBinding
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.offline_db.favourtites.FavProduct
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.CartStateEvent
import com.example.arch_store.view_models.CartViewModel
import com.example.arch_store.view_models.FavStateEvent
import com.example.arch_store.view_models.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavourtiesFragment : Fragment(), FavListListener {

    private val favViewModel: FavViewModel by viewModels()

    lateinit var favListAdapter: FavListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentFavourtiesBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favourties, container, false)



        subscribeObserversOnDeleteProduct(binding)
        subscribeObserversToGetFavList(binding)

        favViewModel.setStateEvent(FavStateEvent.GetAllFavProducts)

        return binding.root
    }


    private fun setupFavList(binding: FragmentFavourtiesBinding, products: List<FavProduct>) {

        favListAdapter = FavListAdapter(
            products = products,
            ctx = requireActivity(),
            favListListener = this
        )
        binding.favProducts.apply {
            adapter = favListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun subscribeObserversToGetFavList(binding: FragmentFavourtiesBinding) {
        favViewModel.favProducts.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<FavProduct>> -> {

                    if (dataState.data.isEmpty()) {
                        binding.favProducts.visibility = View.GONE
                        binding.emptyProducts.visibility = View.VISIBLE
                    } else {
                        binding.favProducts.visibility = View.VISIBLE
                        binding.emptyProducts.visibility = View.GONE
                    }
                    setupFavList(binding, dataState.data);
                }
                is DataState.Error -> {
                    println("Error while add product to cart")
                    Toast.makeText(
                        requireContext(),
                        "Error while get products",
                        Toast.LENGTH_LONG
                    ).show();

                    binding.favProducts.visibility = View.GONE
                    binding.emptyProducts.visibility = View.GONE

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    private fun subscribeObserversOnDeleteProduct(binding: FragmentFavourtiesBinding) {
        favViewModel.productDeleted.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Int> -> {
                    favViewModel.setStateEvent(FavStateEvent.GetAllFavProducts)

                }
                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "error while delete product",
                        Toast.LENGTH_LONG
                    ).show()


                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    override fun onItemClicked(favProduct: FavProduct) {
        findNavController().navigate(
            FavourtiesFragmentDirections.actionFavourtiesFragmentToProductDetailFragment(
                favProduct.productId
            )
        )

    }

    override fun onItemRemoveFromFav(favProduct: FavProduct) {
        favViewModel.setStateEvent(FavStateEvent.DeleteFavProduct, favProduct = favProduct)

    }


}