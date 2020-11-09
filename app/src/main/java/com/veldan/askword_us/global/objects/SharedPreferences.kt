package com.veldan.askword_us.global.objects

import android.content.Context
import androidx.fragment.app.Fragment

object SharedPreferences {
    private const val APP_PREFERENCES = "app"
    const val USER_NAME = "user_name"
    const val USER_SURNAME = "user_surname"

    fun initSharedPref(fragment: Fragment) =
        fragment.requireContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    fun getEdit(fragment: Fragment) = initSharedPref(fragment).edit()

}