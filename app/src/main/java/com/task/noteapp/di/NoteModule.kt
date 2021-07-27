package com.task.noteapp.di

import android.app.Application
import android.content.Context
import com.task.noteapp.db.AppDatabase
import com.task.noteapp.db.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

  @Singleton
  @Provides
  fun getNoteDB(context: Application): AppDatabase {
    return AppDatabase.getAppDB(context)
  }

  @Singleton
  @Provides
  fun getDao(noteDB: AppDatabase): NoteDao {
    return noteDB.getDAO()
  }
}