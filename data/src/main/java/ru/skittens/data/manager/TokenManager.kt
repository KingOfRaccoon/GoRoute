package ru.skittens.data.manager

import android.content.Context

class TokenManager(private val context: Context) {
    private val nameSharedPreferences = "userdata"
    private val tokenTag = "token"
    fun getToken() = context.getSharedPreferences(nameSharedPreferences, Context.MODE_PRIVATE)
        .getString(tokenTag, null)

    fun updateToken(token: String) =
        context.getSharedPreferences(nameSharedPreferences, Context.MODE_PRIVATE).edit()
            .putString(tokenTag, token).commit()
}