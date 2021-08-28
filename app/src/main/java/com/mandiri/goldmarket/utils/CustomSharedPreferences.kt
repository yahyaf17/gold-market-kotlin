package com.mandiri.goldmarket.utils

import android.content.SharedPreferences

class CustomSharedPreferences(val sharedPreferences: SharedPreferences) {

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun setValue(key: Key, value: Any) {
        if (value is String) {
            sharedPreferences.edit().putString(key.toString(), value).apply()
        }
    }

    fun retrieveValue(key: Key): String? {
        return sharedPreferences.getString(key.toString(), null)
    }

    enum class Key {
        TOKEN, CUSTOMER_ID
    }
}