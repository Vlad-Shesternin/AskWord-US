package com.veldan.askword_us.study.adapters

import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimations
import kotlinx.android.synthetic.main.item_phrase.view.*
import kotlinx.android.synthetic.main.item_word.view.*
import kotlinx.android.synthetic.main.layout_detailed_information_phrase.view.*

class SelectedPhraseAdapter(
    val adapterPhrase: PhraseAdapter,
    val animations: StudyAnimations,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<SelectedPhraseAdapter.PhraseViewHolder>() {
    private val TAG = this::class.simpleName

    var phrases = mutableListOf<PhraseModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhraseViewHolder {
        val layout = parent.inflate(R.layout.item_phrase) as ConstraintLayout
        return PhraseViewHolder(layout)
    }

    override fun onBindViewHolder(holder: PhraseViewHolder, position: Int) {
        val phrase = phrases[position]
        holder.bind(phrase, position)
    }

    override fun getItemCount() = phrases.count()

    inner class PhraseViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(phrase: PhraseModel, position: Int) {
            layout.tv_item_phrase.apply {
                text = phrase.phrase

                // ==============================
                //    onClick
                // ==============================
                setOnClickListener {
                    bindingStudy.layoutDetailedInformationPhrase.apply {
                        svDetailedPhrase.tv_detailed_phrase.text = phrase.phrase
                        svDetailedTranslation.tv_detailed_translation.text = phrase.translation
                    }
                    Animator2.apply {
                        motion = bindingStudy.root
                        animations.apply {
                            transition(show_selected_phrases to show_detailed_info_phrase_selected)
                        }
                    }
                }
            }
            // ==============================
            //    onClick Remove
            // ==============================
            layout.ib_drop_item_phrase.setOnClickListener {
                phrases.remove(phrase)
                adapterPhrase.phrases.add(phrase)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                bindingStudy.layoutCountsSelectedWp.tvCountSelectedPhrases.text =
                    phrases.size.toString()

                if (phrases.isEmpty()) {
                    Animator2.apply {
                        previous.removeAll {
                            it == R.id.show_count_selected_phrases
                        }
                        motion = bindingStudy.root
                        animations.apply {
                            transition(show_selected_phrases to show_list_phrases)
                        }
                        motion = bindingStudy.layoutCountsSelectedWp.root
                        animations.apply {
                            transition(show_count_selected_phrases to start)
                        }
                    }
                }
            }
        }
    }
}