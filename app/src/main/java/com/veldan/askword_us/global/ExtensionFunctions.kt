package com.veldan.askword_us.global

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.veldan.askword_us.R

//==============================
//          ViewGroup
//==============================
fun ViewGroup.inflate(id: Int): View {
    return LayoutInflater.from(this.context).inflate(id, this, false)
}

//==============================
//          String
//==============================
fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.toast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

