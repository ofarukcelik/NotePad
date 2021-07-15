package com.task.noteapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.BaseFragment
import com.task.noteapp.adapter.NoteListAdapter
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.model.Notes

  class HomeFragment : BaseFragment() {
  private lateinit var binding: FragmentNotesBinding
  private lateinit var noteList: ArrayList<Notes>

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = FragmentNotesBinding.inflate(inflater, container, false)
    noteList = arrayListOf()
    getNotes()
    return binding.root
  }

  private fun getNotes() {
    try {
      val db= requireActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
      val cursor = db.rawQuery("SELECT * FROM notes ORDER BY id desc", null)
      while (cursor.moveToNext()) {
        val title = cursor.getString(cursor.getColumnIndex("title"))
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val createdDate = cursor.getString(cursor.getColumnIndex("createdDate"))
        noteList.add(Notes(title, id, createdDate))
      }
      binding.recylerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
      binding.recylerView.adapter = NoteListAdapter(noteList)
    }catch (e: Exception) {
      e.printStackTrace()
    }
  }
}