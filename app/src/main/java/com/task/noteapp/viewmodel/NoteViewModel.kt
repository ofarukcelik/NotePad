package com.task.noteapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.task.noteapp.entity.NoteEntity
import com.task.noteapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

  private var notes: MutableLiveData<List<NoteEntity>> = MutableLiveData()
  private var secretNotes: MutableLiveData<List<NoteEntity>> = MutableLiveData()
  private var selectedNote: MutableLiveData<NoteEntity> = MutableLiveData()

  fun getNotesObserver(): MutableLiveData<List<NoteEntity>> {
    notes.postValue(repository.getNotes())
    return notes
  }

  fun getNoteByIdObserver(id: Int): MutableLiveData<NoteEntity> {
    selectedNote.postValue(repository.getNoteById(id))
    return selectedNote
  }

  fun insertNote(noteEntity: NoteEntity) {
    repository.insertNote(noteEntity)
  }

  fun getSecretNoteObserver(): MutableLiveData<List<NoteEntity>> {
    secretNotes.postValue(repository.getSecretNotes())
    return secretNotes
  }

  fun deleteById(id: Int) {
    repository.deleteById(id)
    initNotes()
  }

  fun update(noteEntity: NoteEntity) {
    repository.update(noteEntity)
  }

  fun updateSecretById(value: Int, id: Int) {
    repository.updateSecretById(value, id)
    initNotes()
  }

  private fun initNotes() {
    secretNotes.postValue(repository.getSecretNotes())
    notes.postValue(repository.getNotes())
  }
}