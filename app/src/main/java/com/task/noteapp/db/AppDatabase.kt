package com.task.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.task.noteapp.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

  abstract fun getDAO(): NoteDao
  companion object {
    private var dbINSTANCE: AppDatabase? = null

    fun getAppDB(context: Context): AppDatabase {
      if (dbINSTANCE == null) {
        dbINSTANCE = Room.databaseBuilder<AppDatabase>(
          context.applicationContext, AppDatabase::class.java, "DBNOTES"
        )
          .allowMainThreadQueries()
          .build()
      }
      return dbINSTANCE!!
    }
  }
}