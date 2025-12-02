package com.tamarind.drinks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val categoryName: String,
    val rating: Double,
    val reviewCount: Int,
    val inStock: Boolean,
    val ingredients: String, // JSON string
    val nutritionInfo: String, // JSON string
    val sizes: String, // JSON array of sizes (e.g., ["250ml", "500ml", "1L"])
    val isFeatured: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
