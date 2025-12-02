package com.tamarind.drinks.data.local.dao

import androidx.room.*
import com.tamarind.drinks.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE userId = :userId OR userId IS NULL")
    fun getCartItemsForUser(userId: String?): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE id = :itemId")
    suspend fun getCartItemById(itemId: String): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)

    @Update
    suspend fun updateCartItem(item: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("DELETE FROM cart_items WHERE userId = :userId OR userId IS NULL")
    suspend fun clearCartForUser(userId: String?)
}
