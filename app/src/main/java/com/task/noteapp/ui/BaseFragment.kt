package com.task.noteapp.ui

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment: Fragment() {

  fun navigate(navigationID: Int) {
    Navigation.findNavController(requireView()).navigate(navigationID)
  }
}