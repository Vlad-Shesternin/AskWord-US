package com.veldan.askword_us.study

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction

object StudyAnimator {

    // MotionLayout
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val show_list_phrases = R.id.show_list_phrases
    const val show_detailed_info_word = R.id.show_detailed_info_word
    const val show_detailed_info_phrase = R.id.show_detailed_info_phrase
    const val show_count_selected_words = R.id.show_count_selected_words
    const val show_count_selected_phrases = R.id.show_count_selected_phrases

    // ==============================
    //    Transitions TO_END
    // ==============================
    fun start_To_ShowListPhrases() {
        Animator2.transition(motion,
            start, show_list_phrases,
            direction = Direction.TO_END)
    }

    fun start_To_ShowDetailedInfoWord() {
        Animator2.transition(motion,
            start, show_detailed_info_word,
            direction = Direction.TO_END)
    }

    fun start_To_ShowCountSelectedWords() {
        Animator2.transition(motion,
            start, show_count_selected_words,
            direction = Direction.TO_END)
    }

    fun showListPhrases_To_ShowCountSelectedPhrases() {
        Animator2.transition(motion,
            show_list_phrases, show_count_selected_phrases,
            direction = Direction.TO_END)
    }

    fun showListPhrases_To_ShowDetailedInfoPhrase() {
        Animator2.transition(motion,
            show_list_phrases, show_detailed_info_phrase,
            direction = Direction.TO_END)
    }

    // ==============================
    //    Transitions TO_START
    // ==============================
    fun showListPhrases_To_Start() {
        Animator2.transition(motion,
            show_list_phrases, start,
            direction = Direction.TO_START)
    }

    fun showDetailedInfoWord_To_Start() {
        Animator2.transition(motion,
            show_detailed_info_word, start,
            direction = Direction.TO_START)
    }

    fun showDetailedInfoPhrase_To_ShowListPhrases() {
        Animator2.transition(motion,
            show_detailed_info_phrase, show_list_phrases,
            direction = Direction.TO_START)
    }
}