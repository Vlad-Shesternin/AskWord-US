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

class SelectedPhraseAdapter(
    val animations: StudyAnimations,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<SelectedPhraseAdapter.WordViewHolder>() {
    private val TAG = this::class.simpleName

    var phrases = mutableListOf<PhraseModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = parent.inflate(R.layout.item_phrase) as ConstraintLayout
        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val phrase = phrases[position]
        holder.bind(phrase, position)
    }

    override fun getItemCount() = phrases.count()

    inner class WordViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(phrase: PhraseModel, position: Int) {
            layout.tv_item_phrase.apply {
                text = phrase.phrase
            }
            layout.ib_drop_item_phrase.setOnClickListener {
                phrases.remove(phrase)
                notifyItemRemoved(position)
                notifyDataSetChanged()

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