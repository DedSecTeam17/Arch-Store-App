package com.example.arch_store.view_models

import com.example.arch_store.models.Category
import com.example.arch_store.repository.CategoryRepository


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.models.User
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CategoryViewModel
@ViewModelInject
constructor(
    private val categoryRepository: CategoryRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _categories: MutableLiveData<DataState<List<Category>>> = MutableLiveData()

    val categories: LiveData<DataState<List<Category>>>
        get() = _categories

    fun setStateEvent(
        categoryStateEvent: CategoryStateEvent
    ) {
        viewModelScope.launch {

            when (categoryStateEvent) {
                is CategoryStateEvent.GetCategories -> {
                    categoryRepository.getCategories(
                    )
                        .onEach { dataState ->
                            _categories.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }

}
//getCategories

sealed class CategoryStateEvent() {

    object GetCategories : CategoryStateEvent()

}
