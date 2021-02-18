package com.veldan.askword_us.global.general_classes

import android.content.Context
import androidx.fragment.app.Fragment

class SharedPreferences(private val fragment: Fragment) {
    companion object {
        // Names files SharedPreferences
        const val AUTH = "authentication"
        const val STUDY_FORMAT = "study_format"

        // Authentication
        const val USER_NAME = "user_name"
        const val USER_SURNAME = "user_surname"

        // StudyFormat
        const val QUESTION_FORMAT_WORD = "question_format_word"
        const val QUESTION_FORMAT_PHRASE = "question_format_phrase"
        const val ANSWER_FORMAT_FILL = "answer_format_fill"
        const val ANSWER_FORMAT_SELECTION = "answer_format_selection"
        const val ANSWER_FORMAT_ADDITIONAL = "answer_format_additional"
    }

    fun initSharedPref(nameSharedPref: String) =
        fragment.requireContext().getSharedPreferences(nameSharedPref, Context.MODE_PRIVATE)
}