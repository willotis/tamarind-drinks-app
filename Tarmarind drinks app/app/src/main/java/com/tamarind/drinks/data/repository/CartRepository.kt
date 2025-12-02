package com.tamarind.drinks.data.repository

import com.tamarind.drinks.data.local.dao.CartDao
import com.tamarind.drinks.data.local.entity.CartItemEntity
import com.tamarind.drinks.data.mock.MockDataProvider
import com.tamarind.drinks.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    
    fun getCartItems(userId: String): Flow<Resource<List<CartItemEntity>>> = flow {
        emit(Resource.Loading())
        try {
            // Get from local database
            val cartItems = cartDao.getCartItems(userId)
            
            // If empty, populate with mock data for demo
            if (cartItems.isEmpty()) {
                val mockItems = MockDataProvider.getMockCartItems(userId)
                mockItems.forEach { cartDao.insertCartItem(it) }
                emit(Resource.Success(mockItems))
            } else {
                emit(Resource.Success(cartItems))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load cart"))
        }
    }
    
    suspend fun addToCart(item: CartItemEntity): Resource<Unit> {
        return try {
            // Check if item already exists
            val existing = cartDao.getCartItemByProductId(item.userId, item.productId)
            if (existing != null) {
                // Update quantity
                val updated = existing.copy(quantity = existing.quantity + item.quantity)
                cartDao.updateCartItem(updated)
            } else {
                cartDao.insertCartItem(item)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to add to cart")
        }
    }
    
    suspend fun updateQuantity(cartItemId: String, newQuantity: Int): Resource<Unit> {
        return try {
            if (newQuantity <= 0) {
                cartDao.deleteCartItem(cartItemId)
            } else {
                cartDao.updateQuantity(cartItemId, newQuantity)
            }
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update quantity")
        }
    }
    
    suspend fun removeFromCart(cartItemId: String): Resource<Unit> {
        return try {
            cartDao.deleteCartItem(cartItemId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to remove item")
        }
    }
    
    suspend fun clearCart(userId: String): Resource<Unit> {
        return try {
            cartDao.clearCart(userId)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to clear cart")
        }
    }
    
    suspend fun getCartCount(userId: String): Int {
        return try {
            cartDao.getCartItemCount(userId)
        } catch (e: Exception) {
            0
        }
    }
    
    fun calculateSubtotal(items: List<CartItemEntity>): Double {
        return items.sumOf { it.price * it.quantity }
    }
    
    fun calculateTax(subtotal: Double, taxRate: Double = 0.10): Double {
        return subtotal * taxRate
    }
    
    fun calculateDeliveryFee(subtotal: Double): Double {
        return when {
            subtotal >= 50.0 -> 0.0 // Free delivery
            subtotal >= 30.0 -> 3.0
            else -> 5.0
        }
    }
    
    fun calculateTotal(
        subtotal: Double,
        tax: Double,
        deliveryFee: Double,
        discount: Double = 0.0
    ): Double {
        return subtotal + tax + deliveryFee - discount
    }
    
    suspend fun applyCoupon(code: String, subtotal: Double): Resource<Double> {
        return try {
            // Mock coupon validation
            val discount = when (code.uppercase()) {
                "SAVE10" -> subtotal * 0.10
                "SAVE20" -> subtotal * 0.20
                "FIRST" -> 5.0
                "FREESHIP" -> 0.0 // Handled separately
                else -> return Resource.Error("Invalid coupon code")
            }
            Resource.Success(discount)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to apply coupon")
        }
    }
}
