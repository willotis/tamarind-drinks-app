package com.tamarind.drinks.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamarind.drinks.data.remote.dto.UserDto
import com.tamarind.drinks.data.repository.AuthRepository
import com.tamarind.drinks.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val isLoading: Boolean = false,
    val user: UserDto? = null,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        _authState.value = _authState.value.copy(
            isLoggedIn = authRepository.isLoggedIn()
        )
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = AuthState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _authState.value = AuthState(
                            user = result.data,
                            isLoggedIn = true
                        )
                    }
                    is Resource.Error -> {
                        _authState.value = AuthState(
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun register(email: String, password: String, name: String, phoneNumber: String?) {
        viewModelScope.launch {
            authRepository.register(email, password, name, phoneNumber).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = AuthState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _authState.value = AuthState(
                            user = result.data,
                            isLoggedIn = true
                        )
                    }
                    is Resource.Error -> {
                        _authState.value = AuthState(
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun resetPassword(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.resetPassword(email).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _authState.value = AuthState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _authState.value = AuthState()
                        onSuccess()
                    }
                    is Resource.Error -> {
                        _authState.value = AuthState(
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _authState.value = AuthState(isLoggedIn = false)
    }

    fun clearError() {
        _authState.value = _authState.value.copy(error = null)
    }
}
