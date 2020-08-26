package com.example.arch_store.repository

import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.offline_db.favourtites.FavDao
import com.example.arch_store.offline_db.favourtites.FavProduct

import com.example.arch_store.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

//CategoryRepository
class FavRepository
constructor(
    private val favDao: FavDao
) {
    suspend fun getProducts(): Flow<DataState<List<FavProduct>>> = flow {
        emit(DataState.Loading)
        try {
            val products = favDao.getAllProducts();
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun deleteProducts(favProduct: FavProduct): Flow<DataState<Int>> = flow {
        emit(DataState.Loading)
        try {
            val products = favDao.deleteProduct(favProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun insertProduct(favProduct: FavProduct): Flow<DataState<Long>> = flow {
        emit(DataState.Loading)
        try {
            val products = favDao.insertProduct(favProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun updateProduct(favProduct: FavProduct): Flow<DataState<Int>> = flow {
        emit(DataState.Loading)
        try {
            val products = favDao.updateProduct(favProduct);
            emit(DataState.Success(products))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}