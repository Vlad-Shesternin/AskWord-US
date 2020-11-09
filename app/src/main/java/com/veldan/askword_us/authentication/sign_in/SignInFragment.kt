package com.veldan.askword_us.authentication.sign_in

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.databinding.FragmentSignInBinding
import com.veldan.askword_us.global.emums.RequestCode

private const val TAG = "SignInFragment"

class SignInFragment : Fragment() {

    // Binding
    private lateinit var binding: FragmentSignInBinding

    // ViewModel and Factory
    private lateinit var viewModelEmailPassword: SignInWithEmailPasswordViewModel
    private lateinit var viewModelGoogle: SignInWithGoogleViewModel
    private lateinit var viewModelEmailPasswordFactory: SignInWithEmailPasswordViewModelFactory
    private lateinit var viewModelGoogleFactory: SignInWithGoogleViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        initViewModels()
        initBinding(inflater)

        return binding.root
    }

    // ==============================
    //        Initializing
    // ==============================
    private fun initViewModels() {
        viewModelEmailPasswordFactory = SignInWithEmailPasswordViewModelFactory(this)
        viewModelEmailPassword = ViewModelProvider(this,
            viewModelEmailPasswordFactory).get(SignInWithEmailPasswordViewModel::class.java)

        viewModelGoogleFactory = SignInWithGoogleViewModelFactory(this)
        viewModelGoogle = ViewModelProvider(this,
            viewModelGoogleFactory).get(SignInWithGoogleViewModel::class.java)
    }

    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentSignInBinding.inflate(inflater)
        binding.signInFragment = this
        binding.signInWithGoogle = viewModelGoogle
    }

    // ==============================
    //    signIn with EmailPassword
    // ==============================
    fun getUser() = User(
        email = binding.editEmail.text.toString(),
        password = binding.editPassword.text.toString())

    fun signIn() {
        viewModelEmailPassword.signIn(getUser())
    }

    fun forgetPassword() {
        viewModelEmailPassword.forgetPassword(getUser())
    }

    // ==============================
    //    signIn with Google
    // ==============================
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode.RC_SIGN_IN.id) {
            viewModelGoogle.signOutDefaultAccount()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.i(TAG, "Account: ${account.email}")
                viewModelGoogle.firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.i(TAG, "Exception.!.!.")
            }
        }
    }
}