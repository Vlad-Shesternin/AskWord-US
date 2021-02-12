package com.veldan.askword_us.study.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimator
import kotlinx.android.synthetic.main.item_phrase.view.*
import kotlinx.android.synthetic.main.layout_detailed_information_phrase.view.*

class PhraseAdapter(
    val animatorStudy: StudyAnimator,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder>() {
    private val TAG = this::class.simpleName

    // Components
    private val listSelectedPhrases = mutableListOf<PhraseModel>()

    var phrases = listOf<PhraseModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val layout = parent.inflate(R.layout.item_phrase_study) as MotionLayout
        return PhraseViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val phrase = phrases[position]
        holder.bind(phrase)
    }

    override fun getItemCount() = phrases.count()

    inner class PhraseViewHolder(private val layout: MotionLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(phrase: PhraseModel) {
            layout.tv_item_phrase.apply {
                text = phrase.phrase

                setOnClickListener {
                    listSelectedPhrases.add(phrase)
                    bindingStudy.tvCountSelectedWords.text = listSelectedPhrases.size.toString()

                    animatorStudy.showListPhrases_To_ShowCountSelectedPhrases()

                    Animator2.transition(this@PhraseViewHolder.layout,
                        R.id.start, R.id.hide_item_phrase,
                        duration = 500,
                        Direction.TO_END)
                }

                setOnLongClickListener {
                    bindingStudy.layoutDetailedInformationPhrase.apply {
                        svDetailedPhrase.tv_detailed_phrase.text = phrase.phrase
                        svDetailedTranslation.tv_detailed_translation.text = phrase.translation
                    }
                    animatorStudy.showListPhrases_To_ShowDetailedInfoPhrase()
                    return@setOnLongClickListener true
                }
            }
        }
    }
}