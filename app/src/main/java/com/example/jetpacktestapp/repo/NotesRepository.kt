package com.example.jetpacktestapp.repo

import com.example.jetpacktestapp.db.JetpackTestAppDb
import com.example.jetpacktestapp.db.data.NoteDbData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(private val jetpackTestAppDb: JetpackTestAppDb) {

    fun addNote(noteDbData: NoteDbData) {
        jetpackTestAppDb
            .getNoteDao()
            .addNote(noteDbData)
    }

    fun getNote(noteId: Long): NoteDbData {
        return jetpackTestAppDb
            .getNoteDao()
            .getNote(noteId)
    }

    fun getNotes(): List<NoteDbData> {
        return jetpackTestAppDb
            .getNoteDao()
            .getNotes()
    }

    fun deleteNotes(noteIdList: List<Long>) {
        jetpackTestAppDb
            .getNoteDao()
            .deleteNotes(noteIdList)
    }
}