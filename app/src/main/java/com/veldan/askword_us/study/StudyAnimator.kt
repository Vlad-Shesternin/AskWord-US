package com.veldan.askword_us.study

import androidx.constraintlayout.motion.widget.MotionLayout
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import java.util.*

object StudyAnimator {


    // ==========================================================================================
    //    My Perfect Work
    // ==========================================================================================


    var start_X_ShowCountSelectedWords: Pair<Int, Direction> = 1000 to Direction.TO_END
        set(value) {
            field = value
            Animator2.transition(
                motion,
                start to show_count_selected_words,
                value.first,
                value.second
            )
        }


    // ==========================================================================================
    //    My Bad Work
    // ==========================================================================================


    fun start_X_ShowCountSelectedWords(
        duration: Int = 1000,
        direction: Direction = Direction.TO_END,
    ) {
        Animator2.transition(
            motion,
            show_list_phrases to show_detailed_info_phrase,
            duration,
            direction
        )
    }


    fun call() {
        // My Perfect Work
        start_X_ShowCountSelectedWords = 1000 to Direction.TO_END
        // My Bad Work
        start_X_ShowCountSelectedWords(1000, Direction.TO_END)
    }



































    // Components
    lateinit var motion: MotionLayout

    // States
    const val start = R.id.start
    const val show_list_phrases = R.id.show_list_phrases
    const val show_detailed_info_word = R.id.show_detailed_info_word
    const val show_detailed_info_phrase = R.id.show_detailed_info_phrase
    const val show_count_selected_words = R.id.show_count_selected_words
    const val show_count_selected_phrases = R.id.show_count_selected_phrases

    // ==============================
    //    Transitions
    // ==============================
    fun start_X_ShowListPhrases(
        duration: Int = 1000,
        direction: Direction = Direction.TO_END,
    ) {
        Animator2.transition(
            motion,
            start to show_list_phrases,
            duration,
            direction
        )
    }

    fun start_X_ShowDetailedInfoWord(
        duration: Int = 1000,
        direction: Direction = Direction.TO_END,
    ) {
        Animator2.transition(
            motion,
            start to show_detailed_info_word,
            duration,
            direction
        )

        start_X_ShowCountSelectedWords
    }


//        duration: Int = 1000,
//        direction: Direction = Direction.TO_END,
//    ) {
//        Animator2.transition(
//            motion,
//            start to show_count_selected_words,
//
//        )
//    }

//    fun showListPhrases_X_ShowCountSelectedPhrases(
//        duration: Int = 1000,
//        direction: Direction = Direction.TO_END,
//    ) {
//        Animator2.transition(
//            motion,
//            show_list_phrases, show_count_selected_phrases,
//        )
//    }

//    fun showListPhrases_X_ShowDetailedInfoPhrase(
//        duration: Int = 1000,
//        direction: Direction = Direction.TO_END,
//    ) {
//        Animator2.transition(
//            motion,
//            show_list_phrases, show_detailed_info_phrase,
//        )
//    }
}

