package com.example.jetpacktestapp.ui.screen.notedetail

import android.util.Log
import com.example.jetpacktestapp.db.data.NoteDbData
import com.example.jetpacktestapp.repo.NotesRepository
import com.example.jetpacktestapp.ui.screen.notedetail.data.NoteDetailScreenState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NoteDetailViewModel @Inject constructor(private val notesRepository: NotesRepository) {

    val _notesDetailScreenState: MutableStateFlow<NoteDetailScreenState> = MutableStateFlow(NoteDetailScreenState())
    val notesDetailScreenState = _notesDetailScreenState.asStateFlow()

    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private var job: Job? = null

    fun getNote(noteId: Long) {
        job = viewModelScope.launch {
            val noteDbData = withContext(Dispatchers.IO) { notesRepository.getNote(noteId) }
            _notesDetailScreenState.value = NoteDetailScreenState(
                noteId = noteDbData.id,
                noteName = noteDbData.name,
                noteText = noteDbData.text,
                noteState = 0
            )
        }
    }

    fun addNote(noteId: Long, noteName: String, noteText: String) {
        Log.d("TAG", "vm addNote called")
        job = viewModelScope.launch {

            //val isValidState = noteName.isNotEmpty() || noteText.isNotEmpty()
            val isValidState = noteName.isNotEmpty() || noteText.isNotEmpty()

            if(isValidState) {
                Log.d("TAG", "isValidState")

                withContext(Dispatchers.IO) {
                    val noteDbData = NoteDbData(
                        id = noteId,
                        name = noteName,
                        text = noteText)

                    notesRepository.addNote(noteDbData)
                }

                _notesDetailScreenState.value = _notesDetailScreenState.value.copy(noteState = 1)
            }

            if(!isValidState) {
                Log.d("TAG", "!isValidState")

                _notesDetailScreenState.value = _notesDetailScreenState.value.copy(noteState = -1)
            }
        }
    }

    fun delNote(noteId: Long) {
        job = viewModelScope.launch {
            withContext(Dispatchers.IO) { notesRepository.deleteNote(noteId = noteId) }
            _notesDetailScreenState.value = _notesDetailScreenState.value.copy(noteState = 1)
        }
    }




}