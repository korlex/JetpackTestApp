package com.example.jetpacktestapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jetpacktestapp.db.data.NoteDbData

@Dao
abstract class NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addNote(noteDbData: NoteDbData)

    @Query("SELECT * FROM notedbdata WHERE id = :noteId")
    abstract fun getNote(noteId: Long): NoteDbData

    @Query("SELECT * FROM notedbdata")
    abstract fun getNotes(): List<NoteDbData>

    @Query("delete from notedbdata WHERE id = :noteId")
    abstract fun deleteNote(noteId: Long)

}