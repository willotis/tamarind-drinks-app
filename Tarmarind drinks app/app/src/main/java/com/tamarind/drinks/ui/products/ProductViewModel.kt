package com.tamarind.drinks.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.local.entity.CartItemEntity
import com.tamarind.drinks.data.local.entity.ProductEntity
import com.tamarind.drinks.data.repository.CartRepository
import com.tamarind.drinks.data.repository.ProductRepository
import com.tamarind.drinks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    
    private val _productState = MutableStateFlow(ProductState())
    val productState = _productState.asStateFlow()
    
    private val _relatedProducts = MutableStateFlow<List<ProductEntity>>(emptyList())
    val relatedProducts = _relatedProducts.asStateFlow()
    
    private val _addToCartState = MutableStateFlow<Resource<Unit>?>(null)
    val addToCartState = _addToCartState.asStateFlow()
    
    fun loadProduct(productId: String) {
        viewModelScope.launch {
            productRepository.getProductById(productId).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _productState.value = _productState.value.copy(isLoading = true, error = null)
                    }
                    is Resource.Success -> {
                        _productState.value = _productState.value.copy(
                            product = resource.data,
                            isLoading = false,
                            error = null
                        )
                        // Load related products
                        resource.data?.let { loadRelatedProducts(it.id, it.category) }
                    }
                    is Resource.Error -> {
                        _productState.value = _productState.value.copy(
                            isLoading = false,
                            error = resource.message
                        )
                    }
                }
            }
        }
    }
    
    private fun loadRelatedProducts(productId: String, category: String) {
        viewModelScope.launch {
            productRepository.getRelatedProducts(productId, category).collect { resource ->
                if (resource is Resource.Success) {
                    _relatedProducts.value = resource.data ?: emptyList()
                }
            }
        }
    }
    
    fun addToCart(userId: String, product: ProductEntity, quantity: Int) {
        viewModelScope.launch {
            _addToCartState.value = Resource.Loading()
            
            val cartItem = CartItemEntity(
                id = UUID.randomUUID().toString(),
                userId = userId,
                productId = product.id,
                productName = product.name,
                quantity = quantity,
                price = product.price,
                imageUrl = product.imageUrl
            )
            
            val result = cartRepository.addToCart(cartItem)
            _addToCartState.value = result
        }
    }
    
    fun updateQuantity(newQuantity: Int) {
        _productState.value = _productState.value.copy(selectedQuantity = newQuantity)
    }
    
    fun clearAddToCartState() {
        _addToCartState.value = null
    }
}

data class ProductState(
    val product: ProductEntity? = null,
    val selectedQuantity: Int = 1,
    val isLoading: Boolean = false,
    val error: String? = null
)
