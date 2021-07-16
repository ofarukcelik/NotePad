package com.task.noteapp.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.task.noteapp.BaseFragment
import com.task.noteapp.adapter.NoteListAdapter
import com.task.noteapp.databinding.FragmentNotesBinding
import com.task.noteapp.event.AddSecretNoteEvent
import com.task.noteapp.event.NoteDeleteEvents
import com.task.noteapp.model.ListAdapterType
import com.task.noteapp.model.Notes
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseFragment() {
  private lateinit var binding: FragmentNotesBinding
  private lateinit var noteList: ArrayList<Notes>
  lateinit var db: SQLiteDatabase
  lateinit var adapter: NoteListAdapter
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
      db = requireActivity().openOrCreateDatabase("Notes", Context.MODE_PRIVATE, null)
      val cursor =
        db.rawQuery("SELECT * FROM notes WHERE secret = ? ORDER BY id desc", arrayOf("0"))
      while (cursor.moveToNext()) {
        val title = cursor.getString(cursor.getColumnIndex("title"))
        val id = cursor.getInt(cursor.getColumnIndex("id"))
        val createdDate = cursor.getString(cursor.getColumnIndex("createdDate"))
        val updatedDate = cursor.getString(cursor.getColumnIndex("updatedDate"))
        noteList.add(Notes(title, id, createdDate, updatedDate))
      }
      cursor.close()
      if (noteList.isEmpty()) {
        binding.imgDataNotFound.isVisible = true
      } else {
        binding.imgDataNotFound.isVisible = false
        binding.recylerView.layoutManager =
          LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = NoteListAdapter(noteList, requireContext(), ListAdapterType.MAIN)
        binding.recylerView.adapter = adapter
      }

    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun deleteNoteEvent(event: NoteDeleteEvents) {
    deleteNotes(event.selectedID)
  }

  private fun deleteNotes(id: Int) {
    val sqlString = "DELETE FROM notes WHERE id= ?"
    val statement = db.compileStatement(sqlString)
    statement.bindString(1, id.toString())
    statement.execute()
    noteList.remove(noteList.find { it.id == id })
    adapter.notifyDataSetChanged()
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  fun addSecretNoteEvent(event: AddSecretNoteEvent) {
    val sqlString = "UPDATE notes SET secret = ?  WHERE id= ? "
    val statement = db.compileStatement(sqlString)
    statement.bindString(1, "1")
    statement.bindString(2, event.id.toString())
    statement.execute()
    noteList.remove(noteList.find { it.id == event.id })
    adapter.notifyDataSetChanged()
  }


  override fun onStart() {
    super.onStart()
    EventBus.getDefault().register(this)
  }

  override fun onStop() {
    super.onStop()
    EventBus.getDefault().unregister(this)
  }
}