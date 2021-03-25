package com.example.jetpacktestapp.ui.screen.noteslist.data

import com.example.jetpacktestapp.R

data class NoteListViewData(
    val id: Long,
    val imgRes: Int = R.drawable.ic_baseline_note_24,
    val name: String,
    val text: String
)