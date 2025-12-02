package com.tamarind.drinks.data.remote.dto

import com.squareup.moshi.JsonClass

// Auth DTOs
@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String? = null
)

@JsonClass(generateAdapter = true)
data class ResetPasswordRequest(
    val email: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    val token: String,
    val user: UserDto
)

@JsonClass(generateAdapter = true)
data class UserDto(
    val id: String,
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
    val role: String = "customer",
    val profileImageUrl: String? = null
)

// Product DTOs
@JsonClass(generateAdapter = true)
data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val categoryId: String,
    val categoryName: String,
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val inStock: Boolean = true,
    val ingredients: List<String> = emptyList(),
    val nutritionInfo: NutritionInfo? = null,
    val sizes: List<String> = emptyList(),
    val isFeatured: Boolean = false
)

@JsonClass(generateAdapter = true)
data class NutritionInfo(
    val calories: Int,
    val protein: String,
    val carbohydrates: String,
    val fat: String,
    val sugar: String
)

@JsonClass(generateAdapter = true)
data class ProductsResponse(
    val products: List<ProductDto>,
    val page: Int,
    val totalPages: Int,
    val totalCount: Int
)

@JsonClass(generateAdapter = true)
data class Category(
    val id: String,
    val name: String,
    val imageUrl: String? = null
)

@JsonClass(generateAdapter = true)
data class CategoriesResponse(
    val categories: List<Category>
)

// Cart DTOs
@JsonClass(generateAdapter = true)
data class CartDto(
    val items: List<CartItemDto>,
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
    val couponCode: String? = null
)

@JsonClass(generateAdapter = true)
data class CartItemDto(
    val id: String,
    val productId: String,
    val productName: String,
    val productImageUrl: String,
    val price: Double,
    val quantity: Int,
    val selectedSize: String
)

@JsonClass(generateAdapter = true)
data class CartResponse(
    val cart: CartDto
)

@JsonClass(generateAdapter = true)
data class AddToCartRequest(
    val productId: String,
    val quantity: Int,
    val selectedSize: String
)

@JsonClass(generateAdapter = true)
data class UpdateCartItemRequest(
    val quantity: Int
)

@JsonClass(generateAdapter = true)
data class ApplyCouponRequest(
    val code: String
)

// Checkout & Order DTOs
@JsonClass(generateAdapter = true)
data class CheckoutRequest(
    val shippingAddressId: String,
    val deliveryMethod: String,
    val paymentMethod: String
)

@JsonClass(generateAdapter = true)
data class CheckoutResponse(
    val orderId: String,
    val orderNumber: String,
    val paymentIntent: String? = null,
    val mpesaTransactionId: String? = null
)

@JsonClass(generateAdapter = true)
data class OrderDto(
    val id: String,
    val orderNumber: String,
    val status: String,
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double,
    val paymentMethod: String,
    val shippingAddress: AddressDto,
    val deliveryMethod: String,
    val trackingNumber: String? = null,
    val estimatedDelivery: Long? = null,
    val items: List<OrderItemDto>,
    val createdAt: Long,
    val timeline: List<OrderTimelineItem>? = null
)

@JsonClass(generateAdapter = true)
data class OrderItemDto(
    val id: String,
    val productId: String,
    val productName: String,
    val productImageUrl: String,
    val price: Double,
    val quantity: Int,
    val selectedSize: String
)

@JsonClass(generateAdapter = true)
data class OrderTimelineItem(
    val status: String,
    val timestamp: Long,
    val message: String
)

@JsonClass(generateAdapter = true)
data class OrdersResponse(
    val orders: List<OrderDto>,
    val page: Int = 1,
    val totalPages: Int = 1
)

// Address DTOs
@JsonClass(generateAdapter = true)
data class AddressDto(
    val id: String? = null,
    val name: String,
    val streetAddress: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val isDefault: Boolean = false
)

// Payment DTOs
@JsonClass(generateAdapter = true)
data class PaymentIntentRequest(
    val amount: Double,
    val currency: String = "USD",
    val orderId: String
)

@JsonClass(generateAdapter = true)
data class PaymentIntentResponse(
    val clientSecret: String,
    val publishableKey: String
)

@JsonClass(generateAdapter = true)
data class MpesaPaymentRequest(
    val phoneNumber: String,
    val amount: Double,
    val orderId: String
)

@JsonClass(generateAdapter = true)
data class MpesaPaymentResponse(
    val transactionId: String,
    val checkoutRequestId: String,
    val merchantRequestId: String
)

@JsonClass(generateAdapter = true)
data class PaymentStatusResponse(
    val status: String, // "pending", "success", "failed"
    val transactionId: String,
    val message: String? = null
)

// Admin DTOs
@JsonClass(generateAdapter = true)
data class UpdateOrderStatusRequest(
    val status: String,
    val trackingNumber: String? = null
)

@JsonClass(generateAdapter = true)
data class UpdateProfileRequest(
    val name: String,
    val phoneNumber: String? = null
)

// Common DTOs
@JsonClass(generateAdapter = true)
data class MessageResponse(
    val message: String,
    val success: Boolean = true
)
