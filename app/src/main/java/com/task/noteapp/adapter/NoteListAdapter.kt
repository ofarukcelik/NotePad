package com.task.noteapp.adapter


import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.noteapp.ui.DetailActivity
import com.task.noteapp.R
import com.task.noteapp.entity.NoteEntity
import com.task.noteapp.event.DeleteNoteEvent
import com.task.noteapp.event.MoveToPublicNotesEvent
import com.task.noteapp.event.MoveToSecretNotesEvent
import com.task.noteapp.util.ListAdapterType
import org.greenrobot.eventbus.EventBus


class NoteListAdapter(
  var notes: List<NoteEntity>,
  var context: Context,
  var type: ListAdapterType
) :
  RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

  class ViewHolder(var view: View) : RecyclerView.ViewHolder(view),
    View.OnCreateContextMenuListener {
    var txtTitle: TextView = view.findViewById(R.id.txtNoteTitle)
    var txtDate: TextView = view.findViewById(R.id.txtDate)
    var icNote: ImageView = view.findViewById(R.id.icNote)
    var id: Int = 0
    var type: ListAdapterType = ListAdapterType.MAIN

    init {
      view.setOnCreateContextMenuListener(this)
    }

    fun bindItem(note: NoteEntity, listAdapterType: ListAdapterType) {
      txtTitle.text = note.title
      txtDate.text = note.createdDate
      id = note.id
      type = listAdapterType
    }

    override fun onCreateContextMenu(
      menu: ContextMenu?,
      v: View?,
      menuInfo: ContextMenu.ContextMenuInfo?
    ) {
      val delete: MenuItem = menu!!.add(Menu.NONE, 1, 1, "Sil")
      delete.setOnMenuItemClickListener(contextMenuItemClickListener)

      if (type == ListAdapterType.MAIN) {
        val addSecretNotes: MenuItem = menu.add(Menu.NONE, 2, 2, "Gizli Notlara Ekle")
        addSecretNotes.setOnMenuItemClickListener(contextMenuItemClickListener)
      } else {
        val removeSecretNotes: MenuItem = menu.add(Menu.NONE, 3, 3, "Gizli Notlardan Çıkar")
        removeSecretNotes.setOnMenuItemClickListener(contextMenuItemClickListener)
      }
    }

    private val contextMenuItemClickListener: MenuItem.OnMenuItemClickListener =
      object : MenuItem.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem): Boolean {
          when (item.itemId) {
            1 -> {
              EventBus.getDefault().post(DeleteNoteEvent(id))
              return true
            }
            2 -> {
              EventBus.getDefault().post(MoveToSecretNotesEvent(id))
              return true
            }
            3 -> {
              EventBus.getDefault().post(MoveToPublicNotesEvent(id))
              return true
            }
          }
          return false
        }
      }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindItem(notes[position], type)
    if (!notes[position].updatedDate.isNullOrEmpty())
      holder.icNote.setImageDrawable(context.getDrawable(R.drawable.ic_updated))
    holder.itemView.setOnClickListener {
      intentDetail(notes[position].id)
    }
  }

  override fun getItemCount(): Int {
    return notes.size
  }

  private fun intentDetail(selectedNoteID: Int) {
    val intent = Intent(context, DetailActivity::class.java)
    intent.putExtra("selectedNoteID", selectedNoteID)
    context.startActivity(intent)
  }

}