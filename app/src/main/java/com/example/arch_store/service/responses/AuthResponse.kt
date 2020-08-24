package com.example.arch_store.service.responses

import com.google.gson.annotations.SerializedName

 class AuthResponse(

    @SerializedName("jwt") val jwt: String,
    @SerializedName("user") val user: UserNetwork
)


data class UserNetwork(

    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("confirmed") val confirmed: Boolean,
    @SerializedName("blocked") val blocked: Boolean,
    @SerializedName("role") val role: Role,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)


data class Role(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("type") val type: String
)