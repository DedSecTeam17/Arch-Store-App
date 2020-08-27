package com.example.arch_store.repository

import com.example.arch_store.models.*
import com.example.arch_store.service.mapping.*


import com.example.arch_store.service.ApiCalls
import com.example.arch_store.service.responses.NetworkOrder
import com.example.arch_store.service.responses.NetworkOrderLine
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.UserSession
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.time.Instant
import java.time.format.DateTimeFormatter

//CategoryRepository
class OrderRepository
constructor(
    private val apiCalls: ApiCalls,
    private var networkOrderToOrder: NetworkOrderToOrder,
    private var networkOrderLineToOrderLine: NetworkOrderLineToOrderLine

) {
    suspend fun createOrder(
        networkOrder: NetworkOrder?,
        token: String
    ): Flow<DataState<Order>> = flow {
        emit(DataState.Loading)

        try {
            val order = apiCalls.createOrder(
                userId = networkOrder?.user?.id!!,
                address = networkOrder.location,
                total = networkOrder.total,
                phone = networkOrder.phone,
                Fname = networkOrder.f_name,
                Lname = networkOrder.l_name,
                email = networkOrder.email,
                authHeader = "bearer $token",
                order_id = System.currentTimeMillis().toString()
            );
            val data = networkOrderToOrder.mapFromEntity(order)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }

    suspend fun createOrderLine(

        networkOrderLine: NetworkOrderLine?,
        token: String
    ): Flow<DataState<OrderLine>> = flow {

        print("PRODUCT ID ------------------------------------> ${networkOrderLine?.product?.id!!}")
        emit(DataState.Loading)
        try {
            val orderLine = apiCalls.createOrderLine(
                productId = networkOrderLine?.product?.id!!,
                quantity = networkOrderLine.quantity,
                size = networkOrderLine.selected_size,
                orderId = networkOrderLine.order.id,
                authHeader = "bearer $token"

            );
            val data = networkOrderLineToOrderLine.mapFromEntity(orderLine)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }

    suspend fun getAllOrders(
        session: UserSession
    ): Flow<DataState<List<Order>>> = flow {
        emit(DataState.Loading)
        try {
            val orders = apiCalls.getMyOrders(
                id = session.getUserData().id,
                authHeader ="bearer ${session.getUserData().token}",
                sortBy = "created_at:DESC"
            );
            val data = networkOrderToOrder.mapFromListOfEntities(orders)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }


    suspend fun getOrderLines(
        orderId: Int,
        session: UserSession
    ): Flow<DataState<Shipment>> = flow {
        emit(DataState.Loading)
        try {
            val orders = apiCalls.getOrderOrderLines(
                id = orderId,
                authHeader ="bearer ${session.getUserData().token}"
            );
            val data = networkOrderLineToOrderLine.mapFromShipment(orders)

            emit(DataState.Success(data))
        } catch (e: Exception) {
            println(e.message)
            emit(DataState.Error(e))
        }
    }


}