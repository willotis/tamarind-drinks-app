package com.tamarind.drinks.data.repository

import com.tamarind.drinks.data.local.dao.OrderDao
import com.tamarind.drinks.data.local.entity.AddressEntity
import com.tamarind.drinks.data.local.entity.CartItemEntity
import com.tamarind.drinks.data.local.entity.OrderEntity
import com.tamarind.drinks.data.mock.MockDataProvider
import com.tamarind.drinks.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderDao: OrderDao
) {
    
    fun getUserOrders(userId: String): Flow<Resource<List<OrderEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val orders = orderDao.getUserOrders(userId)
            
            // If empty, populate with mock data
            if (orders.isEmpty()) {
                val mockOrders = MockDataProvider.getMockOrders(userId)
                mockOrders.forEach { orderDao.insertOrder(it) }
                emit(Resource.Success(mockOrders))
            } else {
                emit(Resource.Success(orders.sortedByDescending { it.createdAt }))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load orders"))
        }
    }
    
    fun getOrderById(orderId: String): Flow<Resource<OrderEntity>> = flow {
        emit(Resource.Loading())
        try {
            val order = orderDao.getOrderById(orderId)
            if (order != null) {
                emit(Resource.Success(order))
            } else {
                emit(Resource.Error("Order not found"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load order"))
        }
    }
    
    fun getOrdersByStatus(userId: String, status: String): Flow<Resource<List<OrderEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val orders = orderDao.getOrdersByStatus(userId, status)
            emit(Resource.Success(orders.sortedByDescending { it.createdAt }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load orders"))
        }
    }
    
    suspend fun createOrder(
        userId: String,
        items: List<CartItemEntity>,
        subtotal: Double,
        tax: Double,
        deliveryFee: Double,
        discount: Double,
        total: Double,
        deliveryAddress: AddressEntity,
        paymentMethod: String
    ): Resource<OrderEntity> {
        return try {
            val orderId = UUID.randomUUID().toString()
            val orderNumber = generateOrderNumber()
            
            val order = OrderEntity(
                id = orderId,
                userId = userId,
                orderNumber = orderNumber,
                status = "pending",
                items = items,
                subtotal = subtotal,
                tax = tax,
                deliveryFee = deliveryFee,
                discount = discount,
                total = total,
                deliveryAddress = deliveryAddress,
                paymentMethod = paymentMethod,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            
            orderDao.insertOrder(order)
            Resource.Success(order)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create order")
        }
    }
    
    suspend fun updateOrderStatus(orderId: String, newStatus: String): Resource<Unit> {
        return try {
            orderDao.updateOrderStatus(orderId, newStatus, System.currentTimeMillis())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update order status")
        }
    }
    
    suspend fun cancelOrder(orderId: String): Resource<Unit> {
        return try {
            val order = orderDao.getOrderById(orderId)
            if (order == null) {
                return Resource.Error("Order not found")
            }
            
            // Only allow cancellation of pending or processing orders
            if (order.status in listOf("pending", "processing")) {
                orderDao.updateOrderStatus(orderId, "cancelled", System.currentTimeMillis())
                Resource.Success(Unit)
            } else {
                Resource.Error("Cannot cancel order with status: ${order.status}")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to cancel order")
        }
    }
    
    suspend fun reorder(orderId: String, cartRepository: CartRepository): Resource<Unit> {
        return try {
            val order = orderDao.getOrderById(orderId)
            if (order == null) {
                return Resource.Error("Order not found")
            }
            
            // Add all items from the order back to cart
            order.items.forEach { item ->
                val cartItem = item.copy(id = UUID.randomUUID().toString())
                cartRepository.addToCart(cartItem)
            }
            
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to reorder")
        }
    }
    
    // Admin functions
    fun getAllOrders(): Flow<Resource<List<OrderEntity>>> = flow {
        emit(Resource.Loading())
        try {
            val orders = orderDao.getAllOrders()
            emit(Resource.Success(orders.sortedByDescending { it.createdAt }))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to load orders"))
        }
    }
    
    suspend fun getOrderStats(): Map<String, Int> {
        return try {
            val orders = orderDao.getAllOrders()
            mapOf(
                "total" to orders.size,
                "pending" to orders.count { it.status == "pending" },
                "processing" to orders.count { it.status == "processing" },
                "delivered" to orders.count { it.status == "delivered" },
                "cancelled" to orders.count { it.status == "cancelled" }
            )
        } catch (e: Exception) {
            emptyMap()
        }
    }
    
    private fun generateOrderNumber(): String {
        val timestamp = System.currentTimeMillis()
        val random = (1000..9999).random()
        return "TDR$timestamp$random".takeLast(13)
    }
}
