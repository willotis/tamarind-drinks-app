package com.tamarind.drinks.di

import android.content.Context
import androidx.room.Room
import com.tamarind.drinks.data.local.TamarindDatabase
import com.tamarind.drinks.data.local.dao.CartDao
import com.tamarind.drinks.data.local.dao.OrderDao
import com.tamarind.drinks.data.local.dao.ProductDao
import com.tamarind.drinks.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TamarindDatabase {
        return Room.databaseBuilder(
            context,
            TamarindDatabase::class.java,
            "tamarind_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideProductDao(database: TamarindDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartDao(database: TamarindDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideOrderDao(database: TamarindDatabase): OrderDao {
        return database.orderDao()
    }

    @Provides
    fun provideUserDao(database: TamarindDatabase): UserDao {
        return database.userDao()
    }
}
