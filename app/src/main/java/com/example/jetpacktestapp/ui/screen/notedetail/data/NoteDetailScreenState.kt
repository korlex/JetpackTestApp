package com.example.jetpacktestapp.ui.screen.notedetail.data

data class NoteDetailScreenState(
    var noteId: Long = 0,
    var noteName: String = "",
    var noteText: String = "",
    var noteState: Int = 0)