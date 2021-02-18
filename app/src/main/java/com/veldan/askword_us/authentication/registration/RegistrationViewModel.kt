package com.veldan.askword_us.authentication.registration

import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.global.general_classes.Components
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.AUTH
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val fragment: RegistrationFragment,
) : ViewModel() {
    private val TAG = "RegistrationViewModel"

    // Coroutine
    private val scope = viewModelScope

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val fireDb = FirebaseDatabase.getInstance()
    private val users = fireDb.getReference("Users")
    private var fireUser: FirebaseUser? = null

    // Properties
    private val context = fragment.requireContext()
    private var isDeleteUser = true

    private val _visibilityBooleanTextViewVerifyEmail = MutableLiveData<Boolean>()
    val visibilityTextViewVerifyEmail: LiveData<Int> =
        Transformations.map(_visibilityBooleanTextViewVerifyEmail) {
            if (it) View.VISIBLE else View.INVISIBLE
        }

    init {
        _visibilityBooleanTextViewVerifyEmail.value = false
    }

    // ==============================
    //    Registration
    // ==============================
    fun registration(user: User, vararg views: View) {
        val name = user.name
        val surname = user.surname
        val email = user.email
        val password = user.password

        if (Verification.verifyNameSurname(context, name, surname) &&
            Verification.verifyEmailPassword(context, email, password)
        ) {
            "Проверка пройдена".toast(context)
            scope.launch(Dispatchers.Default) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        "Пользователя зарегистрировано".toast(context)
                        Components(*views).enabled(false).background(null)
                        sendEmailVerification(name, surname, email)
                    }
                    .addOnFailureListener {
                        "Пользователя не зарегистрировано".toast(context)
                    }
            }
        }
    }

    // ==============================
    //    SendEmailVerification
    // ==============================
    private fun sendEmailVerification(name: String, surname: String, email: String) {
        fireUser = auth.currentUser
        fireUser!!.sendEmailVerification()
            .addOnSuccessListener {
                "Подтвердите адрес: $email".toast(context)

                _visibilityBooleanTextViewVerifyEmail.value = true

                scope.launch(Dispatchers.Default) {
                    verificationEmail()
                    isDeleteUser = false
                    val user = User(name, surname, email)
                    addUserFireDb(user)

                    val editor = SharedPreferences(fragment).initSharedPref(AUTH).edit()
                    editor.putString(SharedPreferences.USER_NAME, name)
                    editor.putString(SharedPreferences.USER_SURNAME, surname)
                    editor.apply()

                    transitionToDictionaryOrStudy(name, surname, email)
                }
            }
            .addOnFailureListener {
                "Не удалось отправить: $email".toast(context)
            }
    }

    // ==============================
    //    VerificationEmail
    // ==============================
    private suspend fun verificationEmail() {
        val verifyJob = scope.launch {
            var a = 0
            fireUser?.let { user ->
                // Checking email confirmation every one second
                while (!(user.isEmailVerified)) {
                    delay(1000)
                    user.reload()
                    Log.i(TAG, "Подтвердите...${++a}")
                }
            }
        }
        verifyJob.join()
    }

    // ==============================
    //    AddUserFireDb
    // ==============================
    private suspend fun addUserFireDb(user: User) {
        val addUserJob = scope.launch {
            val id = users.push().key!!
            users.child(id)
                .setValue(user)
                .addOnSuccessListener {
                    "Пользователя добавлено в БД".toast(context)
                }
        }
        addUserJob.join()
    }

    // ==============================
    //    WebViewOnClickBack
    // ==============================
    fun webViewOnClickBack(webView: WebView) {
        webView.also {
            it.setOnKeyListener { _, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && it.canGoBack()
                ) {
                    it.goBack()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    // ==============================
    //    TransitionToStart
    // ==============================
    private fun transitionToDictionaryOrStudy(name: String, surname: String, email: String) {
        val action =
            RegistrationFragmentDirections.actionRegistrationFragmentToDictionaryOrStudy(
                name,
                surname,
                email
            )
        fragment.findNavController().navigate(action)
    }

    // ==============================
    //    DeleteUser from DB
    // ==============================
    private fun deleteUserFromDB() {
        if (isDeleteUser) {
            auth.currentUser?.let {
                it.delete().addOnSuccessListener {
                    Log.i(TAG, "User DELETED")
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: call")
        deleteUserFromDB()
    }
}