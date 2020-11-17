package com.veldan.askword_us.dictionary

import android.view.View
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.global.objects.Animator

class DictionaryAnimator(private val layoutDictionary: FragmentDictionaryBinding) :
    View.OnClickListener,
    View.OnLongClickListener {

    //Components fragment_dictionary
    private val motion = layoutDictionary.layoutDictionary
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
            fabAdd.id to start -> startToAppearanceLayoutWordsCreation()
            fabAdd.id to appearanceFabsAdd -> appearanceFabsAddToBackMoveToCenter()

            fabFile.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabFile()
            fabPhoto.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabPhoto()
            fabCategory.id to appearanceFabsAdd -> appearanceFabsAddToChoiceFabCategory()
        }
    }

    override fun onLongClick(v: View?): Boolean {
        when (v?.id to motion.currentState) {
            fabAdd.id to start -> Animator.transition(motion, start, moveToCenter, 1000)
        }
        return true
    }

    private fun startToAppearanceLayoutWordsCreation() {
        Animator.transition(motion, start, appearanceLayoutWordsCreation, 700)
    }

    private fun appearanceFabsAddToBackMoveToCenter() {
        Animator.transition(motion, appearanceFabsAdd, backMoveToCenter, 1000)
    }

    private fun appearanceFabsAddToChoiceFabFile() {
        Animator.transition(motion, appearanceFabsAdd, choiceFabFile, 1000)
    }

    private fun appearanceFabsAddToChoiceFabPhoto() {
        Animator.transition(motion, appearanceFabsAdd, choiceFabPhoto, 1000)
    }

    private fun appearanceFabsAddToChoiceFabCategory() {
        Animator.transition(motion, appearanceFabsAdd, choiceFabCategory, 1000)
    }
}