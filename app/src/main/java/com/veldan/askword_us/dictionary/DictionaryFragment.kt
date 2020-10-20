package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.veldan.askword_us.Animator
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.databinding.LayoutWordsCreationBinding
import com.veldan.askword_us.generated.callback.OnClickListener


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

        init {
            //init PromptAnimation(layout_words_creation)
            PromptAnimation(layoutDictionary.layoutWordsCreation)
        }

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
                fabAdd.id to start -> appearanceLayoutWordsCreation()
                fabAdd.id to appearanceFabsAdd -> appearanceFabAddsToMoveToCenter()

                fabFile.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabFile()
                fabPhoto.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabPhoto()
                fabCategory.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabCategory()
            }
        }

        override fun onLongClick(v: View?): Boolean {
            when (v?.id to motion.currentState) {
                fabAdd.id to start -> startToMoveToCenter()
            }
            return true
        }


        private fun startToMoveToCenter() {
            Animator.transition(motion, start, moveToCenter, 1000)
        }

        private fun appearanceLayoutWordsCreation() {
            Animator.transition(motion, start, appearanceLayoutWordsCreation, 1000)
        }

        private fun appearanceFabAddsToMoveToCenter() {
            Animator.transition(motion, appearanceFabsAdd, backMoveToCenter, 1000)
        }

        private fun appearanceFabAddsToChoiceFabCategory() {
            Animator.transition(motion, appearanceFabsAdd, choiceFabCategory, 1000)
        }

        private fun appearanceFabAddsToChoiceFabPhoto() {
            Animator.transition(motion, appearanceFabsAdd, choiceFabPhoto, 1000)
        }

        private fun appearanceFabAddsToChoiceFabFile() {
            Animator.transition(motion, appearanceFabsAdd, choiceFabFile, 1000)
        }
    }

    //==============================
    //      PromptAnimation
    //==============================
    private class PromptAnimation(private val layoutWordsCreation: LayoutWordsCreationBinding) :
        View.OnClickListener {

        //Components layout_words_creation
        private val motion = layoutWordsCreation.motion
        private val ifvPromptAdd = layoutWordsCreation.ifvPromptAdd

        //ConstraintIds for dictionary_scene
        private val start = R.id.start
        private val moveToTop = R.id.move_to_top

        init {
            ifvPromptAdd.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //when use Pair<Int, Int> (v?.id, motion.currentState)
            when (v?.id to motion.currentState) {
                ifvPromptAdd.id to start -> startToMoveToTop()
            }
        }

        private fun startToMoveToTop() {
            Animator.transition(motion, start, moveToTop, 1000)
        }
    }
}