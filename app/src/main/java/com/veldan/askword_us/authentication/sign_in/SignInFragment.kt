package com.veldan.askword_us.authentication.sign_in

import android.os.Bundle
import android.os.UserHandle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.authentication.registration.RegistrationViewModel
import com.veldan.askword_us.authentication.registration.RegistrationViewModelFactory
import com.veldan.askword_us.databinding.FragmentRegistrationBinding
import com.veldan.askword_us.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentSignInBinding

    // ViewModel and Factory
    private lateinit var viewModel: SignInViewModel
    private lateinit var viewModelFactory: SignInViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initViewModel()
        initBinding(inflater)

        return binding.root
    }

    private fun initViewModel() {
        viewModelFactory = SignInViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.signInFragment = this
    }

    private fun getUser() = User(
        email = binding.editEmail.text.toString(),
        password = binding.editPassword.text.toString())

    fun signIn() {
        viewModel.signIn(getUser())
    }

    fun forgetPassword() {
        viewModel.forgetPassword(getUser())
    }
}