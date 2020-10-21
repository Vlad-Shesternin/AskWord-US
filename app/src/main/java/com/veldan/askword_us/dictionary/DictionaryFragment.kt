package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.databinding.LayoutWordsCreationBinding
import com.veldan.askword_us.defaultFocusAndKeyboard
import com.veldan.askword_us.interfaces.TransitionListener
import com.veldan.askword_us.objects.Animator


class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDictionaryBinding.inflate(inflater)

        DictionaryAnimation(binding)

        return binding.root
    }

    //==============================
    //      DictionaryAnimation
    //==============================
    private class DictionaryAnimation(private val layoutDictionary: FragmentDictionaryBinding) :
        View.OnClickListener,
        View.OnLongClickListener {

        //Components fragment_dictionary
        private val motion = layoutDictionary.motion
        private val fabAdd = layoutDictionary.fabAdd
        private val fabCategory = layoutDictionary.fabCategory
        private val fabPhoto = layoutDictionary.fabPhoto
        private val fabFile = layoutDictionary.fabFile

        //ConstraintIds for dictionary_scene
        private val start = R.id.start
        private val moveToCenter = R.id.move_to_center
        private val backMoveToCenter = R.id.back_move_to_center
        private val appearanceLayoutWordsCreation = R.id.appearance_layout_words_creation
        private val appearanceFabsAdd = R.id.appearance_fabs_add
        private val choiceFabCategory = R.id.choice_fab_category
        private val choiceFabPhoto = R.id.choice_fab_photo
        private val choiceFabFile = R.id.choice_fab_file

        //PromptAnimation(layout_words_creation)
        init {
            PromptAnimation(layoutDictionary.layoutWordsCreation)
        }

        //Events (fragment_dictionary)
        init {
            //OnLongClick
            fabAdd.setOnLongClickListener(this)
            //OnClick
            fabAdd.setOnClickListener(this)
            fabCategory.setOnClickListener(this)
            fabPhoto.setOnClickListener(this)
            fabFile.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //when use Pair<Int, Int> (v?.id, motion.currentState)
            when (v?.id to motion.currentState) {
                fabAdd.id to start -> Animator.transition(motion,
                    start,
                    appearanceLayoutWordsCreation,
                    1000)
                fabAdd.id to appearanceFabsAdd -> Animator.transition(motion,
                    appearanceFabsAdd,
                    backMoveToCenter,
                    1000)

                fabFile.id to appearanceFabsAdd -> Animator.transition(motion,
                    appearanceFabsAdd,
                    choiceFabFile,
                    1000)
                fabPhoto.id to appearanceFabsAdd -> Animator.transition(motion,
                    appearanceFabsAdd,
                    choiceFabPhoto,
                    1000)
                fabCategory.id to appearanceFabsAdd -> Animator.transition(motion,
                    appearanceFabsAdd,
                    choiceFabCategory,
                    1000)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            when (v?.id to motion.currentState) {
                fabAdd.id to start -> Animator.transition(motion, start, moveToCenter, 1000)
            }
            return true
        }

        //==============================
        //      PromptAnimation
        //==============================
        private class PromptAnimation(private val layoutWordsCreation: LayoutWordsCreationBinding) :
            View.OnClickListener, TransitionListener {

            //Components layout_words_creation
            private val motion = layoutWordsCreation.motion
            private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd

            //ConstraintIds for dictionary_scene
            private val start = R.id.start
            private val moveToTop = R.id.move_to_top
            private val twistingShine = R.id.twisting_shine

            //Events (layout_words_creation)
            init {
                motion.setTransitionListener(this)
                ifvPromptAdd.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                //when use Pair<Int, Int> (v?.id, motion.currentState)
                when (v?.id to motion.currentState) {
                    ifvPromptAdd.id to start -> Animator.transition(motion, start, moveToTop, 5000)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                if (currentId == twistingShine) {
                    layoutWordsCreation.layoutPrompt.editPrompt.defaultFocusAndKeyboard(true)
                }
            }
        }
    }
}