package com.example.arch_store.service

import com.example.arch_store.service.responses.*
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

//    order stuff

    @FormUrlEncoded
    @POST("orders")
    suspend fun createOrder(
        @Field("user") userId: Int,
        @Field("location") address: String,
        @Field("total") total: Float,
        @Field("phone") phone: String,
        @Field("f_name") Fname: String,
        @Field("l_name") Lname: String,
        @Field("email") email: String,
        @Field("order_id") order_id: String,


        @Header("Authorization") authHeader: String

    ): NetworkOrder


    @FormUrlEncoded
    @POST("order-lines")

    suspend fun createOrderLine(
        @Field("product") productId: Int,
        @Field("quantity") quantity: Int,
        @Field("selected_size") size: Int,
        @Field("order") orderId: Int,
        @Header("Authorization") authHeader: String
    ): NetworkOrderLine

    @GET("orders")
    suspend fun getMyOrders(
        @Query("user") id: Int,
        @Query("_sort") sortBy: String,
        @Header("Authorization") authHeader: String

    ): List<NetworkOrder>


    @GET("order-lines")
    suspend fun getOrderOrderLines(
        @Query("order") id: Int,
        @Header("Authorization") authHeader: String

    ): List<NetworkOrderLine>

}