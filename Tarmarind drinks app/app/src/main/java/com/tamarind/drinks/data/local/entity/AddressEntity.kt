package com.tamarind.drinks.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String, // "Home", "Office", etc.
    val streetAddress: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
