package com.example.arch_store.utils

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

//    fun mapToEntity(domainModel: DomainModel): Entity
}