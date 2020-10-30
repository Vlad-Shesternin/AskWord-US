package com.veldan.askword_us.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.databinding.FragmentAuthenticationBinding


class AuthenticationFragment : Fragment() {

    private lateinit var binding: FragmentAuthenticationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentAuthenticationBinding.inflate(inflater)
        binding.authenticationFragment = this

        return binding.root
    }



    fun transitionToStart() {
        val action =
            AuthenticationFragmentDirections.actionAuthenticationFragmentToStartFragment()
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
}