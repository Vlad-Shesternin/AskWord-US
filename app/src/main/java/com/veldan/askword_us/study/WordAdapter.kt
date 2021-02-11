package com.veldan.askword_us.study

import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.global.inflate
import kotlinx.android.synthetic.main.item_word.view.*

class WordAdapter(
    val animatorStudy: StudyAnimator,
) : RecyclerView.Adapter<com.veldan.askword_us.study.WordAdapter.WordViewHolder>() {

    var words = listOf<WordModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = parent.inflate(R.layout.item_word_study) as ConstraintLayout
        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.count()

    inner class WordViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(word: WordModel) {
            layout.tv_item_word.setOnClickListener {

            }
        }
    }
}