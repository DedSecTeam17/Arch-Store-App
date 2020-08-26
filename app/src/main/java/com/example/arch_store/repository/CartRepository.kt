package com.example.arch_store.repository

import com.example.arch_store.offline_db.cart.CartDao
import com.example.arch_store.offline_db.cart.CartProduct

import com.example.arch_store.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

//CategoryRepository
class CartRepository
constructor(
    private val cartDao: CartDao
) {
    suspend fun getProducts(): Flow<DataState<List<CartProduct>>> = flow {
        emit(DataState.Loading)
        try {
            val products = cartDao.getAllProducts();
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun deleteProducts(cartProduct: CartProduct): Flow<DataState<Int>> = flow {
        emit(DataState.Loading)
        try {
            val products = cartDao.deleteProduct(cartProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun insertProduct(cartProduct: CartProduct): Flow<DataState<Long>> = flow {
        emit(DataState.Loading)
        try {
            val products = cartDao.insertProduct(cartProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun updateProduct(cartProduct: CartProduct): Flow<DataState<Int>> = flow {
        emit(DataState.Loading)
        try {
            val products = cartDao.updateProduct(cartProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}