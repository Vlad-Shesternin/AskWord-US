package com.veldan.askword_us.authentication.sign_in

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.veldan.askword_us.authentication.User
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.AUTH
import com.veldan.askword_us.global.objects.Verification
import com.veldan.askword_us.global.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val fragment: SignInFragment,
) : ViewModel() {
    private val TAG = "SignInFragment"

    // Coroutine
    private val scope = viewModelScope

    // Firebase
    private val auth = FirebaseAuth.getInstance()
    private val fireDb = FirebaseDatabase.getInstance()
    private val users = fireDb.getReference("Users")

    // Components
    private val context = fragment.requireContext()
    private val _visibilityBooleanTextViewVerifyEmail = MutableLiveData<Boolean>()
    val visibilityTextViewVerifyEmail: LiveData<Int> =
        Transformations.map(_visibilityBooleanTextViewVerifyEmail) {
            if (it) View.VISIBLE else View.INVISIBLE
        }

    init {
        _visibilityBooleanTextViewVerifyEmail.value = false
    }

    // ==============================
    //    SignIn
    // ==============================
    fun signIn(user: User) {
        val email = user.email
        val password = user.password

        if (Verification.verifyEmailPassword(context, email, password)) {
            scope.launch(Dispatchers.Default) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        getUserForAccount(email)
                        "Пользователь вошёл".toast(context)
                    }

                    .addOnFailureListener {
                        "Пользователь не вошёл".toast(context)
                    }
            }
        }
    }

    // ==============================
    //    ForgetPassword
    // ==============================
    fun forgetPassword(user: User) {
        val email = user.email

        if (Verification.verifyEmail(context, email)) {
            scope.launch(Dispatchers.Default) {
                auth.sendPasswordResetEmail(email)
                    .addOnSuccessListener {
                        "Инструкцию по восстановлению пароля отправлено на почту: $email".toast(
                            context)
                        _visibilityBooleanTextViewVerifyEmail.value = true
                    }
                    .addOnFailureListener {
                        "Нет такой почты".toast(context)
                    }
            }
        }
    }

    // ==============================
    //    GetUserForAccount
    // ==============================
    private fun getUserForAccount(email: String) {
        users.orderByChild("email").equalTo(email)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val user = data.getValue(User::class.java)!!
                        user.also {
                            "Success getUserForAccount".toast(context)

                            val editor = SharedPreferences(fragment).initSharedPref(AUTH).edit()
                            editor.putString(SharedPreferences.USER_NAME, it.name)
                            editor.putString(SharedPreferences.USER_SURNAME, it.surname)
                            editor.apply()

                            transitionToDictionaryOrStudy(it.name, it.surname, it.email)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    "Fail getUserForAccount".toast(context)
                }
            })
    }

    // ==============================
    //    TransitionToStart
    // ==============================
    private fun transitionToDictionaryOrStudy(name: String, surname: String, email: String) {
        val action =
            SignInFragmentDirections.actionSignInFragmentToDictionaryOrStudy(
                name,
                surname,
                email
            )
        fragment.findNavController().navigate(action)
    }
}