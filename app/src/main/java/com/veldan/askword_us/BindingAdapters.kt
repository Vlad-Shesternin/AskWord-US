package com.veldan.askword_us

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.databinding.BindingAdapter


@BindingAdapter("focusAfterClickKeyDone")
fun EditText.focusAfterClickKeyDone(use: Boolean) {
    this.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            v.isFocusable = false
            v.isFocusableInTouchMode = true
        }
        return@setOnEditorActionListener false
    }
}

@BindingAdapter("clickHideKeyboard")
fun View.clickHideKeyboard(use: Boolean) {
    this.setOnClickListener {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }
}

@BindingAdapter("defaultFocusAndKeyboard")
fun View.defaultFocusAndKeyboard(use: Boolean) {
    this.requestFocus()
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this,
        SHOW_IMPLICIT) //HIDE KEYBOARD //  imm.hideSoftInputFromWindow(this.windowToken, 0)
}