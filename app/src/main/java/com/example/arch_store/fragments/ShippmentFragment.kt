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
import com.afollestad.vvalidator.form
import com.example.arch_store.R
import com.example.arch_store.databinding.FragmentBagBinding
import com.example.arch_store.databinding.FragmentShippmentBinding
import com.example.arch_store.databinding.FragmentSignInBinding
import com.example.arch_store.models.Order
import com.example.arch_store.models.OrderLine
import com.example.arch_store.models.User
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.service.responses.*
import com.example.arch_store.utils.CartUtil
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.UserSession
import com.example.arch_store.view_models.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shippment.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint

class ShippmentFragment : Fragment() {
    private val viewModel: OrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var order: Order

    var noOfOrderLine: Int = 0
    lateinit var cartProducts: List<CartProduct>

    @Inject lateinit  var  cartUtil  :CartUtil


    @Inject
    lateinit var session: UserSession
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: FragmentShippmentBinding =
            DataBindingUtil.inflate<FragmentShippmentBinding>(
                inflater,
                R.layout.fragment_shippment,
                container,
                false
            );
        binding.email.setText(session.getUserData().Email)


        formValidationAndSubmit(binding)
        subscribeToCreateOrder(binding)
        subscribeToCreateOrderLine(binding)
        subscribeObserversToGetCartList(binding)
        subscribeObserversToDeleteAll(binding)
        return binding.root
    }

    private fun createOrder(products: List<CartProduct>, binding: FragmentShippmentBinding) {

        viewModel.setStateEvent(
            OrderStateEvent.CreateOrder, networkOrder = NetworkOrder(
                user = OrderUser(id = session.getUserData().id),
                location = binding.address.text.toString(),
                f_name = binding.fName.text.toString(),
                l_name = binding.lName.text.toString(),
                phone = binding.phone.text.toString(),
                total = cartUtil.calculateTotal(products),
                email = binding.email.text.toString()


            ),
            session = session
        );

    }




    private fun createOrderLine(order: Order) {

        for (product in cartProducts) {
            viewModel.setStateEvent(
                OrderStateEvent.CreateOrderLine, null, NetworkOrderLine(
                    product = OrderLineProduct(
                        id = product.productId,
                        images = null,
                        preview_image = null
                    ),
                    quantity = product.quantity,
                    selected_size = product.size.toInt(),
                    order = OrderLineOrder(id = order.orderId)
                ),

                session = session
            )


        }

    }

    private fun formValidationAndSubmit(binding: FragmentShippmentBinding) {
        form {
            input(binding.fName) {
                isNotEmpty()
                length().atLeast(6)


            }
            input(binding.lName) {
                isNotEmpty()
                length().atLeast(6)


            }
            input(binding.address) {
                isNotEmpty()
                length().atLeast(6)


            }
            input(binding.phone) {
                isNotEmpty()
                length().atLeast(6)


            }
            input(binding.email) {
                isNotEmpty()
                isEmail()
            }
            submitWith(binding.createOrder) { result ->
                if (result.success()) {
                    // get all products from cart
                    cartViewModel.setStateEvent(CartStateEvent.GetAllCartProducts)


                }
                // this block is only called if form is valid.
                // do something with a valid form state.
            }
        }
    }

    private fun subscribeToCreateOrder(binding: FragmentShippmentBinding) {
        viewModel.createOrder.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Order> -> {
                    println("OrderCreated");
                    createOrderLine(order = dataState.data);

                }
                is DataState.Error -> {
                    println("Error create order.")
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "Error while create order",
                        Toast.LENGTH_LONG
                    ).show();


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }

    private fun subscribeToCreateOrderLine(binding: FragmentShippmentBinding) {
        viewModel.createOrderLine.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<OrderLine> -> {
                    println("Order line Created");
                    noOfOrderLine += 1
                    if (noOfOrderLine == cartProducts.size) {
                        cartViewModel.setStateEvent(CartStateEvent.DeleteAll)
                    }
                }
                is DataState.Error -> {
                    println("Error create order line .")
                    binding.loading.visibility = View.GONE
                    Toast.makeText(
                        requireActivity(),
                        "Error while create order line ",
                        Toast.LENGTH_LONG
                    ).show();


                }
                is DataState.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                    println("Loading...")
                }
            }
        })
    }

    private fun subscribeObserversToGetCartList(binding: FragmentShippmentBinding) {
        cartViewModel.cartProducts.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<List<CartProduct>> -> {
                    cartProducts = dataState.data;
                    if (cartProducts.isNotEmpty()) {
                        createOrder(dataState.data, binding)
                    }

                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }

    private fun subscribeObserversToDeleteAll(binding: FragmentShippmentBinding) {
        cartViewModel.productsDeleted.observe(requireActivity(), Observer { dataState ->
            when (dataState) {
                is DataState.Success<Int> -> {
                    Toast.makeText(
                        requireActivity(),
                        "Order created successfully",
                        Toast.LENGTH_LONG
                    ).show();

                    binding.loading.visibility = View.GONE
                    findNavController().navigate(R.id.action_shippmentFragment_to_orderSuccessFragment)

                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {
                    print("Loading..")
                }
            }
        })
    }


}