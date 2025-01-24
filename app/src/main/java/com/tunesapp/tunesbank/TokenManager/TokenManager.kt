package com.tunesapp.tunesbank.activities.token

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun hasToken(): Boolean {
        return prefs.contains("token")
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun deleteToken() {
        prefs.edit().remove("token").apply()
    }

    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }
}



