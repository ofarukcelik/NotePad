package com.task.noteapp

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

open class BaseFragment: Fragment() {

  fun navigate(navigationID: Int) {
    Navigation.findNavController(requireView()).navigate(navigationID)
  }
}