package com.veldan.askword_us.dictionary.word_creator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.ConstraintSet.*
import com.veldan.askword_us.databinding.DeleteBinding
import com.veldan.askword_us.databinding.ItemListTranslationsBinding
import com.veldan.askword_us.databinding.LayoutTranslationsBinding
import kotlinx.android.synthetic.main.layout_translations.view.*

class ListTranslations(
    private val inflater: LayoutInflater
) {
    val TAG = "sss"

    // Components
    private var increment = 0
        get() = field++
    private val ids = mutableListOf<Int>()

    // ==============================
    //     Add Item to List
    // ==============================
    fun addItemToLayoutTranslations(translation: String) {
        val layoutTranslations: ConstraintLayout
        val scroll: HorizontalScrollView
        val delete = DeleteBinding.inflate(inflater).ivDelete

        LayoutTranslationsBinding.inflate(inflater).also {
            layoutTranslations = it.layoutTranslations
        }
        ItemListTranslationsBinding.inflate(inflater).also {
            scroll = it.scrollHItemTranslation
            it.itemTranslation.text = translation
            Log.i(TAG, "addItemToLayoutTranslations: text = ${it.itemTranslation.text}")
        }


        scroll.id = ("1${increment}").toInt()
        Log.i(TAG, "addItemToLayoutTranslations: inc_1 = ${scroll.id}")
        delete.id = ("2${increment}").toInt()
        Log.i(TAG, "addItemToLayoutTranslations: inc_2 = ${delete.id}")

        val set = ConstraintSet()

        layoutTranslations.also { layout ->
            layout.addView(scroll)
            layout.addView(delete)

            set.clone(layout)

            // connect scroll
            set.constrainWidth(scroll.id, 0)
            set.connect(scroll.id, START, PARENT_ID, START, 10)
            set.connect(scroll.id, END, layout.guideV_80.id, START, 10)

            // connect delete
            set.constrainHeight(delete.id, 0)
            set.constrainWidth(delete.id, 0)
            set.setDimensionRatio(delete.id, "1:1")
            set.connect(delete.id, START, layout.guideV_80.id, END, 10)
            set.connect(delete.id, END, PARENT_ID, END, 10)

            if (ids.isEmpty()) {
                // connect scroll
                set.connect(scroll.id, TOP, PARENT_ID, TOP, 10)
                // connect delete
                set.connect(delete.id, TOP, scroll.id, TOP)
                set.connect(delete.id, BOTTOM, scroll.id, BOTTOM)
            } else {
                // connect scroll
                set.connect(scroll.id, TOP, ids.last(), BOTTOM)
                // connect delete
                set.connect(delete.id, TOP, scroll.id, TOP)
                set.connect(delete.id, BOTTOM, scroll.id, BOTTOM)
            }
            // add id
            ids.add(scroll.id)
            // apply changes
            set.applyTo(layout)
        }


        delete.setOnClickListener {
            layoutTranslations.also { layout ->
                set.clone(layout)
                set.constrainHeight(scroll.id, 0)
                ids.remove(scroll.id)
                set.applyTo(layout)
            }

            if (ids.isEmpty()) {
                WordCreatorAnimator.set_5_To_Set_3()
            }
        }
    }
}
