package com.example.jetpacktestapp.extensions

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.jetpacktestapp.navigation.ScreenDestination
import com.example.jetpacktestapp.repo.NotesRepository

inline fun <reified T : ScreenDestination> NavGraphBuilder.notesComposable(
    destination: T,
    notesRepository: NotesRepository,
    crossinline content: @Composable (NavBackStackEntry, NotesRepository) -> Unit) {

    composable(destination.path, destination.arguments) { navEntry ->
        when(destination.path) {
            "NOTES_LIST_SCREEN" -> content(navEntry, notesRepository)
            "NOTE_DETAIL_SCREEN" -> content(navEntry, notesRepository)
        }
    }
}