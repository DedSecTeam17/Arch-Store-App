package com.example.arch_store.offline_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.arch_store.offline_db.cart.CartProduct
import com.example.arch_store.offline_db.cart.CartDao
import com.example.arch_store.offline_db.favourtites.FavDao
import com.example.arch_store.offline_db.favourtites.FavProduct


@Database(entities = [CartProduct::class,FavProduct::class], version = 3)
abstract class ProductDataBase : RoomDatabase() {

    abstract fun cartDao(): CartDao
    abstract fun favDao(): FavDao


    companion object {
        val DATABASE_NAME: String = "blog_db"
    }


}