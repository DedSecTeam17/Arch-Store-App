package com.example.arch_store.fragments

import android.opengl.Visibility
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
import com.example.arch_store.adapters.CartListAdapter
import com.example.arch_store.adapters.CartListListener
import com.example.arch_store.databinding.FragmentBagBinding
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.utils.DataState
import com.example.arch_store.view_models.CartStateEvent
import com.example.arch_store.view_models.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.cart_row.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BagFragment : Fragment(), CartListListener {
    private val cartViewModel: CartViewModel by viewModels()

    lateinit var cartAdapter: CartListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var binding: FragmentBagBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bag, container, false)

        subscribeObserversToGetCartList(binding);
        subscribeObserversOnDeleteProduct(binding);
        subscribeObserversOnUpdateProduct(binding);

        cartViewModel.setStateEvent(CartStateEvent.GetAllCartProducts)


        return binding.root
    }


    private fun setupCartList(
        binding: FragmentBagBinding,
        products: List<CartProduct>
    ) {
        val cartList = binding.cartItems
        cartAdapter = CartListAdapter(
            products = products,
            ctx = requireActivity(),
            cartListListener = this
        )
        cartList.apply {
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


    private fun subscribeObserversToGetCartList(binding: FragmentBagBinding) {
        cartViewModel.cartProducts.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<CartProduct>> -> {

                    if (dataState.data.isEmpty()) {
                        binding.cart.visibility = View.GONE
                        binding.purchaseProducts.visibility = View.GONE
                        binding.emptyProducts.visibility = View.VISIBLE
                    } else {
                        binding.cart.visibility = View.VISIBLE
                        binding.purchaseProducts.visibility = View.VISIBLE
                        binding.emptyProducts.visibility = View.GONE
                    }
                    setupCartList(binding, dataState.data);
                }
                is DataState.Error -> {
                    println("Error while add product to cart")
                    Toast.makeText(
                        requireContext(),
                        "Error while get products",
                        Toast.LENGTH_LONG
                    ).show();

                    binding.cart.visibility = View.GONE
                    binding.purchaseProducts.visibility = View.GONE
                    binding.emptyProducts.visibility = View.GONE

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    private fun subscribeObserversOnDeleteProduct(binding: FragmentBagBinding) {
        cartViewModel.productDeleted.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Int> -> {
                    cartViewModel.setStateEvent(CartStateEvent.GetAllCartProducts)

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


    private fun subscribeObserversOnUpdateProduct(binding: FragmentBagBinding) {
        cartViewModel.productUpdated.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Int> -> {
//                    notify list
                    cartViewModel.setStateEvent(CartStateEvent.GetAllCartProducts)

                }
                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "error while updated product",
                        Toast.LENGTH_LONG
                    ).show()


                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }


    override fun onItemClicked(cartProduct: CartProduct) {
        findNavController().navigate(
            BagFragmentDirections.actionCartDestToProductDetailFragment(
                cartProduct.productId
            )
        );

    }

    override fun onQuantityDecreased(cartProduct: CartProduct) {
        if (cartProduct.quantity > 1) {
            cartProduct.quantity -= 1
            cartViewModel.setStateEvent(CartStateEvent.UpdateCartProduct, cartProduct)
        } else {
            cartViewModel.setStateEvent(CartStateEvent.DeleteCartProduct, cartProduct)

        }

    }

    override fun onQuantityIncreased(cartProduct: CartProduct) {
        cartProduct.quantity += 1
        cartViewModel.setStateEvent(CartStateEvent.UpdateCartProduct, cartProduct)
    }


}