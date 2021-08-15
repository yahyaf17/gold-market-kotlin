package com.mandiri.goldmarket.utils

import android.content.Context
import android.content.SharedPreferences

object CustomSharedPreferences {

    fun credentialsPref(context: Context): SharedPreferences = context.getSharedPreferences("CREDENTIALS", Context.MODE_PRIVATE)

    fun setUsername(context: Context, username: String) {
        credentialsPref(context).edit()?.putString("username", username)?.apply()
    }

    val SharedPreferences.ClearAll
        get() = edit().clear().apply()

    var SharedPreferences.Username
        get() = getString("username", "tes")
        set(value) = edit().putString("username", value).apply()

    var SharedPreferences.CustomerId
        get() = getInt("user_id", 1)
        set(value) = edit().putInt("user_id", value).apply()

    var SharedPreferences.PocketId
        get() = getInt("pocket_id", 0)
        set(value) = edit().putInt("pocket_id", value).apply()

//    var CustomSharedPreferences.userId
//        get() = getInt(USER_ID, 0)
//        set(value) {
//            editMe {
//                it.putString(USER_ID, value)
//            }
//        }
//
//    var CustomSharedPreferences.password
//        get() = getString(USER_PASSWORD, "")
//        set(value) {
//            editMe {
//                it.putString(USER_PASSWORD, value)
//            }
//        }
//
//    var CustomSharedPreferences.clearValues
//        get() = { }
//        set(value) {
//            editMe {
//                it.clear()
//            }
//        }
}