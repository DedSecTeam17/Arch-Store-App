package com.example.arch_store.service

import com.example.arch_store.service.responses.AuthResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiCalls {
    @FormUrlEncoded
     @POST("auth/local")
    suspend  fun signIn(
        @Field("identifier") identifier: String,
        @Field("password") password: String
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/local/register")
    suspend fun signUp(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): AuthResponse

}