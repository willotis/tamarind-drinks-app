package com.tamarind.drinks.ui.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.local.entity.AddressEntity
import com.tamarind.drinks.data.local.entity.CartItemEntity
import com.tamarind.drinks.data.mock.MockDataProvider
import com.tamarind.drinks.data.repository.CartRepository
import com.tamarind.drinks.data.repository.OrderRepository
import com.tamarind.drinks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {
    
    private val _checkoutState = MutableStateFlow(CheckoutState())
    val checkoutState = _checkoutState.asStateFlow()
    
    fun loadCheckoutData(userId: String, cartItems: List<CartItemEntity>, pricing: PricingInfo) {
        viewModelScope.launch {
            // Load addresses
            val addresses = MockDataProvider.getMockAddresses(userId)
            _checkoutState.value = _checkoutState.value.copy(
                cartItems = cartItems,
                subtotal = pricing.subtotal,
                tax = pricing.tax,
                deliveryFee = pricing.deliveryFee,
                discount = pricing.discount,
                total = pricing.total,
                availableAddresses = addresses,
                selectedAddress = addresses.firstOrNull { it.isDefault }
            )
        }
    }
    
    fun selectAddress(address: AddressEntity) {
        _checkoutState.value = _checkoutState.value.copy(selectedAddress = address)
    }
    
    fun selectPaymentMethod(method: PaymentMethod) {
        _checkoutState.value = _checkoutState.value.copy(selectedPaymentMethod = method)
    }
    
    fun placeOrder(userId: String) {
        viewModelScope.launch {
            val state = _checkoutState.value

            if (state.selectedAddress == null) {
                _checkoutState.value = _checkoutState.value.copy(
                    error = "Please select a delivery address"
                )
                return@launch
            }
            
            if (state.selectedPaymentMethod == null) {
                _checkoutState.value = _checkoutState.value.copy(
                    error = "Please select a payment method"
                )
                return@launch
            }
            
            _checkoutState.value = _checkoutState.value.copy(isPlacingOrder = true, error = null)
            
            val result = orderRepository.createOrder(
                userId = userId,
                items = state.cartItems,
                subtotal = state.subtotal,
                tax = state.tax,
                deliveryFee = state.deliveryFee,
                discount = state.discount,
                total = state.total,
                deliveryAddress = state.selectedAddress!!,
                paymentMethod = state.selectedPaymentMethod!!.displayName
            )
            
            when (result) {
                is Resource.Success -> {
                    // Clear cart after successful order
                    cartRepository.clearCart(userId)
                    _checkoutState.value = _checkoutState.value.copy(
                        isPlacingOrder = false,
                        orderPlaced = result.data
                    )
                }
                is Resource.Error -> {
                    _checkoutState.value = _checkoutState.value.copy(
                        isPlacingOrder = false,
                        error = result.message
                    )
                }
                else -> {}
            }
        }
    }
}

data class CheckoutState(
    val cartItems: List<CartItemEntity> = emptyList(),
    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val discount: Double = 0.0,
    val total: Double = 0.0,
    val availableAddresses: List<AddressEntity> = emptyList(),
    val selectedAddress: AddressEntity? = null,
    val selectedPaymentMethod: PaymentMethod? = null,
    val isPlacingOrder: Boolean = false,
    val orderPlaced: com.tamarind.drinks.data.local.entity.OrderEntity? = null,
    val error: String? = null
)

data class PricingInfo(
    val subtotal: Double,
    val tax: Double,
    val deliveryFee: Double,
    val discount: Double,
    val total: Double
)

enum class PaymentMethod(val displayName: String, val icon: String) {
    CARD("Credit/Debit Card", "💳"),
    GOOGLE_PAY("Google Pay", "🌐"),
    PAYPAL("PayPal", "💰"),
    MPESA("M-PESA", "📱"),
    CASH("Cash on Delivery", "💵")
}
