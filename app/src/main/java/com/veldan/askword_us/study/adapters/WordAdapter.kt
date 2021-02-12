package com.veldan.askword_us.study.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.veldan.askword_us.R
import com.veldan.askword_us.database.word.WordModel
import com.veldan.askword_us.databinding.FragmentStudyBinding
import com.veldan.askword_us.global.inflate
import com.veldan.askword_us.global.objects.Animator2
import com.veldan.askword_us.global.objects.Direction
import com.veldan.askword_us.study.StudyAnimator
import kotlinx.android.synthetic.main.item_word.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WordAdapter(
    val animatorStudy: StudyAnimator,
    val bindingStudy: FragmentStudyBinding,
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {
    private val TAG = this::class.simpleName

    // Components
    private val listSelectedWords = mutableListOf<WordModel>()

    var words = listOf<WordModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = parent.inflate(R.layout.item_word_study) as MotionLayout
        return WordViewHolder(layout)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.bind(word)
    }

    override fun getItemCount() = words.count()

    inner class WordViewHolder(private val layout: MotionLayout) :
        RecyclerView.ViewHolder(layout) {

        fun bind(word: WordModel) {
            layout.tv_item_word.apply {
                text = word.word

                setOnClickListener {
                    listSelectedWords.add(word)
                    bindingStudy.tvCountSelectedWords.text = listSelectedWords.size.toString()

                    animatorStudy.start_To_ShowCountSelectedWords()

                    Animator2.transition(this@WordViewHolder.layout,
                        R.id.start, R.id.hide_item_word,
                        duration = 500,
                        Direction.TO_END)
                }

                setOnLongClickListener {
                    bindingStudy.layoutDetailedInformationWord.apply {
                        Glide.with(root)
                            .load(word.image)
                            .into(ivDetailedImage)

                        tvDetailedWord.text = word.word
                        tvDetailedTranslation.text = ""// очищаем прошлый текст
                        word.translations.forEach {
                            tvDetailedTranslation.append("$it;\n")
                        }
                        tvDetailedPrompt.text = word.prompt
                    }
                    StudyAnimator.start_To_ShowDetailedInfoWord()
                    return@setOnLongClickListener true
                }
            }
        }
    }
}