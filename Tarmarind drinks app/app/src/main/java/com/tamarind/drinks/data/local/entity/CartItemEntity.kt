package com.tamarind.drinks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val id: String,
    val productId: String,
    val productName: String,
    val productImageUrl: String,
    val price: Double,
    val quantity: Int,
    val selectedSize: String,
    val userId: String? = null, // null for guest users
    val addedAt: Long = System.currentTimeMillis()
)
