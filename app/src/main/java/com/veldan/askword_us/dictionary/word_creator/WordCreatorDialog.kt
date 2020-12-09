package com.veldan.askword_us.dictionary.word_creator

import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.LayoutWordCreatorBinding
import com.veldan.askword_us.dictionary.DictionaryFragment
import com.veldan.askword_us.global.interfaces.TransitionListener

class WordCreatorDialog(
    private val layoutWordCreation: LayoutWordCreatorBinding,
    fragment: DictionaryFragment
) :
    View.OnClickListener,
    TransitionListener {

    // Components
    private val viewModelFactory = WordCreatorAnimatorViewModelFactory(layoutWordCreation, fragment)
    private val viewModel =
        ViewModelProvider(fragment, viewModelFactory).get(WordCreatorAnimatorViewModel::class.java)

    // init Listeners
    init {
        layoutWordCreation.also {
            // onClick
            it.ifvPromptAdd.setOnClickListener(this)
            it.ibTranslations.setOnClickListener(this)
            it.ifvListTranslations.setOnClickListener(this)
            // onTransition
            it.motionWordCreator.setTransitionListener(this)
        }
    }

    override fun onTransitionChange(
        motionLayout: MotionLayout?,
        start: Int,
        end: Int,
        progress: Float
    ) {
        super.onTransitionChange(motionLayout, start, end, progress)
        viewModel.onTransitionChange(end, progress)
    }

    // ==============================
    //    OnClick
    // ==============================
    override fun onClick(v: View?) {
        viewModel.onClick(v!!)
    }
}

