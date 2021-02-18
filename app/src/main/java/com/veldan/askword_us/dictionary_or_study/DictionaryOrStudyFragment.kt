package com.veldan.askword_us.dictionary_or_study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.veldan.askword_us.DictionaryOrStudyArgs
import com.veldan.askword_us.database.DatabaseDao
import com.veldan.askword_us.database.MyDatabase
import com.veldan.askword_us.databinding.FragmentDictionaryOrStudyBinding
import com.veldan.askword_us.global.general_classes.SharedPreferences
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.AUTH
import com.veldan.askword_us.global.general_classes.SharedPreferences.Companion.STUDY_FORMAT
import com.veldan.askword_us.start.StartFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DictionaryOrStudyFragment : Fragment() {
    private val TAG = "DictionaryOrStudyFragment"

    // Binding
    private lateinit var binding: FragmentDictionaryOrStudyBinding

    // Firebase
    private val auth = FirebaseAuth.getInstance()

    // Components
    private var visibility = View.INVISIBLE
    private lateinit var databaseDao: DatabaseDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        initBinding(inflater)
        initDatabase()

        return binding.root
    }

    // ==============================
    //    Init Binding
    // ==============================
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentDictionaryOrStudyBinding.inflate(inflater)
        binding.dictionaryOrStudyFragment = this
        binding.layoutAccount.dictionaryOrStudyFragment = this
    }

    // ==============================
    //    Init Database
    // ==============================
    private fun initDatabase() {
        val application = requireNotNull(activity).application
        databaseDao = MyDatabase.getInstance(application).databaseDao
    }

    // ==============================
    //    to Dictionary
    // ==============================
    fun transitionToDictionary() {
        val action =
            DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToDictionaryFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    to Study
    // ==============================
    fun transitionToStudy() {
        val action =
            DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToStudyFragment()
        findNavController().navigate(action)
    }

    // ==============================
    //    to Authentication
    // ==============================
    fun transitionToAuthentication() {
        clearAllDataUser()
        auth.signOut()
        val action =
            DictionaryOrStudyFragmentDirections.actionDictionaryOrStudyFragmentToAuthentication()
        findNavController().navigate(action)
    }

    // ==============================
    //    Clear all data User
    // ==============================
    private fun clearAllDataUser() {
        databaseDao.apply {
            CoroutineScope(Dispatchers.Default).launch {
                wordsDelete()
                phrasesDelete()
            }
        }
        SharedPreferences(this).initSharedPref(AUTH).edit().clear().apply()
        SharedPreferences(this).initSharedPref(STUDY_FORMAT).edit().clear().apply()
    }

    // ==============================
    //    ClickOnAccount
    // ==============================
    fun clickOnAccount() {
        if (visibility == View.INVISIBLE) {
            visibility = View.VISIBLE
            val args = DictionaryOrStudyArgs.fromBundle(requireArguments())
            binding.layoutAccount.also {
                it.tvNameSurname.text = "${args.userName} ${args.userSurname}"
                it.tvEmail.text = args.userEmail
                it.layoutAccount.visibility = visibility
            }
        } else {
            visibility = View.INVISIBLE
            binding.layoutAccount.layoutAccount.visibility = visibility
        }
    }
}
