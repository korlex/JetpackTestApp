package com.example.jetpacktestapp.ui.screen.notedetail

import android.util.Log
import com.example.jetpacktestapp.db.data.NoteDbData
import com.example.jetpacktestapp.repo.NotesRepository
import com.example.jetpacktestapp.ui.screen.notedetail.data.NoteDetailScreenState
import com.example.jetpacktestapp.ui.screen.notedetail.data.NoteDetailViewData
import com.example.jetpacktestapp.ui.screen.noteslist.data.NotesListScreenState
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

    fun addNote(noteName: String, noteText: String) {
        Log.d("TAG", "vm addNote called")
        job = viewModelScope.launch {

            //val isValidState = noteName.isNotEmpty() || noteText.isNotEmpty()
            val isValidState = noteName.isNotEmpty() || noteText.isNotEmpty()

            if(isValidState) {
                Log.d("TAG", "isValidState")

                withContext(Dispatchers.IO) {
                    val noteDbData = NoteDbData(
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


    fun changeName(newName: String) {
        _notesDetailScreenState.value = _notesDetailScreenState.value.copy(noteName = newName)
    }
}