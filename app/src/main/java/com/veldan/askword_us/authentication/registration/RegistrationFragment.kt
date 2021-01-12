package com.veldan.askword_us.authentication.registration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.veldan.askword_us.global.general_classes.GoogleAccount
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.databinding.FragmentRegistrationBinding
import com.veldan.askword_us.global.defaultFocusAndKeyboard
import com.veldan.askword_us.global.objects.Internet
import com.veldan.askword_us.global.objects.RequestCode

class RegistrationFragment : Fragment() {
    private val TAG = "RegistrationFragment"

    // Binding
    private lateinit var binding: FragmentRegistrationBinding

    // ViewModel and Factory
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var viewModelFactory: RegistrationViewModelFactory

    // Components
    private lateinit var googleAccount: GoogleAccount

    // Components UI
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnGoogle: Button
    private lateinit var editSurname: EditText
    private lateinit var editPassword: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initViewModel()
        initBinding(inflater)
        initComponentsUI()
        initComponents()
        checkInternet()

        return binding.root
    }

    // ==============================
    //    Init ViewModel
    // ==============================
    private fun initViewModel() {
        viewModelFactory = RegistrationViewModelFactory(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            RegistrationViewModel::class.java)
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.registrationFragment = this
        binding.registrationViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    // ==============================
    //    Init Components UI
    // ==============================
    private fun initComponentsUI() {
        binding.also {
            editName = it.editName
            editEmail = it.editEmail
            btnGoogle = it.btnRegistrationWithGoogle
            editSurname = it.editSurname
            editPassword = it.editPassword
        }
    }

    // ==============================
    //    Init Components
    // ==============================
    private fun initComponents() {
        googleAccount = GoogleAccount(this)
    }

    // ==============================
    //    Check Internet
    // ==============================
    private fun checkInternet() {
        if (!Internet.isOnline(requireContext())) {
            noInternet()
        }
    }

    // ==============================
    //    No Internet
    // ==============================
    private fun noInternet() {
        binding.groupRegistration.visibility = View.INVISIBLE
        binding.lottieProgress.also {
            it.setAnimation(Internet.NO_INTERNET)
            it.playAnimation()
        }
    }

    // ==============================
    //    GetUser
    // ==============================
    private fun getUser() = User(
        name = binding.editName.text.toString(),
        surname = binding.editSurname.text.toString(),
        email = binding.editEmail.text.toString(),
        password = binding.editPassword.text.toString())


    // ==============================
    //    Registration
    // ==============================
    fun registration() {
        val views = arrayOf(btnGoogle, editName, editSurname, editEmail, editPassword)
        viewModel.registration(getUser(), *views)
    }

    // ==============================
    //    RegistrationWithGoogle
    // ==============================
    fun registrationWithGoogle() {
        googleAccount.signInWithGoogle()
    }

    // ==============================
    //    OnActivityResult
    // ==============================
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestCode.RC_GOOGLE) {
            googleAccount.signOutDefaultAccount()
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.i(TAG, "Account: ${account.email}")
                setAccount(account)
                editPassword.defaultFocusAndKeyboard(true)
            } catch (e: ApiException) {
                Log.i(TAG, "Exception.!.!. $e")
            }
        }
    }

    // ==============================
    //    SetAccount
    // ==============================
    private fun setAccount(account: GoogleSignInAccount) {
        account.also {
            editName.setText(it.givenName)
            editSurname.setText(it.familyName)
            editEmail.setText(it.email)
        }
    }

    // ==============================
    //    Verification
    // ==============================
    @SuppressLint("SetJavaScriptEnabled")
    fun verification() {
        binding.webGmail.also {
            it.visibility = View.VISIBLE
            it.settings.javaScriptEnabled = true
            it.loadUrl("https://mail.google.com/mail")
            it.webViewClient = RegistrationWebViewClient(binding.lottieProgress)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.webViewOnClickBack(binding.webGmail)
    }
}