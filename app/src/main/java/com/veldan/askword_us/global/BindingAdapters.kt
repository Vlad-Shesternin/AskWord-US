package com.veldan.askword_us.global

import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.veldan.askword_us.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ==============================
//         Focus click Done
// ==============================
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

// ==============================
//         Hide Keyboard
// ==============================
@BindingAdapter("clickHideKeyboard")
fun View.clickHideKeyboard(use: Boolean) {
    this.setOnClickListener {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        clearFocus()
    }
}

// ==============================
//         Focus and Keyboard
// ==============================
@BindingAdapter("defaultFocusAndKeyboard")
fun View.defaultFocusAndKeyboard(use: Boolean) {
    this.also { view ->
        view.requestFocus()
        CoroutineScope(Dispatchers.Main).launch {
            delay(100)
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(
                view,
                SHOW_IMPLICIT
            )
        }
    }
}