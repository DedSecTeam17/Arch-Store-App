package com.example.arch_store.di

import android.content.Context
import androidx.room.Room
import com.example.arch_store.offline_db.ProductDataBase
import com.example.arch_store.offline_db.cart.CartDao
import com.example.arch_store.offline_db.favourtites.FavDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object OfflineDataBaseModule {


    @Singleton
    @Provides
    fun provideCartDataBase(@ApplicationContext context: Context): ProductDataBase {
        return Room
            .databaseBuilder(
                context,
                ProductDataBase::class.java,
                ProductDataBase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideCartDao(blogDatabase: ProductDataBase): CartDao {
        return blogDatabase.cartDao()
    }

    @Singleton
    @Provides
    fun provideFavDao(blogDatabase: ProductDataBase): FavDao {
        return blogDatabase.favDao()
    }


}