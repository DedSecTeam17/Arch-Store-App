package com.example.arch_store.di

import com.example.arch_store.repository.AuthRepository
import com.example.arch_store.repository.CategoryRepository
import com.example.arch_store.repository.ProductRepository
import com.example.arch_store.service.ApiCalls
import com.example.arch_store.service.mapping.AuthResponseToUser
import com.example.arch_store.service.mapping.NetworkCatToCategory
import com.example.arch_store.service.mapping.NetworkProductToProduct
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


    //ProductRepository

}
