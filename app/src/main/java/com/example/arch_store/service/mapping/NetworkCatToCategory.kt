package com.example.arch_store.service.mapping

import com.example.arch_store.models.Category
import com.example.arch_store.service.responses.NetworkCategory


import com.example.arch_store.models.User
import com.example.arch_store.service.responses.AuthResponse
import com.example.arch_store.utils.EntityMapper
import javax.inject.Inject

class NetworkCatToCategory
@Inject
constructor() : EntityMapper<NetworkCategory, Category> {

    override fun mapFromEntity(entity: NetworkCategory): Category {
        return Category(
            id = entity.id,
            name = entity.name

        )
    }

    //entities
    fun mapFromListOfEntities(entities: List<NetworkCategory>): List<Category> {
        return entities.map { en ->
            mapFromEntity(en)
        }

    }

}
