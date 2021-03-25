package com.example.jetpacktestapp.ui.screen.notedetail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetpacktestapp.R
import com.example.jetpacktestapp.navigation.ScreenDestination
import com.example.jetpacktestapp.repo.NotesRepository
import com.example.jetpacktestapp.ui.screen.notedetail.data.NoteDetailScreenState
import com.example.jetpacktestapp.ui.theme.White900

object NoteDetailScreen : ScreenDestination(path = "NOTE_DETAIL_SCREEN")

@Composable
fun noteDetailScreen(
    navController: NavHostController,
    noteDetailViewModel: NoteDetailViewModel,
    noteId: Long?) {

    LaunchedEffect(key1 = "key") {  if(noteId != null && noteId != -1L) noteDetailViewModel.getNote(noteId) }
    val noteDetailScreenState = noteDetailViewModel.notesDetailScreenState.collectAsState().value
    val noteState = noteDetailScreenState.noteState
    val nameState = mutableStateOf(TextFieldValue(noteDetailScreenState.noteName))
    val textState = mutableStateOf(TextFieldValue(noteDetailScreenState.noteText))
    val context = LocalContext.current


    if(noteState == -1) {
        Scaffold(
            topBar = {
                toolBar(
                    backCallback = { navController.popBackStack() },
                    addNoteCallback = { noteDetailViewModel.addNote(nameState.value.text, textState.value.text) }) },


            content = { content(nameState = nameState, textState = textState) }
        )

        Toast.makeText(context, "sd", Toast.LENGTH_SHORT).show()
    }

    if(noteState == 0) {
        Scaffold(
            topBar = {
                toolBar(
                    backCallback = { navController.popBackStack() },
                    addNoteCallback = { noteDetailViewModel.addNote(nameState.value.text, textState.value.text) }) },


            content = { content(nameState = nameState, textState = textState) }
        )
    }


    if(noteState == 1) {
        navController.popBackStack()
    }

}



@Composable
fun toolBar(backCallback: () -> Unit, addNoteCallback: () -> Unit) {
    val backIconImage = painterResource(id = R.drawable.ic_baseline_arrow_back_24)
    val saveIconImage = painterResource(id = R.drawable.ic_baseline_check_24)
    val title = stringResource(id = R.string.note)

    TopAppBar(
        title = { Text(text = title) },
        contentColor = White900
    )


    TopAppBar(
        title = { Text(text = title) },
        contentColor = White900,
        navigationIcon = {
            IconButton(onClick = { backCallback.invoke() }) {
                Icon(
                    painter = backIconImage,
                    contentDescription = null
                )
            }
        },

        actions = {

            IconButton(onClick = {
                Log.d("TAG", "btnSave click")
                addNoteCallback.invoke()

            }) {
                Icon(
                    painter = saveIconImage,
                    contentDescription = null
                )
            }

        }


    )
}


@Composable
fun content(nameState: MutableState<TextFieldValue>, textState: MutableState<TextFieldValue>) {
    val nameLabel = stringResource(id = R.string.name)
    val textLabel = stringResource(id = R.string.text)

    Log.d("TAG", "Content called, name = ${nameState.value.text}")

    Column(modifier = Modifier.fillMaxSize()) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = nameState.value,
            onValueChange = { nameState.value = it },
            textStyle = TextStyle(fontSize = 16.sp),
            label = { Text(text = nameLabel) },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = White900
            )
        )

        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f))

        TextField(
            modifier = Modifier.fillMaxSize(),
            value = textState.value,
            onValueChange = { textState.value = it },
            textStyle = TextStyle(fontSize = 16.sp),
            label = { Text(text = textLabel) },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = White900
            )
        )

    }

}

