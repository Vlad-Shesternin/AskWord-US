package com.veldan.askword_us.authentication.sign_in

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.veldan.askword_us.R
import com.veldan.askword_us.global.emums.RequestCode

private const val TAG = "SignInFragment"

open class SignInWithGoogleViewModel(
    private val fragment: SignInFragment,
) : ViewModel() {

    // Google
    private lateinit var googleSignInClient: GoogleSignInClient

    // Firebase
    private val auth = FirebaseAuth.getInstance()

    // Properties
    private val context = fragment.requireContext()

    // ==============================
    //       InitGoogleSignInClient
    // ==============================
    private fun initGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(context, getGSO())
    }

    // ==============================
    //       GetGoogleSignInOptions
    // ==============================
    private fun getGSO() = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    // ==============================
    //       SignInWithGoogle
    // ==============================
    fun signInWithGoogle() {
        initGoogleSignInClient()
        val signInIntent = googleSignInClient.signInIntent
        fragment.startActivityForResult(signInIntent, RequestCode.RC_SIGN_IN.id)
    }

    // ==============================
    //       FirebaseAuthWithGoogle
    // ==============================
    fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                account.also {
                    transitionToStart(it.givenName!!, it.familyName!!, it.email!!)
                }
                Log.i(TAG, "SignIn with Google Credential is Successful")
            }
            .addOnFailureListener {
                Log.i(TAG, "SignIn with Google Credential is Failure |||| ${it.message} ")
            }
    }

    // ==============================
    //       SignOutDefaultAccount
    // ==============================
    fun signOutDefaultAccount() {
        googleSignInClient.signOut()
    }

    // ==============================
    //       TransitionToStart
    // ==============================
    private fun transitionToStart(name: String, surname: String, email: String) {
        val action =
            SignInFragmentDirections.actionSignInFragmentToStartFragment(name, surname, email)
        fragment.findNavController().navigate(action)
    }
}
