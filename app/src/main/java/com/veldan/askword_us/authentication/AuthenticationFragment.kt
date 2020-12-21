package com.veldan.askword_us.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding
import com.veldan.askword_us.global.objects.Animator
import com.veldan.askword_us.global.objects.Internet


class AuthenticationFragment :
    Fragment(),
    View.OnClickListener {

    // Binding
    private lateinit var binding: FragmentAuthenticationBinding

    // Components UI
    private lateinit var motion: MotionLayout
    private lateinit var btnSignIn: Button
    private lateinit var btnCancel: Button
    private lateinit var btnContinue: Button
    private lateinit var btnRegistration: Button
    private lateinit var btnSignInWithoutRegistration: Button

    // Components
    private val animator = AuthenticationAnimator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initComponentsUI()
        initComponents()
        initListeners()

        return binding.root
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentAuthenticationBinding.inflate(inflater)
    }

    // ==============================
    //    Init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            motion = it.layoutAuthentication
            btnSignIn = it.btnSignIn
            btnCancel = it.layoutWarning.btnCancel
            btnContinue = it.layoutWarning.btnContinue
            btnRegistration = it.btnRegistration
            btnSignInWithoutRegistration = it.btnSignInWithoutRegistration
        }
    }

    // ==============================
    //    Init Components
    // ==============================
    private fun initComponents() {
        animator.motion = motion
    }

    // ==============================
    //    Init Listeners
    // ==============================
    private fun initListeners() {
        btnCancel.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
        btnContinue.setOnClickListener(this)
        btnRegistration.setOnClickListener(this)
        btnSignInWithoutRegistration.setOnClickListener(this)
    }

    // ==============================
    //    to DictionaryOrStudy
    // ==============================
    private fun transitionToDictionaryOrStudy() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToDictionaryOrStudyFragment(
                "Нет имени",
                "Нет фамилии",
                "Нет адреса"
            )
        findNavController().navigate(action)
    }

    // ==============================
    //    to SignIn
    // ==============================
    private fun transitionToSignIn() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToSignInFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    to Registration
    // ==============================
    private fun transitionToRegistration() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    onClick
    // ==============================
    override fun onClick(v: View) {
        when (v.id) {
            btnSignIn.id -> transitionToSignIn()
            btnContinue.id -> transitionToDictionaryOrStudy()
            btnRegistration.id -> transitionToRegistration()
        }
        // when use Pair<Int, Int> (v?.id, motion.currentState)
        when (v.id to motion.currentState) {
            btnSignInWithoutRegistration.id to animator.start -> {
                animator.start_To_Set_1()
            }
            btnCancel.id to animator.set_1 -> {
                animator.set_1_To_Start()
            }
        }
    }
}