package com.example.arch_store.utils

import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

//MoneyFormatter
@Singleton
class MoneyFormatter @Inject constructor() {



    fun  getReadableMoney(money : Float)  :String{
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance("USD")

      return  format.format(money);
    }


}