package com.veldan.askword_us.study.adapters

import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.objects.Animator
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimations
import kotlinx.android.synthetic.main.item_phrase.view.*
import kotlinx.android.synthetic.main.layout_detailed_information_phrase.view.*

class PhraseAdapter(
    val animations: StudyAnimations,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder>() {
    private val TAG = this::class.simpleName

    // Components
    val listSelectedPhrases = mutableListOf<PhraseModel>()

    var phrases = mutableListOf<PhraseModel>()
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
                    bindingStudy.layoutCountsSelectedWp.root.also { motionCounter ->
                        Animator2.apply {
                            this.motion = motionCounter
                            animations.apply {
                                if (motion.currentState == start) {
                                    previous.add(show_count_selected_phrases)
                                    transition(start to show_count_selected_phrases)
                                }
                            }
                        }
                    }

                    this@PhraseViewHolder.layout.also { motionPhrase ->
                        if (motionPhrase.currentState == R.id.start) {
                            Animator2.apply {
                                this.motion = motionPhrase
                                transition(R.id.start to R.id.hide_item_phrase)
                            }
                            listSelectedPhrases.apply {
                                add(phrase)
                                bindingStudy.layoutCountsSelectedWp.tvCountSelectedPhrases.text =
                                    this.size.toString()
                            }
                        }
                    }
                }

                setOnLongClickListener {
                    bindingStudy.layoutDetailedInformationPhrase.apply {
                        svDetailedPhrase.tv_detailed_phrase.text = phrase.phrase
                        svDetailedTranslation.tv_detailed_translation.text = phrase.translation
                    }
                    animations.apply {
                        Animator2.apply {
                            this.motion = bindingStudy.root
                            animations.apply {
                                transition(show_list_phrases to show_detailed_info_phrase)
                            }
                            this.motion = bindingStudy.layoutCountsSelectedWp.root
                            animations.apply {
                                if (motion.currentState == show_count_selected_phrases) {
                                    transition(show_count_selected_phrases to start)
                                }
                            }
                        }
                    }
                    return@setOnLongClickListener true
                }
            }
        }
    }
}