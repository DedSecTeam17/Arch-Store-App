package com.example.arch_store.service.mapping


import com.example.arch_store.models.Order
import com.example.arch_store.models.OrderLine
import com.example.arch_store.models.Product
import com.example.arch_store.models.Shipment
import com.example.arch_store.service.responses.NetworkOrder
import com.example.arch_store.service.responses.NetworkOrderLine
import com.example.arch_store.service.responses.OrderLineOrder


import com.example.arch_store.utils.EntityMapper
import javax.inject.Inject

class NetworkOrderLineToOrderLine
@Inject
constructor() : EntityMapper<NetworkOrderLine, OrderLine> {

    override fun mapFromEntity(entity: NetworkOrderLine): OrderLine {


        return OrderLine(
            products = entity.product,
            firstName = entity.order.f_name,
            lastName = entity.order.l_name,
            address = entity.order.location,
            phoneNumber = entity.order.phone
        )
    }

    //entities
    fun mapFromListOfEntities(entities: List<NetworkOrderLine>): List<OrderLine> {
        return entities.map { en ->
            mapFromEntity(en)
        }

    }

    //entities
    fun mapFromShipment(entities: List<NetworkOrderLine>): Shipment {
        var order: OrderLineOrder = entities[0].order
        return Shipment(
            orderId = order.id,
            firstName = order.f_name,
            lastName = order.l_name,
            email = order.email,
            phone = order.phone,
            address = order.location,
            product = entities.map { et ->
                val entity = et.product
                Product(
                    id = entity.id,
                    title = entity.name,
                    images = entity.images?.map { img -> img.url }?.toList()!!,
                    sizes = arrayListOf(et.selected_size.toString()),
                    description = entity.description,
                    previewImage = entity.preview_image?.formats?.thumbnail?.url!!,
                    discount = entity.discount,
                    price = entity.price


                )
            }
        )

    }


}
