package com.task.noteapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.task.noteapp.entity.NoteEntity

@Dao
interface NoteDao {
  @Query("SELECT * FROM note WHERE secret = '0' ORDER BY id DESC")
  fun getNotes(): List<NoteEntity>

  @Query("SELECT * FROM note WHERE id =:id")
  fun getNoteById(id: Int): NoteEntity

  @Insert
  fun insertNote(noteEntity: NoteEntity)

  @Query("SELECT * FROM note WHERE secret = '1' ORDER BY id DESC")
  fun getSecretNotes(): List<NoteEntity>

  @Query("DELETE FROM note WHERE id = :id")
  fun deleteById(id: Int)

  @Update
  fun update(noteEntity: NoteEntity)

  @Query("UPDATE note SET secret =:value WHERE id =:id")
  fun updateSecretById(value: Int, id: Int)
}