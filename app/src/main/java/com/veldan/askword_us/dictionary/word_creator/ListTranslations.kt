package com.veldan.askword_us.dictionary.word_creator

import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemTranslationsBinding
import com.veldan.askword_us.global.toast
import kotlinx.android.synthetic.main.layout_translations.view.*

class ListTranslations(
    private val inflater: LayoutInflater,
) {

    // Components
    val listTranslations = mutableListOf<String>()
    private var increment = 0
        get() = ++field
    private var id = 0
    private val animator = WordCreatorAnimator

    // ==============================
    //     Add Item to List
    // ==============================
    fun addItemToLayoutTranslations(translation: String, layout: ConstraintLayout) {
        val delete = DeleteBinding.inflate(inflater).root
        val item = ItemTranslationsBinding.inflate(inflater)
        val scroll = item.scrollHItemTranslation
        item.itemTranslation.text = translation

        if (!listTranslations.contains(translation)) {
            listTranslations.add(translation)

            delete.id = increment
            scroll.id = increment

            layout.addView(delete)
            layout.addView(scroll)

            val set = ConstraintSet()

            set.clone(layout)
            // delete [size = 0]
            set.constrainHeight(delete.id, 0)
            set.constrainWidth(delete.id, 0)
            set.constrainPercentWidth(delete.id, 0.2F)
            // delete [ratio = 1:1]
            set.setDimensionRatio(delete.id, "1:1")
            // delete [horizontal connect]
            set.connect(delete.id, END, layout.id, END, 10)
            // scroll [width = 0]
            set.constrainWidth(scroll.id, 0)
            set.constrainHeight(scroll.id, 0)
            // scroll [horizontal connect]
            set.connect(scroll.id, START, layout.id, START, 10)
            set.connect(scroll.id, END, delete.id, START, 10)
            // scroll [vertical connect]
            if (id == 0) {
                set.connect(delete.id, TOP, layout.id, TOP, 10)
            } else {
                set.connect(delete.id, TOP, id, BOTTOM)
            }
            set.connect(scroll.id, TOP, delete.id, TOP)
            set.connect(scroll.id, BOTTOM, delete.id, BOTTOM)
            // add id
            id = delete.id
            // apply changes
            set.applyTo(layout)

            delete.setOnClickListener {
                set.clone(layout)
                set.setDimensionRatio(delete.id, "0:0")
                set.applyTo(layout)
                listTranslations.remove(translation)
                if (listTranslations.isEmpty()) {
                    animator.apply {
                        when (motion.currentState) {
                            set_5 -> {
                                set_5_To_Set_3()
                            }
                            set_13 -> {
                                set_13_To_Set_11()
                            }
                        }
                    }
                }
            }
        } else {
            "Такой перевод уже добавлен!!!".toast(inflater.context)
        }
    }
}

