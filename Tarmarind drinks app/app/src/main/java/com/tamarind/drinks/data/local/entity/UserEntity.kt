package com.tamarind.drinks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
    val role: String = "customer", // "customer", "admin"
    val profileImageUrl: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
