package com.example.arch_store.service.responses

import com.google.gson.annotations.SerializedName


data class NetworkOrderLine(

    @SerializedName("id") val id: Int=0,
    @SerializedName("product") val product: OrderLineProduct,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("selected_size") val selected_size: Int,
    @SerializedName("selected_color") val selected_color: String="",
    @SerializedName("order") val order: OrderLineOrder,
    @SerializedName("created_at") val created_at: String="",
    @SerializedName("updated_at") val updated_at: String=""
)

data class OrderLineProduct(

    @SerializedName("id") val id: Int=0,
    @SerializedName("name") val name: String="",
    @SerializedName("description") val description: String="",
    @SerializedName("price") val price: Float=0f,
    @SerializedName("discount") val discount: Float=0f,
    @SerializedName("created_at") val created_at: String="",
    @SerializedName("updated_at") val updated_at: String="",
    @SerializedName("images") val images: List<Images>?,
    @SerializedName("preview_image") val preview_image: Preview_image?
)

data class OrderLineOrder(

    @SerializedName("id") val id: Int=0,
    @SerializedName("user") val user: Int=0,
    @SerializedName("order_state") val order_state: String="",
    @SerializedName("location") val location: String="",
    @SerializedName("total") val total: Float=0f,
    @SerializedName("order_id") val order_id: String="",
    @SerializedName("phone") val phone: String="",
    @SerializedName("f_name") val f_name: String="",
    @SerializedName("l_name") val l_name: String="",
    @SerializedName("email") val email: String="",
    @SerializedName("created_at") val created_at: String="",
    @SerializedName("updated_at") val updated_at: String=""
)
