package com.example.arch_store.offline_db.cart

import androidx.room.*
import com.example.arch_store.offline_db.cart.CartProduct

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(blogEntity: CartProduct): Long


    @Delete
    suspend fun deleteProduct(blogEntity: CartProduct): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(blogEntity: CartProduct): Int

    @Query("SELECT * FROM product ORDER BY id ASC")
    suspend fun getAllProducts(): List<CartProduct>
}