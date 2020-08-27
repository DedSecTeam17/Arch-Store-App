package com.example.arch_store.service.responses

import com.example.arch_store.models.OrderLine
import com.google.gson.annotations.SerializedName


data class NetworkOrder(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("user") val user: OrderUser,
    @SerializedName("order_state") val order_state: OrderStatus = OrderStatus(
        id = 0,
        state = "",
        created_at = "",
        updated_at = ""
    ),
    @SerializedName("location") val location: String = "",
    @SerializedName("total") val total: Float = 0f,
    @SerializedName("order_id") val order_id: String = "",
    @SerializedName("phone") val phone: String = "",
    @SerializedName("f_name") val f_name: String = "",
    @SerializedName("l_name") val l_name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("created_at") val created_at: String = "",
    @SerializedName("updated_at") val updated_at: String = "",
    @SerializedName("order_lines") val order_lines: List<OrderOrderLine> = arrayListOf()
)

data class OrderUser(

    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("provider") val provider: String = "",
    @SerializedName("confirmed") val confirmed: Boolean = false,
    @SerializedName("blocked") val blocked: Boolean = false,
    @SerializedName("role") val role: Int = 0,
    @SerializedName("created_at") val created_at: String = "",
    @SerializedName("updated_at") val updated_at: String = ""
)

data class OrderOrderLine(

    @SerializedName("id") val id: Int,
    @SerializedName("product") val product: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("selected_size") val selected_size: Int,
    @SerializedName("selected_color") val selected_color: String,
    @SerializedName("order") val order: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class OrderStatus(

    @SerializedName("id") val id: Int,
    @SerializedName("state") val state: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)