package com.example.jetpacktestapp.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.jetpacktestapp.MainActivitySubComponent
import com.example.jetpacktestapp.ui.screen.notedetail.NoteDetailScreen
import com.example.jetpacktestapp.ui.screen.notedetail.noteDetailScreen
import com.example.jetpacktestapp.ui.screen.noteslist.NotesListScreen
import com.example.jetpacktestapp.ui.screen.noteslist.notesListScreen

@Composable
fun root(mainActivitySubComponent: MainActivitySubComponent) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NotesListScreen.path) {

        //arguments = listOf(navArgument("userId") { defaultValue = "me" })

        composable(route = NotesListScreen.path) { backStackEntry ->
            val vm = mainActivitySubComponent.notesListViewModelProvider().get()
            notesListScreen(
                navController = navController,
                notesListViewModel = vm)
        }


        composable(
            route = "${NoteDetailScreen.path}/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.LongType
            })
        ) { backStackEntry ->

            val vm = mainActivitySubComponent.noteDetailViewModelProvider().get()
            noteDetailScreen(
                navController = navController,
                noteDetailViewModel = vm,
                backStackEntry.arguments?.getLong("noteId"))
        }
    }
}