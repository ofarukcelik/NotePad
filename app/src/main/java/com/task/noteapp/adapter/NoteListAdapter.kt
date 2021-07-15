package com.task.noteapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.R
import com.task.noteapp.model.Notes

class NoteListAdapter(var notes: ArrayList<Notes>) :
  RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var txtTitle: TextView = view.findViewById(R.id.txtNoteTitle)
    var txtDate: TextView = view.findViewById(R.id.txtDate)

    fun bindItem(note: Notes) {
      txtTitle.text = note.title
      txtDate.text = note.createdDate
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bindItem(notes[position])
  }

  override fun getItemCount(): Int {
    return notes.size
  }
}