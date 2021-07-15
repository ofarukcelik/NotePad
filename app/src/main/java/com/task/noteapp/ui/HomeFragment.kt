package com.task.noteapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.task.noteapp.databinding.FragmentNotesBinding

class HomeFragment : Fragment() {
  private lateinit var binding: FragmentNotesBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNotesBinding.inflate(inflater, container, false)
    binding.textHome.text = "Home"
    return binding.root
  }
}