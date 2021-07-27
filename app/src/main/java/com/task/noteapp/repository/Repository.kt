package com.task.noteapp.repository

import com.task.noteapp.db.NoteDao
import com.task.noteapp.entity.NoteEntity
import javax.inject.Inject

class Repository @Inject constructor(private val noteDao: NoteDao) {

  fun getNotes(): List<NoteEntity> {
    return noteDao.getNotes()
  }

  fun insertNote(noteEntity: NoteEntity) {
    noteDao.insertNote(noteEntity)
  }

  fun getSecretNotes(): List<NoteEntity> {
    return noteDao.getSecretNotes()
  }

  fun deleteById(id: Int) {
    noteDao.deleteById(id)
  }

  fun update(noteEntity: NoteEntity) {
    noteDao.update(noteEntity)
  }

  fun updateSecretById(value: Int, id: Int) {
    noteDao.updateSecretById(value, id)
  }

  fun getNoteById(id: Int): NoteEntity {
    return noteDao.getNoteById(id)
  }
}