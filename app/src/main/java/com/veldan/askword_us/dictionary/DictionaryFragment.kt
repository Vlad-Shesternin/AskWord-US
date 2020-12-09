package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.FragmentDictionaryBinding
import com.veldan.askword_us.global.interfaces.TransitionListener

class DictionaryFragment :
    Fragment(),
    View.OnClickListener,
    View.OnLongClickListener,
    TransitionListener {

    // Components
    private lateinit var binding: FragmentDictionaryBinding

    private lateinit var viewModel: DictionaryAnimatorViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initBinding(inflater)
        initViewModel()
        initListeners()

        return binding.root
    }

    // init Binding
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryBinding.inflate(inflater)
    }

    // init ViewModel
    private fun initViewModel() {
        val viewModelFactory = DictionaryAnimatorViewModelFactory(binding, this)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DictionaryAnimatorViewModel::class.java)
    }

    // init Listeners
    private fun initListeners() {
        // onClick
        binding.also {
            it.fabAdd.setOnClickListener(this)
            it.fabCategory.setOnClickListener(this)
            it.fabPhoto.setOnClickListener(this)
            it.fabFile.setOnClickListener(this)
        }
        // onLongClick
        binding.fabAdd.setOnLongClickListener(this)
        // onTransition
        binding.motionDictionary.setTransitionListener(this)
    }

    // ==============================
    //    onClick
    // ==============================
    override fun onClick(v: View?) {
        viewModel.onClick(v!!)
    }

    // ==============================
    //    onLongClick
    // ==============================
    override fun onLongClick(v: View?): Boolean {
        viewModel.onLongClick(v!!)
        Log.i("ccc", "onLongClick: ")
        return true
    }

    // ==============================
    //    OnTransition
    // ==============================
    override fun onTransitionCompleted(motionLayout: MotionLayout?, end: Int) {
        super.onTransitionCompleted(motionLayout, end)
        viewModel.onTransitionCompleted(end)
    }
}