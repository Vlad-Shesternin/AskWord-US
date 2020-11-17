package com.veldan.askword_us.global.general_classes

import android.content.Context
import androidx.fragment.app.Fragment

class SharedPreferences(private val fragment: Fragment) {
    companion object {
        private const val APP_PREFERENCES = "app"
        const val USER_NAME = "user_name"
        const val USER_SURNAME = "user_surname"
    }

    fun initSharedPref() =
        fragment.requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun getEditor() = initSharedPref().edit()

    fun clearSharedPref() = getEditor().clear().apply()

}