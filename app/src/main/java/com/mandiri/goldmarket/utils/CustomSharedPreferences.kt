package com.mandiri.goldmarket.utils

import android.content.Context
import android.content.SharedPreferences

object CustomSharedPreferences {
    val USER_ID = "USER_ID"
    val USER_PASSWORD = "PASSWORD"

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun setUsername(context: Context, name: String, username: String) {
        customPreference(context, name).edit()?.putString("username", username)?.apply()
    }

    var SharedPreferences.Username
        get() = getString("username", "user")
        set(value) = edit().putString("username", value).apply()



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