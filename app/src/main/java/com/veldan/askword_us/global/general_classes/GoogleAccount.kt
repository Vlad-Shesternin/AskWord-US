package com.veldan.askword_us.global.general_classes

import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.veldan.askword_us.R
import com.veldan.askword_us.global.objects.RequestCode

class GoogleAccount(private val fragment: Fragment) {
    private val TAG = "RequestGoogleAccount"

    // Components
    private lateinit var googleSignInClient: GoogleSignInClient
    private val context = fragment.requireContext()

    // ==============================
    //    InitGoogleSignInClient
    // ==============================
    private fun initGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(context, getGSO())
    }

    // ==============================
    //    GetGoogleSignInOptions
    // ==============================
    private fun getGSO() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    // ==============================
    //    SignInWithGoogle
    // ==============================
    fun signInWithGoogle() {
        initGoogleSignInClient()
        val signInIntent = googleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, RequestCode.RC_GOOGLE)
    }

    // ==============================
    //    SignOutDefaultAccount
    // ==============================
    fun signOutDefaultAccount() {
        googleSignInClient.signOut()
    }
}