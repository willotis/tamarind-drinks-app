package com.tamarind.drinks.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.local.entity.CartItemEntity
import com.tamarind.drinks.data.repository.CartRepository
import com.tamarind.drinks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _cartState = MutableStateFlow(CartState())
    val cartState = _cartState.asStateFlow()
    
    fun loadCart(userId: String) {
        viewModelScope.launch {
            cartRepository.getCartItems(userId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _cartState.value = _cartState.value.copy(isLoading = true, error = null)
                    }
                    is Resource.Success -> {
                        val items = resource.data ?: emptyList()
                        updateCartTotals(items)
                    }
                    is Resource.Error -> {
                        _cartState.value = _cartState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }
    
    fun updateQuantity(cartItemId: String, userId: String, newQuantity: Int) {
        viewModelScope.launch {
            val result = cartRepository.updateQuantity(cartItemId, newQuantity)
            if (result is Resource.Success) {
                loadCart(userId) // Reload cart
            }
        }
    }
    
    fun removeItem(cartItemId: String, userId: String) {
        viewModelScope.launch {
            val result = cartRepository.removeFromCart(cartItemId)
            if (result is Resource.Success) {
                loadCart(userId) // Reload cart
            }
        }
    }
    
    fun applyCoupon(code: String) {
        viewModelScope.launch {
            val result = cartRepository.applyCoupon(code, _cartState.value.subtotal)
            when (result) {
                is Resource.Success -> {
                    _cartState.value = _cartState.value.copy(
                        couponCode = code,
                        discount = result.data ?: 0.0,
                        couponError = null
                    )
                    updateTotals()
                }
                is Resource.Error -> {
                    _cartState.value = _cartState.value.copy(
                        couponError = result.message,
                        couponCode = "",
                        discount = 0.0
                    )
                }
                else -> {}
            }
        }
    }
    
    fun removeCoupon() {
        _cartState.value = _cartState.value.copy(
            couponCode = "",
            discount = 0.0,
            couponError = null
        )
        updateTotals()
    }
    
    private fun updateCartTotals(items: List<CartItemEntity>) {
        val subtotal = cartRepository.calculateSubtotal(items)
        val tax = cartRepository.calculateTax(subtotal)
        val deliveryFee = cartRepository.calculateDeliveryFee(subtotal)
        
        _cartState.value = _cartState.value.copy(
            items = items,
            subtotal = subtotal,
            tax = tax,
            deliveryFee = deliveryFee,
            isLoading = false,
            error = null
        )
        updateTotals()
    }
    
    private fun updateTotals() {
        val total = cartRepository.calculateTotal(
            _cartState.value.subtotal,
            _cartState.value.tax,
            _cartState.value.deliveryFee,
            _cartState.value.discount
        )
        _cartState.value = _cartState.value.copy(total = total)
    }
}

data class CartState(
    val items: List<CartItemEntity> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val discount: Double = 0.0,
    val total: Double = 0.0,
    val couponCode: String = "",
    val couponError: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
