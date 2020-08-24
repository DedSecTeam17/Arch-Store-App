package com.example.arch_store.repository


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

class AuthRepository
constructor(
    private val apiCalls: ApiCalls,
    private val authResponseToUser: AuthResponseToUser
) {
    suspend fun signIn(id: String, pwd: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val authResponse = apiCalls.signIn(identifier = id, password = pwd);
            val user = authResponseToUser.mapFromEntity(authResponse)

            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }


    suspend fun signUp(userName: String, email: String, password: String): Flow<DataState<User>> =
        flow {
            emit(DataState.Loading)
            try {
                val authResponse =
                    apiCalls.signUp(username = userName, email = email, password = password)
                val user = authResponseToUser.mapFromEntity(authResponse)

                emit(DataState.Success(user))
            } catch (e: Exception) {


                emit(DataState.Error(e))
            }
        }
}