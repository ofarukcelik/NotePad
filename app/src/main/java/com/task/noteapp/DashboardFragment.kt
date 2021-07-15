package com.task.noteapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.R
import com.task.noteapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

  private lateinit var binding: FragmentDashboardBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentDashboardBinding.inflate(inflater, container, false)
    binding.textDashboard.text = "Dashboard"
    return binding.root
  }
}