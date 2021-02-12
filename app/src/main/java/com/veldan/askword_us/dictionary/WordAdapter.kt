package com.veldan.askword_us.dictionary

import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.global.inflate
import kotlinx.android.synthetic.main.item_word.view.*

class WordAdapter(
    val viewModel: DictionaryViewModel,
    val animatorDictionary: DictionaryAnimator,
    val binding: FragmentDictionaryBinding,
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    private val TAG = this::class.simpleName

    var words = listOf<WordModel>()
        set(value) {
            Log.i("VLAD", "$value")
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
                setOnClickListener {
                    binding.layoutDetailedInformationWord.apply {
                        Glide.with(root)
                            .load(word.image)
                            .into(ivDetailedImage)

                        tvDetailedWord.text = word.word
                        tvDetailedTranslation.text = ""
                        word.translations.forEach {
                            tvDetailedTranslation.append("$it;\n")
                        }
                        tvDetailedPrompt.text = word.prompt
                    }
                    animatorDictionary.start_To_Set_7()
                }
            }

            layout.ib_drop_item.setOnClickListener {
                viewModel.wordDelete(word)
                notifyItemRemoved(position)
            }
        }
    }
}