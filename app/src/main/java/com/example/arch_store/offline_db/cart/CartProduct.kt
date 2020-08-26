package com.example.arch_store.offline_db.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
class CartProduct(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0,

    @ColumnInfo(name = "product_id")
    var productId: Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "price")
    var price: Float,
    @ColumnInfo(name = "discount")
    var discount: Float,
    @ColumnInfo(name = "previewImage")
    var previewImage: String,
    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @ColumnInfo(name = "size")
    var size: String

)
