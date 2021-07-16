package com.task.noteapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.BitmapFactory
import android.os.Bundle
import com.task.noteapp.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {
  private lateinit var binding: ActivityDetailBinding
  private lateinit var db: SQLiteDatabase
  private var selectedID: Int = 0
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDetailBinding.inflate(layoutInflater)
    setContentView(binding.root)
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
    db = openOrCreateDatabase("Notes", MODE_PRIVATE, null)
    val cursor = db.rawQuery("SELECT * FROM notes WHERE id = ?", arrayOf(selectedID.toString()))
    while (cursor.moveToNext()) {
      binding.etTitle.setText(cursor.getString(cursor.getColumnIndex("title")))
      binding.etDescription.setText(cursor.getString(cursor.getColumnIndex("description")))
      val byteArray = cursor.getBlob(cursor.getColumnIndex("image"))
      if (byteArray != null) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        binding.imgSelected.setImageBitmap(bitmap)
      }
    }
    cursor.close()
  }

  private fun update() {
    val sqlString = "UPDATE notes SET title = ?, description = ?, updatedDate = ? WHERE id= ? "
    val statement = db.compileStatement(sqlString)
    statement.bindString(1, binding.etTitle.text.toString())
    statement.bindString(2, binding.etDescription.text.toString())
    statement.bindString(3, getDate())
    statement.bindString(4, selectedID.toString())
    statement.execute()
    intentMainActivity()
  }

  private fun intentMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    startActivity(intent)
  }


}