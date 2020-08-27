package com.example.arch_store.view_models

import com.example.arch_store.models.*
import com.example.arch_store.repository.OrderRepository
import com.example.arch_store.service.responses.NetworkOrder
import com.example.arch_store.service.responses.NetworkOrderLine


import com.example.arch_store.repository.CategoryRepository


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.repository.ProductRepository
import com.example.arch_store.utils.DataState
import com.example.arch_store.utils.UserSession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class OrderViewModel
@ViewModelInject
constructor(
    private val orderRepository: OrderRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    //    order
    private val _createOrder: MutableLiveData<DataState<Order>> = MutableLiveData()

    val createOrder: LiveData<DataState<Order>>
        get() = _createOrder

//    order line

    private val _createOrderLine: MutableLiveData<DataState<OrderLine>> = MutableLiveData()

    val createOrderLine: LiveData<DataState<OrderLine>>
        get() = _createOrderLine

//    all order

    private val _orders: MutableLiveData<DataState<List<Order>>> = MutableLiveData()

    val getOrders: LiveData<DataState<List<Order>>>
        get() = _orders

//    all orderLines

    private val _shipment: MutableLiveData<DataState<Shipment>> = MutableLiveData()

    val getShipment: LiveData<DataState<Shipment>>
        get() = _shipment


    fun setStateEvent(
        orderStateEvent: OrderStateEvent,
        networkOrder: NetworkOrder?,
        networkOrderLine: NetworkOrderLine? = null,
        session: UserSession,
        orderId: Int = 0
    ) {
        viewModelScope.launch {

            when (orderStateEvent) {
                is OrderStateEvent.CreateOrder -> {
                    orderRepository.createOrder(
                        networkOrder,
                        session.getUserData().token
                    )
                        .onEach { dataState ->
                            _createOrder.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is OrderStateEvent.CreateOrderLine -> {
                    orderRepository.createOrderLine(
                        networkOrderLine,
                        session.getUserData().token
                    )
                        .onEach { dataState ->
                            _createOrderLine.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is OrderStateEvent.GetOrders -> {
                    orderRepository.getAllOrders(
                        session
                    )
                        .onEach { dataState ->
                            _orders.value = dataState
                        }
                        .launchIn(viewModelScope)
                }
                is OrderStateEvent.GetOrderLines -> {
                    orderRepository.getOrderLines(
                        orderId,
                        session
                    )
                        .onEach { dataState ->
                            _shipment.value = dataState
                        }
                        .launchIn(viewModelScope)
                }

            }
        }
    }

}
//getCategories

sealed class OrderStateEvent() {
    object CreateOrder : OrderStateEvent()
    object CreateOrderLine : OrderStateEvent()
    object GetOrders : OrderStateEvent()
    object GetOrderLines : OrderStateEvent()

}
