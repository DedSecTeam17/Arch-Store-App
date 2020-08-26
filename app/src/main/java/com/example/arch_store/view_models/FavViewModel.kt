package com.example.arch_store.view_models

import com.example.arch_store.offline_db.favourtites.FavProduct
import com.example.arch_store.repository.FavRepository


import com.example.arch_store.models.Category
import com.example.arch_store.repository.CategoryRepository


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.models.User
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FavViewModel
@ViewModelInject
constructor(
    private val favRepository: FavRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favProducts: MutableLiveData<DataState<List<FavProduct>>> = MutableLiveData()

    private val _productCreated: MutableLiveData<DataState<Long>> = MutableLiveData()
    private val _productUpdated: MutableLiveData<DataState<Int>> = MutableLiveData()
    private val _productDeleted: MutableLiveData<DataState<Int>> = MutableLiveData()


    val favProducts: LiveData<DataState<List<FavProduct>>>
        get() = _favProducts
    val productCreated: LiveData<DataState<Long>>
        get() = _productCreated
    val productDeleted: LiveData<DataState<Int>>
        get() = _productDeleted
    val productUpdated: LiveData<DataState<Int>>
        get() = _productUpdated


    fun setStateEvent(
        FavStateEvent: FavStateEvent,
        favProduct: FavProduct = FavProduct(
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

            when (FavStateEvent) {
                is FavStateEvent.GetAllFavProducts -> {
                    favRepository.getProducts(
                    )
                        .onEach { dataState ->
                            _favProducts.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is FavStateEvent.InsertFavProduct -> {
                    favRepository.insertProduct(
                        favProduct = favProduct
                    )
                        .onEach { dataState ->
                            _productCreated.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is FavStateEvent.UpdateFavProduct -> {
                    favRepository.updateProduct(
                        favProduct = favProduct
                    )
                        .onEach { dataState ->
                            _productUpdated.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is FavStateEvent.DeleteFavProduct -> {
                    favRepository.deleteProducts(
                        favProduct = favProduct
                    )
                        .onEach { dataState ->
                            _productDeleted.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}
//getCategories

sealed class FavStateEvent() {

    object GetAllFavProducts : FavStateEvent()
    object DeleteFavProduct : FavStateEvent()
    object UpdateFavProduct : FavStateEvent()
    object InsertFavProduct : FavStateEvent()


}
