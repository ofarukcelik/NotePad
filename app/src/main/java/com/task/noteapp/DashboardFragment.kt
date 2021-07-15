package com.task.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.task.noteapp.databinding.FragmentCreateNotesBinding

class DashboardFragment : Fragment() {

  private lateinit var binding: FragmentCreateNotesBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentCreateNotesBinding.inflate(inflater, container, false)
    binding.textDashboard.text = "Dashboard"
    return binding.root
  }
}