package com.example.arch_store.offline_db.favourtites


import androidx.room.*
import com.example.arch_store.offline_db.cart.CartProduct

@Dao
interface FavDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: FavProduct): Long


    @Delete
    suspend fun deleteProduct(product: FavProduct): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(product: FavProduct): Int

    @Query("SELECT * FROM favs ORDER BY id ASC")
    suspend fun getAllProducts(): List<FavProduct>
}