package com.veldan.askword_us.global.objects

import android.content.Context
import com.veldan.askword_us.global.isEmail
import com.veldan.askword_us.global.toast

object Verification {
    //==============================
    //      Email
    //==============================
    fun verifyEmail(context: Context, email: String) = when {
        //Consistency is important
        email.isEmpty() -> {
            "Введiть електронну адресу".toast(context)
            false
        }
        !(email.isEmail()) -> {
            "Невiрна електронна адреса".toast(context)
            false
        }
        else -> true
    }

    //==============================
    //      Password
    //==============================
    fun verifyPassword(context: Context, password: String) = when {
        password.isEmpty() -> {
            "Введiть пароль".toast(context)
            false
        }

        password.length < 6 -> {
            "Короткий пароль".toast(context)
            false
        }
        else -> true
    }

    //==============================
    //      EmailPassword
    //==============================
    fun verifyEmailPassword(context: Context, email: String, password: String) = when {
        verifyEmail(context, email) && verifyPassword(context, password) -> true
        else -> false
    }

    //==============================
    //      NameSurname
    //==============================
    fun verifyNameSurname(context: Context, name: String, surname: String) = when {
        //Consistency is important
        name.isEmpty() && surname.isEmpty() -> {
            "Введiть Iм`я i Прiзвище".toast(context)
            false
        }
        name.isEmpty() -> {
            "Введiть Iм`я ".toast(context)
            false
        }
        surname.isEmpty() -> {
            "Введiть Прiзвище".toast(context)
            false
        }
        else -> true
    }
}