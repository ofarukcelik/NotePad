package com.task.noteapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentLoginBinding

class LoginFragment: BaseFragment() {
  private lateinit var binding: FragmentLoginBinding
  private lateinit var sharedPreferences: SharedPreferences
  private val CACHE_KEY: String = "cache"

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentLoginBinding.inflate(inflater, container, false)
    sharedPreferences = requireContext().getSharedPreferences(CACHE_KEY, Context.MODE_PRIVATE)
    binding.btnCreatePass.setOnClickListener { createPass() }
    binding.btnOk.setOnClickListener {
      checkPassword()
    }
    if (passwordIsCreated()) {
      binding.lnrCreatePass.isVisible = false
      binding.lnrEnterPass.isVisible = true
    } else {
      binding.lnrCreatePass.isVisible = true
      binding.lnrEnterPass.isVisible = false
    }
    return binding.root
  }

  private fun createPass() {
    var password = binding.etCreatedPass.text.toString()
    if (password.isEmpty()) {
      Toast.makeText(requireContext(), getString(R.string.empty_value_error), Toast.LENGTH_LONG).show()
      return
    }
    savePass(password)
    setPasswordIsCreated(true)
    navigateSecretNotes()
  }

  private fun navigateSecretNotes() {
    navigate(R.id.action_navigation_custom_notes_to_navigation_secret_notes)
  }

  private fun checkPassword() {
    if (binding.etCheckPass.text.toString() == getPassword()) {
      navigateSecretNotes()
    } else {
      Toast.makeText(requireContext(), "Yanlış Parola! Lütfen Daha Sonra Tekrar Deneyin.", Toast.LENGTH_LONG).show()
    }
  }

  private fun savePass(password: String) {
    sharedPreferences.edit().putString("password", password).apply()
  }

  private fun getPassword(): String? {
    return requireContext().getSharedPreferences(CACHE_KEY, 0).getString("password", "")
  }

  private fun passwordIsCreated(): Boolean {
    return requireContext().getSharedPreferences(CACHE_KEY,0).getBoolean("passwordIsCreated", false)
  }

  private fun setPasswordIsCreated(value: Boolean) {
    sharedPreferences.edit().putBoolean("passwordIsCreated", value).apply()
  }
}