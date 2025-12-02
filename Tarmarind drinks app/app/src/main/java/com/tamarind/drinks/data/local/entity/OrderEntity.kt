package com.tamarind.drinks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val orderNumber: String,
    val userId: String,
    val status: String, // "processing", "shipped", "delivered", "cancelled"
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
    val paymentMethod: String, // "card", "paypal", "mpesa", "google_pay"
    val shippingAddress: String, // JSON string
    val deliveryMethod: String, // "standard", "express"
    val trackingNumber: String? = null,
    val estimatedDelivery: Long? = null,
    val items: String, // JSON array of order items
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
