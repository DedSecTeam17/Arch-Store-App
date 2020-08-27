package com.example.arch_store.models

import com.example.arch_store.service.responses.OrderLineProduct

data class OrderLine(
    var products:OrderLineProduct,
    var firstName: String,
    var lastName: String,
    var address: String,
    var phoneNumber: String
)