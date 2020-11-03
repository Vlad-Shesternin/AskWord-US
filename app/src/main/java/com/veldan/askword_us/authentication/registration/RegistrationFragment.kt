package com.veldan.askword_us.authentication.registration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.databinding.FragmentRegistrationBinding

private const val TAG = "RegistrationFragment"

class RegistrationFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentRegistrationBinding

    // ViewModel and Factory
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var viewModelFactory: RegistrationViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.registrationFragment = this

        Log.i(TAG, "viewModel = ViewModelProvider")
        viewModelFactory = RegistrationViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)

        return binding.root
    }

    private fun getUser() = User(
        name = binding.editName.text.toString(),
        surname = binding.editSurname.text.toString(),
        email = binding.editEmail.text.toString(),
        password = binding.editPassword.text.toString())

    fun registration() {
        viewModel.registration(getUser())
    }
}