package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.R
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding
import com.veldan.askword_us.global.objects.Animator


class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAuthenticationBinding.inflate(inflater)
        binding.authenticationFragment = this
        AuthenticationAnimation(binding)

        return binding.root
    }

    fun transitionToDictionaryOrStudy() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToDictionaryOrStudyFragment(
                "Нет имени",
                "Нет фамилии",
                "Нет адреса"
            )
        findNavController().navigate(action)
    }

    fun transitionToSignIn() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToSignInFragment()
        findNavController().navigate(action)
    }

    fun transitionToRegistration() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToRegistrationFragment()
        findNavController().navigate(action)
    }

    //==============================
    //      AuthenticationAnimation
    //==============================
    private inner class AuthenticationAnimation(private val layoutAuthentication: FragmentAuthenticationBinding) :
        View.OnClickListener {

        // Components fragment_authentication
        private val motion = layoutAuthentication.layoutAuthentication
        private val btnSignInWithoutRegistration = layoutAuthentication.btnSignInWithoutRegistration

        private val btnCancel = layoutAuthentication.layoutWarning.btnCancel
        private val btnContinue = layoutAuthentication.layoutWarning.btnContinue

        // ConstraintIds for authentication_scene
        private val start = R.id.start
        private val end = R.id.end

        // Events (fragment_authentication)
        init {
            // OnClick
            btnSignInWithoutRegistration.setOnClickListener(this)
            btnCancel.setOnClickListener(this)
            btnContinue.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            // when use Pair<Int, Int> (v?.id, motion.currentState)
            when (v?.id to motion.currentState) {
                btnSignInWithoutRegistration.id to start -> startToEnd()
                btnCancel.id to end -> endToStart()
                btnContinue.id to end -> transitionToDictionaryOrStudy()
            }
        }

        private fun startToEnd() {
            Animator.transition(motion, end, 1000)
        }

        private fun endToStart() {
            Animator.transition(motion, start, 1000)
        }
    }
}