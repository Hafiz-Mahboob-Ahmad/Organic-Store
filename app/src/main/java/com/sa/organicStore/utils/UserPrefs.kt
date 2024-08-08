package com.sa.organicStore.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.sa.organicStore.database.entities.UserEntity

class UserPrefs(val context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("user_prefs", MODE_PRIVATE)

    fun setUser(user: UserEntity) {
        Log.d("USER", "fun setUser: userId = ${user.userId}")
        with(sharedPref.edit())
        {
            val userJson = Gson().toJson(user)
            putString("user", userJson)
            //putString("user_email", user.email)
            apply()
        }
    }

    fun getUser(): UserEntity? {
        val userJson = sharedPref.getString("user", null)
        return if (userJson == null) {
            null
        } else {
            val user = Gson().fromJson(userJson, UserEntity::class.java)
            Log.d("USER", "fun getUser: userId = ${user.userId}")
            user
        }
    }
}