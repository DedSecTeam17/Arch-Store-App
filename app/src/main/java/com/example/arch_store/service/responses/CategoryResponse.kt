package com.example.arch_store.service.responses

import com.google.gson.annotations.SerializedName

data class NetworkCategory(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)