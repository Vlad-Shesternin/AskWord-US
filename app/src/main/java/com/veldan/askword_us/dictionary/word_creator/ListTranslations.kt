package com.veldan.askword_us.dictionary.word_creator

import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemTranslationsBinding
import kotlinx.android.synthetic.main.layout_translations.view.*

class ListTranslations(
    private val inflater: LayoutInflater
) {

    // Components
    private var increment = 0
        get() = field++
    val ids = mutableListOf<Int>()

    // ==============================
    //     Add Item to List
    // ==============================
    fun addItemToLayoutTranslations(translation: String, layout: ConstraintLayout) {
        val delete = DeleteBinding.inflate(inflater).root
        val item = ItemTranslationsBinding.inflate(inflater)
        val scroll = item.scrollHItemTranslation
        item.itemTranslation.text = translation

        scroll.id = increment
        delete.id = increment

        layout.addView(scroll)
        layout.addView(delete)

        val set = ConstraintSet()

        set.clone(layout)
        // scroll [width = 0]
        set.constrainWidth(scroll.id, 0)
        // scroll [horizontal connect]
        set.connect(scroll.id, START, layout.id, START, 10)
        set.connect(scroll.id, END, layout.guideV_80.id, START, 10)
        // delete [size = 0]
        set.constrainHeight(delete.id, 0)
        set.constrainWidth(delete.id, 0)
        // delete [ratio = 1:1]
        set.setDimensionRatio(delete.id, "1:1")
        // delete [horizontal connect]
        set.connect(delete.id, START, layout.guideV_80.id, END, 10)
        set.connect(delete.id, END, layout.id, END, 10)
        // scroll [vertical connect]
        if (ids.isEmpty()) {
            set.connect(scroll.id, TOP, layout.id, TOP, 10)
        } else {
            set.connect(scroll.id, TOP, ids.last(), BOTTOM)
        }
        // delete [vertical connect]
        set.connect(delete.id, TOP, scroll.id, TOP)
        set.connect(delete.id, BOTTOM, scroll.id, BOTTOM)
        // add id
        ids.add(scroll.id)
        // apply changes
        set.applyTo(layout)

        delete.setOnClickListener {
            set.clone(layout)
            set.constrainHeight(scroll.id, 0)
            ids.remove(scroll.id)
            set.applyTo(layout)

            if (ids.isEmpty()) {
                WordCreatorAnimator.set_5_To_Set_3()
            }
        }
    }
}
