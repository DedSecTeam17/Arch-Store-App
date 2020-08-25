package com.example.arch_store.repository

import com.example.arch_store.models.Category
import com.example.arch_store.service.mapping.NetworkCatToCategory


import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.arch_store.models.User
import com.example.arch_store.service.ApiCalls
import com.example.arch_store.service.mapping.AuthResponseToUser
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
class CategoryRepository
constructor(
    private val apiCalls: ApiCalls,
    private val networkCatToCategory: NetworkCatToCategory
) {
    suspend fun getCategories(): Flow<DataState<List<Category>>> = flow {
        emit(DataState.Loading)
        try {
            val categories = apiCalls.getCategories( sortBy = "created_at:DESC");
            val data = networkCatToCategory.mapFromListOfEntities(categories)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}