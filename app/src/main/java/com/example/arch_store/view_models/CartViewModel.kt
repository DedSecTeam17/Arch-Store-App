package com.example.arch_store.view_models

import com.example.arch_store.models.Category
import com.example.arch_store.repository.CategoryRepository


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.models.User
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.repository.CartRepository
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CartViewModel
@ViewModelInject
constructor(
    private val cartRepository: CartRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _cartProducts: MutableLiveData<DataState<List<CartProduct>>> = MutableLiveData()

    private val _productCreated: MutableLiveData<DataState<Long>> = MutableLiveData()
    private val _productUpdated: MutableLiveData<DataState<Int>> = MutableLiveData()
    private val _productDeleted: MutableLiveData<DataState<Int>> = MutableLiveData()

    private val _productsDeleted: MutableLiveData<DataState<Int>> = MutableLiveData()


    val cartProducts: LiveData<DataState<List<CartProduct>>>
        get() = _cartProducts
    val productCreated: LiveData<DataState<Long>>
        get() = _productCreated
    val productDeleted: LiveData<DataState<Int>>
        get() = _productDeleted

    val productsDeleted: LiveData<DataState<Int>>
        get() = _productsDeleted
    val productUpdated: LiveData<DataState<Int>>
        get() = _productUpdated


    fun setStateEvent(
        cartStateEvent: CartStateEvent,
        cartProduct: CartProduct = CartProduct(
            quantity = 0,
            productId = 0,
            title = "",
            price = 0f,
            discount = 0f,
            previewImage = "",
            size = ""

        )
    ) {
        viewModelScope.launch {

            when (cartStateEvent) {
                is CartStateEvent.GetAllCartProducts -> {
                    cartRepository.getProducts(
                    )
                        .onEach { dataState ->
                            _cartProducts.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is CartStateEvent.InsertCartProduct -> {
                    cartRepository.insertProduct(
                        cartProduct = cartProduct
                    )
                        .onEach { dataState ->
                            _productCreated.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is CartStateEvent.UpdateCartProduct -> {
                    cartRepository.updateProduct(
                        cartProduct = cartProduct
                    )
                        .onEach { dataState ->
                            _productUpdated.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is CartStateEvent.DeleteCartProduct -> {
                    cartRepository.deleteProducts(
                        cartProduct = cartProduct
                    )
                        .onEach { dataState ->
                            _productDeleted.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

                is CartStateEvent.DeleteAll -> {
                    cartRepository.deleteAll(
                    )
                        .onEach { dataState ->
                            _productsDeleted.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}
//getCategories

sealed class CartStateEvent() {

    object GetAllCartProducts : CartStateEvent()
    object DeleteCartProduct : CartStateEvent()
    object UpdateCartProduct : CartStateEvent()
    object InsertCartProduct : CartStateEvent()
    object DeleteAll : CartStateEvent()


}
