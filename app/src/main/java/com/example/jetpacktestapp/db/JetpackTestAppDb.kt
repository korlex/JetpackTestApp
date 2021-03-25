package com.example.jetpacktestapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpacktestapp.db.dao.NoteDao
import com.example.jetpacktestapp.db.data.NoteDbData

private const val DB_NAME = "JETPACK_TEST_APP_DB"

@Database(entities = [NoteDbData::class], version = 1)
abstract class JetpackTestAppDb : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        fun buildDb(context: Context): JetpackTestAppDb =
            Room.databaseBuilder(context, JetpackTestAppDb::class.java, DB_NAME).build()
    }
}