package com.veldan.askword_us.dictionary

import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.veldan.askword_us.R
import com.veldan.askword_us.global.inflate
import kotlinx.android.synthetic.main.item_word.view.*

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var words = listOf<WordModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val card = parent.inflate(R.layout.item_word) as CardView

        val height = (parent.measuredHeight * 0.05).toInt()
        card.layoutParams.height = height

        return WordViewHolder(card)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.count()

    class WordViewHolder(private val card: CardView) : RecyclerView.ViewHolder(card) {
        private val textView = card.tv_item_word

        fun bind(word: WordModel) {
            textView.text = word.word
        }
    }
}