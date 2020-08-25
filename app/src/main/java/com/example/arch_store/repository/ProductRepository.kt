package com.example.arch_store.repository

import com.example.arch_store.models.Category
import com.example.arch_store.service.mapping.NetworkCatToCategory


import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.arch_store.models.Product
import com.example.arch_store.models.User
import com.example.arch_store.service.ApiCalls
import com.example.arch_store.service.mapping.AuthResponseToUser
import com.example.arch_store.service.mapping.NetworkProductToProduct
import com.example.arch_store.service.responses.AuthResponse
import com.example.arch_store.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

//CategoryRepository
class ProductRepository
constructor(
    private val apiCalls: ApiCalls,
    private val networkProductToProduct: NetworkProductToProduct
) {
    suspend fun getCategories(catId: Int): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading)
        try {
            val products = apiCalls.getProductsByCategoryId(catId);
            val data = networkProductToProduct.mapFromListOfEntities(products)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }

    suspend fun search(word: String): Flow<DataState<List<Product>>> = flow {
        emit(DataState.Loading)
        try {
            val products = apiCalls.searchForProduct(nameContains = word);
            val data = networkProductToProduct.mapFromListOfEntities(products)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }


    suspend fun getProductById(id: Int): Flow<DataState<Product>> = flow {
        emit(DataState.Loading)
        try {
            val products = apiCalls.getProductById(id);
            val data = networkProductToProduct.mapFromEntity(products)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }


}