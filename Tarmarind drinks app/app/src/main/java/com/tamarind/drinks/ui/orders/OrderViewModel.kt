package com.tamarind.drinks.ui.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.local.entity.OrderEntity
import com.tamarind.drinks.data.repository.OrderRepository
import com.tamarind.drinks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {
    
    private val _ordersState = MutableStateFlow(OrdersState())
    val ordersState = _ordersState.asStateFlow()
    
    private val _orderDetailState = MutableStateFlow<Resource<OrderEntity>?>(null)
    val orderDetailState = _orderDetailState.asStateFlow()
    
    fun loadOrders(userId: String) {
        viewModelScope.launch {
            orderRepository.getUserOrders(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _ordersState.value = _ordersState.value.copy(isLoading = true, error = null)
                    }
                    is Resource.Success -> {
                        val orders = resource.data ?: emptyList()
                        _ordersState.value = _ordersState.value.copy(
                            allOrders = orders,
                            filteredOrders = filterOrders(orders, _ordersState.value.selectedFilter),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _ordersState.value = _ordersState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }
    
    fun filterOrders(filter: OrderFilter) {
        _ordersState.value = _ordersState.value.copy(
            selectedFilter = filter,
            filteredOrders = filterOrders(_ordersState.value.allOrders, filter)
        )
    }
    
    private fun filterOrders(orders: List<OrderEntity>, filter: OrderFilter): List<OrderEntity> {
        return when (filter) {
            OrderFilter.ALL -> orders
            OrderFilter.ACTIVE -> orders.filter { it.status in listOf("pending", "processing") }
            OrderFilter.COMPLETED -> orders.filter { it.status == "delivered" }
            OrderFilter.CANCELLED -> orders.filter { it.status == "cancelled" }
        }
    }
    
    fun loadOrderDetail(orderId: String) {
        viewModelScope.launch {
            orderRepository.getOrderById(orderId).collect { resource ->
                _orderDetailState.value = resource
            }
        }
    }
    
    fun cancelOrder(orderId: String, userId: String) {
        viewModelScope.launch {
            val result = orderRepository.cancelOrder(orderId)
            if (result is Resource.Success) {
                loadOrderDetail(orderId) // Reload detail
                loadOrders(userId) // Reload list
            }
        }
    }
}

data class OrdersState(
    val allOrders: List<OrderEntity> = emptyList(),
    val filteredOrders: List<OrderEntity> = emptyList(),
    val selectedFilter: OrderFilter = OrderFilter.ALL,
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class OrderFilter(val displayName: String) {
    ALL("All Orders"),
    ACTIVE("Active"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}
