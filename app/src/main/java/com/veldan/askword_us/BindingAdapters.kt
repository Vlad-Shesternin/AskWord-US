package com.veldan.askword_us

import android.text.InputType
import android.view.inputmethod.EditorInfo
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

@BindingAdapter("multilineDone")
fun EditText.multilineDone(use: Boolean) {
    this.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)
    this.imeOptions = EditorInfo.IME_ACTION_DONE

    this.setOnEditorActionListener { v, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            v.isFocusable = false
            v.isFocusableInTouchMode = true
        }
        return@setOnEditorActionListener false
    }
}

@BindingAdapter("defaultFocus")
fun EditText.defaultFocus(isFocus: Boolean) {
    this.isFocusableInTouchMode= (true)
    this.isFocusable = (true)

}