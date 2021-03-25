package com.example.jetpacktestapp.ui.screen.noteslist.data

data class NotesListScreenState(
    var noteLists: List<NoteListViewData> = emptyList(),
    var isLoading: Boolean = false
)