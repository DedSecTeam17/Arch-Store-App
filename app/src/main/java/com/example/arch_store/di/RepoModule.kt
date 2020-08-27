package com.example.arch_store.di

import com.example.arch_store.offline_db.cart.CartDao
import com.example.arch_store.offline_db.favourtites.FavDao
import com.example.arch_store.repository.*
import com.example.arch_store.service.ApiCalls
import com.example.arch_store.service.mapping.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun authRepository(
        apiCalls: ApiCalls,
        authResponseToUser: AuthResponseToUser
    ): AuthRepository {
        return AuthRepository(apiCalls, authResponseToUser)
    }

    @Singleton
    @Provides
    fun categoryRepository(
        apiCalls: ApiCalls,
        networkCatToCategory: NetworkCatToCategory
    ): CategoryRepository {
        return CategoryRepository(apiCalls, networkCatToCategory)
    }


    @Singleton
    @Provides
    fun productRepository(
        apiCalls: ApiCalls,
        networkProductToProduct: NetworkProductToProduct
    ): ProductRepository {
        return ProductRepository(apiCalls, networkProductToProduct)
    }


    @Singleton
    @Provides
    fun cartRepository(

        cartDao: CartDao
    ): CartRepository {
        return CartRepository(cartDao)
    }


    @Singleton
    @Provides
    fun favRepository(

        favDao: FavDao
    ): FavRepository {
        return FavRepository(favDao)
    }


    @Singleton
    @Provides
    fun orderRepository(
        apiCalls: ApiCalls,
        networkOrderToOrder: NetworkOrderToOrder,
        networkOrderLineToOrderLine: NetworkOrderLineToOrderLine
    ): OrderRepository {
        return OrderRepository(apiCalls, networkOrderToOrder, networkOrderLineToOrderLine)
    }


    //ProductRepository

}
