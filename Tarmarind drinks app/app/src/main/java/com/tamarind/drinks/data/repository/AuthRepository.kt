package com.tamarind.drinks.data.repository

import com.tamarind.drinks.data.local.SecurePreferencesManager
import com.tamarind.drinks.data.local.dao.UserDao
import com.tamarind.drinks.data.local.entity.UserEntity
import com.tamarind.drinks.data.remote.TamarindApiService
import com.tamarind.drinks.data.remote.dto.*
import com.tamarind.drinks.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: TamarindApiService,
    private val securePrefs: SecurePreferencesManager,
    private val userDao: UserDao
) {
    
    suspend fun login(email: String, password: String): Flow<Resource<UserDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                
                // Save auth token
                securePrefs.saveAuthToken(loginResponse.token)
                securePrefs.saveUserId(loginResponse.user.id)
                securePrefs.saveUserRole(loginResponse.user.role)
                
                // Cache user locally
                userDao.insertUser(loginResponse.user.toEntity())
                
                emit(Resource.Success(loginResponse.user))
            } else {
                emit(Resource.Error(response.message() ?: "Login failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Network error"))
        }
    }
    
    suspend fun register(
        email: String,
        password: String,
        name: String,
        phoneNumber: String?
    ): Flow<Resource<UserDto>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.register(
                RegisterRequest(email, password, name, phoneNumber)
            )
            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!
                
                securePrefs.saveAuthToken(loginResponse.token)
                securePrefs.saveUserId(loginResponse.user.id)
                securePrefs.saveUserRole(loginResponse.user.role)
                
                userDao.insertUser(loginResponse.user.toEntity())
                
                emit(Resource.Success(loginResponse.user))
            } else {
                emit(Resource.Error(response.message() ?: "Registration failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Network error"))
        }
    }
    
    suspend fun resetPassword(email: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.resetPassword(ResetPasswordRequest(email))
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.Success(response.body()!!.message))
            } else {
                emit(Resource.Error(response.message() ?: "Reset failed"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Network error"))
        }
    }
    
    fun logout() {
        securePrefs.clearAll()
    }
    
    fun isLoggedIn(): Boolean {
        return securePrefs.isLoggedIn()
    }
    
    fun getCurrentUserId(): String? {
        return securePrefs.getUserId()
    }
    
    fun isAdmin(): Boolean {
        return securePrefs.isAdmin()
    }
    
    private fun UserDto.toEntity() = UserEntity(
        id = id,
        email = email,
        name = name,
        phoneNumber = phoneNumber,
        role = role,
        profileImageUrl = profileImageUrl
    )
}
