package com.example.arch_store.service

import com.example.arch_store.service.responses.AuthResponse
import com.example.arch_store.service.responses.NetworkCategory
import com.example.arch_store.service.responses.ProductResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiCalls {
    @FormUrlEncoded
    @POST("auth/local")
    suspend fun signIn(
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


    @GET("categories")
    suspend fun getCategories(
        @Query("_sort") sortBy: String

    ): List<NetworkCategory>


    @GET("products")
    suspend fun getProductsByCategoryId(
        @Query("categories") id: Int


    ): List<ProductResponse>

    @GET("products")
    suspend fun searchForProduct(
        @Query("name_contains") nameContains: String
    ): List<ProductResponse>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): ProductResponse


}