package com.example.arch_store.models

data class Shipment
    (
    var  orderId : Int  ,
    var firstName: String="",
    var lastName: String,
    var email: String,
    var phone: String ,
    var address: String ,
    var  product :List<Product>
)
