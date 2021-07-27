package com.task.noteapp.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.task.noteapp.databinding.ActivityDetailBinding
import com.task.noteapp.entity.NoteEntity
import com.task.noteapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailActivity : BaseActivity() {
  private lateinit var binding: ActivityDetailBinding
  lateinit var note: NoteEntity
  lateinit var viewModel: NoteViewModel
  private var selectedID: Int = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)
    viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    selectedID = intent.getIntExtra("selectedNoteID", 1)
    getDetail(selectedID)
    binding.btnUpdate.setOnClickListener { update() }
  }

  private fun getDate(): String {
    return SimpleDateFormat("dd.MM.yyyy").format(Date())
  }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }

  private fun getDetail(selectedID: Int) {
    viewModel.getNoteByIdObserver(selectedID).observe(this, { t ->
      note = t
      binding.etTitle.setText(t.title)
      binding.etDescription.setText(t.description)
      val byteArray = t.image
      if (byteArray != null) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.imgSelected.setImageBitmap(bitmap)
      }
    })
  }

  private fun update() {
    note.title = binding.etTitle.text.toString()
    note.description = binding.etDescription.text.toString()
    note.updatedDate = getDate()
    viewModel.update(note)
    intentMainActivity()
  }

  private fun intentMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
  }


}