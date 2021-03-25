package com.example.jetpacktestapp

import com.example.jetpacktestapp.di.scopes.ActivityScope
import com.example.jetpacktestapp.ui.screen.notedetail.NoteDetailViewModel
import com.example.jetpacktestapp.ui.screen.noteslist.NotesListViewModel
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Provider

@Subcomponent
@ActivityScope
interface MainActivitySubComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance mainActivity: MainActivity): MainActivitySubComponent
    }

    fun notesListViewModelProvider(): Provider<NotesListViewModel>
    fun noteDetailViewModelProvider(): Provider<NoteDetailViewModel>
    fun inject(mainActivity: MainActivity)
}