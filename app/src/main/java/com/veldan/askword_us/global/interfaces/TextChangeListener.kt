package com.veldan.askword_us.global.interfaces

import android.text.Editable
import android.text.TextWatcher

interface TextChangeListener : TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {}
}