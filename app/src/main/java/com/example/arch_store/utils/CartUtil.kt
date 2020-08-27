package com.example.arch_store.utils

import com.example.arch_store.offline_db.cart.CartProduct
import javax.inject.Inject

class CartUtil @Inject constructor(){

     fun calculateTotal(products: List<CartProduct> ,  isCart : Boolean  = false): Float {
        var total: Float = 0f
        for (product in products) {
            if (!isCart){
                if (product.discount > 0) {
                    var percentageToNum = product.price * product.discount;

                    total += (product.price - (percentageToNum / 100))
                } else {
                    total += product.quantity * product.price

                }
            }else{
                total += product.quantity * product.price

            }

        }
        return total
    }

}