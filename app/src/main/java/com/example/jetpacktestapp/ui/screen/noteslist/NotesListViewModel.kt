package com.example.jetpacktestapp.ui.screen.noteslist

import com.example.jetpacktestapp.repo.NotesRepository
import com.example.jetpacktestapp.ui.screen.noteslist.data.NoteListViewData
import com.example.jetpacktestapp.ui.screen.noteslist.data.NotesListScreenState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class NotesListViewModel @Inject constructor(private val notesRepository: NotesRepository) {

    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Main + Job())
    private var job: Job? = null

    val _viewState: MutableStateFlow<NotesListScreenState> = MutableStateFlow(NotesListScreenState())
    val viewState = _viewState.asStateFlow()


    fun getNotes() {
        job = viewModelScope.launch {
            _viewState.value = _viewState.value.copy(isLoading = true)

            delay(500)

            val notesList = withContext(Dispatchers.IO) {
                notesRepository
                    .getNotes()
                    .map { NoteListViewData(id = it.id, name = it.name, text = it.text) }
            }
            _viewState.value = _viewState.value.copy(noteLists = notesList, isLoading = false)
        }
    }

}