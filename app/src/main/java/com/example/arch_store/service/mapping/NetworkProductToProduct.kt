package com.example.arch_store.service.mapping

import com.example.arch_store.models.Category
import com.example.arch_store.models.Product
import com.example.arch_store.service.responses.NetworkCategory


import com.example.arch_store.service.responses.ProductResponse
import com.example.arch_store.utils.EntityMapper
import javax.inject.Inject

class NetworkProductToProduct
@Inject
constructor() : EntityMapper<ProductResponse, Product> {

    override fun mapFromEntity(entity: ProductResponse): Product {
        return Product(
            id = entity.id,
            title = entity.name,
            images = entity.images.map { img -> img.url }.toList(),
            sizes = entity.sizes.map { size -> size.size.toString() },
            description = entity.description,
            previewImage = entity.preview_image.formats.thumbnail.url,
            discount = entity.discount,
            price = entity.price
        )
    }

    //entities
    fun mapFromListOfEntities(entities: List<ProductResponse>): List<Product> {
        return entities.map { en ->
            mapFromEntity(en)
        }

    }

}
