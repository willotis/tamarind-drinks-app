package com.tamarind.drinks.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.local.entity.ProductEntity
import com.tamarind.drinks.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val featuredProducts: List<ProductEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(isLoading = true)
            
            try {
                productRepository.getFeaturedProducts().collect { products ->
                    _homeState.value = _homeState.value.copy(
                        featuredProducts = products,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _homeState.value = _homeState.value.copy(
                    isLoading = false,
                    error = e.localizedMessage
                )
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _homeState.value = _homeState.value.copy(isLoading = true)
            productRepository.refreshProducts()
            loadProducts()
        }
    }
}
