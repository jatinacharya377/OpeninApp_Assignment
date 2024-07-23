package com.assignment.openinapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.assignment.openinapp.MyApplication

object PrefsManager {
    const val USER_TOKEN = "user_auth_token"
    private const val PREF_NAME = "open_in_app_pref"
    private val preference: SharedPreferences = MyApplication.INSTANCE.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun getString(key: String) = preference.getString(key, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
}