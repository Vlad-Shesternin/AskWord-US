package com.veldan.askword_us.global

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


//==============================
//          ViewGroup
//==============================
fun ViewGroup.inflate(id: Int): View {
    return LayoutInflater.from(this.context).inflate(id, this, false)
}

