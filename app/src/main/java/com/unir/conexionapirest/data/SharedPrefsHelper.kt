package com.unir.conexionapirest.data

import android.content.Context
import com.google.gson.Gson
import com.unir.conexionapirest.data.model.UserModel

class SharedPrefsHelper(context: Context) {

    private val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveUser(user: UserModel) {
        val json = gson.toJson(user)
        prefs.edit().putString("user", json).apply()
    }

    fun getUser(): UserModel? {
        val json = prefs.getString("user", null) ?: return null
        return gson.fromJson(json, UserModel::class.java)
    }

    fun clearUser() {
        prefs.edit().remove("user").apply()
    }
}
