package com.veldan.askword_us.authentication.registration

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.databinding.FragmentRegistrationBinding
import com.veldan.askword_us.global.objects.Animator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

private const val TAG = "RegistrationFragment"

@RequiresApi(Build.VERSION_CODES.M)
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

        initViewModel()
        initBinding(inflater)

        return binding.root
    }

    private fun initViewModel() {
        viewModelFactory = RegistrationViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(RegistrationViewModel::class.java)
    }

    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.registrationFragment = this
        binding.registrationViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun getUser() = User(
        name = binding.editName.text.toString(),
        surname = binding.editSurname.text.toString(),
        email = binding.editEmail.text.toString(),
        password = binding.editPassword.text.toString())

    fun registration() {
        viewModel.registration(getUser())
    }

    fun verification() {
        if (viewModel.isOnline()) {
            binding.webGmail.also {
                it.visibility = View.VISIBLE
                it.settings.javaScriptEnabled = true
                it.loadUrl("https://mail.google.com/mail")
                it.webViewClient = RegistrationWebViewClient(binding.lottieProgress)
            }

        } else {
            binding.lottieProgress.setAnimation(Animator.NO_INTERNET)
        }
    }
}