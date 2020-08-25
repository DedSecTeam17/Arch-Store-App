package com.example.arch_store.service.responses

import com.google.gson.annotations.SerializedName


data class ProductResponse(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("price") val price: Float,
    @SerializedName("discount") val discount: Float,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("images") val images: List<Images>,
    @SerializedName("preview_image") val preview_image: Preview_image,
    @SerializedName("colors") val colors: List<Colors>,
    @SerializedName("sizes") val sizes: List<Sizes>,
    @SerializedName("categories") val categories: List<Categories>,
    @SerializedName("reviews") val reviews: List<Reviews>
)


data class Categories(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Colors(

    @SerializedName("id") val id: Int,
    @SerializedName("color") val color: String,
    @SerializedName("name") val name: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Formats(

    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("large") val large: Large,
    @SerializedName("medium") val medium: Medium,
    @SerializedName("small") val small: Small
)

data class Images(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("alternativeText") val alternativeText: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("formats") val formats: Formats,
    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("size") val size: Double,
    @SerializedName("url") val url: String,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("provider_metadata") val provider_metadata: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Large(

    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("size") val size: Double,
    @SerializedName("path") val path: String,
    @SerializedName("url") val url: String
)

data class Medium(

    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("size") val size: Double,
    @SerializedName("path") val path: String,
    @SerializedName("url") val url: String
)

data class Preview_image(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("alternativeText") val alternativeText: String,
    @SerializedName("caption") val caption: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("formats") val formats: Formats,
    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("size") val size: Double,
    @SerializedName("url") val url: String,
    @SerializedName("previewUrl") val previewUrl: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("provider_metadata") val provider_metadata: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Reviews(

    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: Int,
    @SerializedName("product") val product: Int,
    @SerializedName("review") val review: String,
    @SerializedName("ratting") val ratting: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Sizes(

    @SerializedName("id") val id: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
)

data class Small(

    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("size") val size: Double,
    @SerializedName("path") val path: String,
    @SerializedName("url") val url: String
)

data class Thumbnail(

    @SerializedName("hash") val hash: String,
    @SerializedName("ext") val ext: String,
    @SerializedName("mime") val mime: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("size") val size: Double,
    @SerializedName("path") val path: String,
    @SerializedName("url") val url: String
)