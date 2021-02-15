package com.veldan.askword_us.study.adapters

import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimations
import kotlinx.android.synthetic.main.item_word.view.*

class SelectedWordAdapter(
    val adapterWord: WordAdapter,
    val animations: StudyAnimations,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<SelectedWordAdapter.WordViewHolder>() {
    private val TAG = this::class.simpleName

    var words = mutableListOf<WordModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = parent.inflate(R.layout.item_word) as ConstraintLayout
        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word, position)
    }

    override fun getItemCount() = words.count()

    inner class WordViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(word: WordModel, position: Int) {
            layout.tv_item_word.apply {
                text = word.word
            }
            layout.ib_drop_item.setOnClickListener {
                words.remove(word)
                adapterWord.words.add(word)
                notifyItemRemoved(position)
                notifyDataSetChanged()
                bindingStudy.layoutCountsSelectedWp.tvCountSelectedWords.text =
                    words.size.toString()

                if (words.isEmpty()) {
                    Animator2.apply {
                        previous.removeAll {
                            it == R.id.show_count_selected_words
                        }
                        motion = bindingStudy.root
                        animations.apply {
                            transition(show_selected_words to start)
                        }
                        motion = bindingStudy.layoutCountsSelectedWp.root
                        animations.apply {
                            transition(show_count_selected_words to start)
                        }
                    }
                }
            }
        }
    }
}