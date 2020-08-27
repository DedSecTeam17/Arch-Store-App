package com.example.arch_store.service.mapping

import com.example.arch_store.models.Order
import com.example.arch_store.service.responses.NetworkOrder


import com.example.arch_store.utils.EntityMapper
import javax.inject.Inject

class NetworkOrderToOrder
@Inject
constructor() : EntityMapper<NetworkOrder, Order> {

    override fun mapFromEntity(entity: NetworkOrder): Order {

        var state = ""
        if (entity.order_state==null)
            state= "pending"
        else
            state = entity.order_state.state
        return Order(
            orderId = entity.id,
            orderName = entity.order_id?:"",
            orderStatus = state ,
            orderLinesCount = entity.order_lines.size,
            orderLinesTotal = entity.total,
            createAt =  entity.created_at

        )
    }

    //entities
    fun mapFromListOfEntities(entities: List<NetworkOrder>): List<Order> {
        return entities.map { en ->
            mapFromEntity(en)
        }

    }

}
