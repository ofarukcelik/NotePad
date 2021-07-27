package com.task.noteapp.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

@Entity(tableName = "note")
class NoteEntity(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
  @ColumnInfo(name = "title") var title: String,
  @ColumnInfo(name = "description") var description: String,
  @ColumnInfo(name = "image") val image: ByteArray?,
  @ColumnInfo(name = "createdDate") val createdDate: String,
  @ColumnInfo(name = "updatedDate") var updatedDate: String,
  @ColumnInfo(name = "secret") val secret: Int
)