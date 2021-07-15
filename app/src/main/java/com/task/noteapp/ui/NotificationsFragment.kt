package com.task.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.task.noteapp.databinding.FragmentCustomNotesBinding

class NotificationsFragment : Fragment() {
  private lateinit var binding: FragmentCustomNotesBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCustomNotesBinding.inflate(inflater, container, false)
    return binding.root
  }
}