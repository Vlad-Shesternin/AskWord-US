package com.veldan.askword_us.dictionary.animators

import android.content.Context
import android.view.View
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryStartBinding
import com.veldan.askword_us.global.objects.Animator

class DictionaryAnimator(
    private val layoutDictionary: FragmentDictionaryStartBinding,
    val context: Context,
) :
    View.OnClickListener,
    View.OnLongClickListener {

    // Components fragment_dictionary
    private val motion = layoutDictionary.motionDictionary
    private val fabAdd = layoutDictionary.fabAdd
//    private val fabCategory = layoutDictionary.fabCategory
//    private val fabPhoto = layoutDictionary.fabPhoto
//    private val fabFile = layoutDictionary.fabFile

    // TransitionIds for dictionary_scene

    private val startToSet1 = R.id.start_to_set_1
//    private val backMoveToCenter = R.id.back_move_to_center
//    private val appearanceLayoutWordsCreation = R.id.appearance_layout_words_creation
//    private val appearanceFabsAdd = R.id.appearance_fabs_add
//    private val choiceFabCategory = R.id.choice_fab_category
//    private val choiceFabPhoto = R.id.choice_fab_photo
//    private val choiceFabFile = R.id.choice_fab_file

    // WordCreation(layout_words_creation)
    init {
        // WordCreationAnimator(layoutDictionary.layoutWordsCreation, context)
    }

    // Events (fragment_dictionary)
    init {
        //OnLongClick
        fabAdd.setOnLongClickListener(this)
        //OnClick
        fabAdd.setOnClickListener(this)
//        fabCategory.setOnClickListener(this)
//        fabPhoto.setOnClickListener(this)
//        fabFile.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v?.id to motion.currentState) {
            fabAdd.id to R.layout.fragment_dictionary_start -> start_To_Set1()
//
//            fabFile.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabFile()
//            fabPhoto.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabPhoto()
//            fabCategory.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabCategory()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v?.id to motion.currentState) {
            //   fabAdd.id to start -> Animator.transition(motion, moveToCenter, 1000)
        }
        return true
    }

    private fun start_To_Set1() {
        Animator.transitionToEnd(motion, startToSet1, 1000)
    }

//    private fun appearanceFabsAddToBackMoveToCenter() {
//        Animator.transition(motion, backMoveToCenter, 1000)
//    }
//
//    private fun appearanceFabsAddToChoiceFabFile() {
//        Animator.transition(motion, choiceFabFile, 1000)
//    }
//
//    private fun appearanceFabsAddToChoiceFabPhoto() {
//        Animator.transition(motion, choiceFabPhoto, 1000)
//    }
//
//    private fun appearanceFabsAddToChoiceFabCategory() {
//        Animator.transition(motion, choiceFabCategory, 1000)
//    }
}