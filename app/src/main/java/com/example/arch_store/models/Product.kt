package com.example.arch_store.models

data class Product(
    var title: String, var price: Float, var images: List<String>, var previewImage: String,
    var description: String, var sizes: List<String>, var discount: Float, var id: Int
) {
}