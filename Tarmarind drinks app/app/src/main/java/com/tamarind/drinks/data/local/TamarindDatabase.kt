package com.tamarind.drinks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamarind.drinks.data.local.dao.*
import com.tamarind.drinks.data.local.entity.*

@Database(
    entities = [
        ProductEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        UserEntity::class,
        AddressEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class TamarindDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun userDao(): UserDao
}
