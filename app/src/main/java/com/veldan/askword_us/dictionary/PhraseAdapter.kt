package com.veldan.askword_us.dictionary

import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.phrase.PhraseModel
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.global.inflate
import kotlinx.android.synthetic.main.item_phrase.view.*
import kotlinx.android.synthetic.main.item_word.view.*
import kotlinx.android.synthetic.main.layout_detailed_information_phrase.view.*

class PhraseAdapter(
    val viewModel: DictionaryViewModel,
    val animatorDictionary: DictionaryAnimator,
    val binding: FragmentDictionaryBinding,
) : RecyclerView.Adapter<PhraseAdapter.PhraseViewHolder>() {
    private val TAG = this::class.simpleName

    var phrases = listOf<PhraseModel>()
        set(value) {
            Log.i("VLAD", "$value")
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
                setOnClickListener {
                    binding.layoutDetailedInformationPhrase.apply {
                        svDetailedPhrase.tv_detailed_phrase.text = phrase.phrase
                        svDetailedTranslation.tv_detailed_translation.text = phrase.translation
                    }
                    animatorDictionary.set_8_To_Set_10()
                }
            }
            layout.ib_drop_item_phrase.setOnClickListener {
                viewModel.phraseDelete(phrase)
                notifyItemRemoved(position)
            }
        }
    }
}