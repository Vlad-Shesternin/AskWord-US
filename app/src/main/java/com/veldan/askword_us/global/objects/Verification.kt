package com.veldan.askword_us.global.objects

import android.content.Context
import com.veldan.askword_us.global.isEmail
import com.veldan.askword_us.global.toast

object Verification {
    fun verify(context: Context, email: String, password: String) = when {
        //Consistency is important
        email.isEmpty() && password.isEmpty() -> {
            "Введiть електронну адресу i пароль".toast(context)
            false
        }
        email.isEmpty() -> {
            "Введiть електронну адресу".toast(context)
            false
        }
        password.isEmpty() -> {
            "Введiть пароль".toast(context)
            false
        }
        !(email.isEmail()) -> {
            "Невiрна електронна адреса".toast(context)
            false
        }
        password.length < 6 -> {
            "Короткий пароль".toast(context)
            false
        }
        else -> true
    }
}