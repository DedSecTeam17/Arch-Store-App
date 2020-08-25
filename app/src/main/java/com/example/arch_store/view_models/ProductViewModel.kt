package com.example.arch_store.view_models

import com.example.arch_store.models.Category
import com.example.arch_store.repository.CategoryRepository


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.models.Product
import com.example.arch_store.models.User
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.repository.ProductRepository
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class ProductViewModel
@ViewModelInject
constructor(
    private val productRepository: ProductRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _products: MutableLiveData<DataState<List<Product>>> = MutableLiveData()

    val products: LiveData<DataState<List<Product>>>
        get() = _products

    //_search_result
    private val _searchProducts: MutableLiveData<DataState<List<Product>>> = MutableLiveData()

    val searchProducts: LiveData<DataState<List<Product>>>
        get() = _searchProducts


    private val _product: MutableLiveData<DataState<Product>> = MutableLiveData()

    val product: LiveData<DataState<Product>>
        get() = _product

    fun setStateEvent(
        productStateEvent: ProductStateEvent,
        catId: Int = 0,
        productId: Int = 0,
        word: String = ""
    ) {
        viewModelScope.launch {

            when (productStateEvent) {
                is ProductStateEvent.GetProductsByCatId -> {
                    productRepository.getCategories(
                        catId
                    )
                        .onEach { dataState ->
                            _products.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is ProductStateEvent.GetProductById -> {
                    productRepository.getProductById(
                        productId
                    )
                        .onEach { dataState ->
                            _product.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is ProductStateEvent.SearchForProduct -> {
                    productRepository.search(
                        word
                    )
                        .onEach { dataState ->
                            _searchProducts.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}
//getCategories

sealed class ProductStateEvent() {

    object GetProductsByCatId : ProductStateEvent()
    object GetProductById : ProductStateEvent()
    object SearchForProduct : ProductStateEvent()


}
