package com.polsl.yerbapp.data.device

import android.content.Context
import com.polsl.yerbapp.R
import com.polsl.yerbapp.domain.models.reponse.sharedPreferences.CurrentUserInfo

class SharedPreferencesManager(private val context: Context) {
    private val prefsFile = "yerbapp"
    private val sharedPreferences =
        context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE)

    fun saveUserData(userId: Int, userRole: String, accessToken: String) {
        with(sharedPreferences.edit()) {
            putInt(context.getString(R.string.USER_ID_KEY), userId)
            putString(context.getString(R.string.USER_ROLE_KEY), userRole)
            putString(context.getString(R.string.ACCESS_TOKEN_KEY), accessToken)
            commit()
        }
    }

    fun getUserData(): CurrentUserInfo =
        CurrentUserInfo(
            sharedPreferences.getInt(context.getString(R.string.USER_ID_KEY), -1),
            sharedPreferences.getString(context.getString(R.string.USER_ROLE_KEY), "") ?: "",
            sharedPreferences.getString(context.getString(R.string.ACCESS_TOKEN_KEY), "") ?: ""
        )
}