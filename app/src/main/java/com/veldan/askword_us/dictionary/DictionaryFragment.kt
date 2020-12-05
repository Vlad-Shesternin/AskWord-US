package com.veldan.askword_us.dictionary

import android.os.Bundle
import android.transition.Transition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.databinding.FragmentDictionaryStartBinding
import com.veldan.askword_us.dictionary.worc_creator.WordCreatorAnimatorViewModel
import com.veldan.askword_us.dictionary.worc_creator.WordCreatorAnimatorViewModelFactory
import com.veldan.askword_us.generated.callback.OnClickListener
import com.veldan.askword_us.global.interfaces.TransitionListener
import com.veldan.askword_us.global.objects.Animator

class DictionaryFragment :
    Fragment(),
    View.OnClickListener,
    View.OnLongClickListener,
    TransitionListener {

    // Components
    private lateinit var binding: FragmentDictionaryStartBinding

    private lateinit var viewModelFactory: DictionaryAnimatorViewModelFactory
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
        binding = FragmentDictionaryStartBinding.inflate(inflater)
    }

    // init ViewModel
    private fun initViewModel() {
        viewModelFactory = DictionaryAnimatorViewModelFactory(binding, this)
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