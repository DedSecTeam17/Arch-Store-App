package com.example.arch_store.models


data class Order
    (
    var  orderId : Int  ,
    var orderName: String="",
    var orderStatus: String,
    var orderLinesCount: Int,
    var orderLinesTotal: Float,
    var  createAt  :String
)
