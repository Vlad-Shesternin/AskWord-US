package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import kotlinx.android.synthetic.main.fragment_dictionary.view.*

class DictionaryFragment : Fragment() {

    private lateinit var binding: FragmentDictionaryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentDictionaryBinding.inflate(inflater)

        FabAnimation(binding.motion)

        return binding.root
    }

    private class FabAnimation(private val motion: MotionLayout) : View.OnClickListener,
        View.OnLongClickListener {

        private val start = R.id.start

        private val moveToCenter = R.id.move_to_center
        private val backMoveToCenter = R.id.back_move_to_center

        private val appearanceLayoutWordsCreation = R.id.appearance_layout_words_creation
        private val appearanceFabsAdd = R.id.appearance_fabs_add

        private val choiceFabCategory = R.id.choice_fab_category
        private val choiceFabPhoto = R.id.choice_fab_photo
        private val choiceFabFile = R.id.choice_fab_file

        init {
            motion.fab_add.setOnLongClickListener(this)

            motion.fab_add.setOnClickListener(this)
            motion.fab_category.setOnClickListener(this)
            motion.fab_photo.setOnClickListener(this)
            motion.fab_file.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //when use Pair<Int, Int> (v?.id, motion.currentState)
            when (v?.id to motion.currentState) {
                motion.fab_add.id to start -> appearanceLayoutWordsCreation()
                motion.fab_add.id to appearanceFabsAdd -> appearanceFabAddsToMoveToCenter()

                motion.fab_file.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabFile()
                motion.fab_photo.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabPhoto()
                motion.fab_category.id to appearanceFabsAdd -> appearanceFabAddsToChoiceFabCategory()
            }
        }

        override fun onLongClick(v: View?): Boolean {
            when (v?.id) {
                motion.fab_add.id -> startToMoveToCenter()
            }
            return true
        }

        private fun transition(start: Int, end: Int) {
            motion.also {
                if (it.currentState == start) {
                    it.setTransition(start, end)
                    it.setTransitionDuration(1000)
                    it.transitionToEnd()
                }
            }
        }

        private fun startToMoveToCenter() {
            transition(start, moveToCenter)
        }

        private fun appearanceLayoutWordsCreation() {
            transition(start, appearanceLayoutWordsCreation)
        }

        private fun appearanceFabAddsToMoveToCenter() {
            transition(appearanceFabsAdd, backMoveToCenter)
        }

        private fun appearanceFabAddsToChoiceFabCategory() {
            transition(appearanceFabsAdd, choiceFabCategory)
        }

        private fun appearanceFabAddsToChoiceFabPhoto() {
            transition(appearanceFabsAdd, choiceFabPhoto)
        }

        private fun appearanceFabAddsToChoiceFabFile() {
            transition(appearanceFabsAdd, choiceFabFile)
        }
    }
}