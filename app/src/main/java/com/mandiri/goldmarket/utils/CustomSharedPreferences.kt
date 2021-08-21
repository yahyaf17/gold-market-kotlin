package com.mandiri.goldmarket.utils

import android.content.Context
import android.content.SharedPreferences

class CustomSharedPreferences(context: Context) {

    val credentialsPref: SharedPreferences = context.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)

    fun clearAll() {
        credentialsPref.edit().clear().apply()
    }

    fun setValue(key: Key, value: Any) {
        if (value is String) {
            credentialsPref.edit().putString(key.toString(), value).apply()
        }
        if (value is Int) {
            credentialsPref.edit().putInt(key.toString(), value).apply()
        }
    }

    fun retrieveString(key: Key): String? {
        return credentialsPref.getString(key.toString(), null)
    }

    fun retrieveInt(key: Key): Int {
        return credentialsPref.getInt(key.toString(), 0)
    }

    enum class Key {
        TOKEN, USER_ID, CUSTOMER_ID
    }
}