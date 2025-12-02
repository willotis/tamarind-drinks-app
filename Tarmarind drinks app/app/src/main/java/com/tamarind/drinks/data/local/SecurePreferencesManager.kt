package com.tamarind.drinks.data.local

import android.content.SharedPreferences
import javax.inject.Inject

class SecurePreferencesManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN, token).apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    fun clearAuthToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }

    fun saveUserId(userId: String) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    fun saveUserRole(role: String) {
        sharedPreferences.edit().putString(KEY_USER_ROLE, role).apply()
    }

    fun getUserRole(): String? {
        return sharedPreferences.getString(KEY_USER_ROLE, null)
    }

    fun isAdmin(): Boolean {
        return getUserRole() == "admin"
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_AUTH_TOKEN = "auth_token"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_ROLE = "user_role"
    }
}
