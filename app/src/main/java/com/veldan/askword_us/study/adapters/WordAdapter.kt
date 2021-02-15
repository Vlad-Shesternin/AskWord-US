package com.veldan.askword_us.study.adapters

import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
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

class WordAdapter(
    val animations: StudyAnimations,
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

                // ==============================
                //    onClick
                // ==============================

                setOnClickListener {
                    bindingStudy.layoutCountsSelectedWp.root.also { motionCounter ->
                        Animator2.apply {
                            this.motion = motionCounter
                            animations.apply {
                                if (motion.currentState == start) {
                                    previous.add(show_count_selected_words)
                                    transition(start to show_count_selected_words)
                                }
                            }
                        }
                    }

                    this@WordViewHolder.layout.also { motionWord ->
                        if (motionWord.currentState == R.id.start) {
                            Animator2.apply {
                                this.motion = motionWord
                                transition(R.id.start to R.id.hide_item_word)
                            }
                            listSelectedWords.apply {
                                add(word)
                                bindingStudy.layoutCountsSelectedWp.tvCountSelectedWords.text =
                                    this.size.toString()
                            }
                        }
                    }
                }

                // ==============================
                //    onLongClick
                // ==============================
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
                    Animator2.apply {
                        this.motion = bindingStudy.root
                        animations.apply {
                            transition(start to show_detailed_info_word)
                        }
                        this.motion = bindingStudy.layoutCountsSelectedWp.root
                        animations.apply {
                            if (motion.currentState == show_count_selected_words) {
                                transition(show_count_selected_words to start)
                            }
                        }
                    }
                    return@setOnLongClickListener true
                }
            }
        }
    }
}