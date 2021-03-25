package com.example.jetpacktestapp.ui.screen.noteslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import com.example.jetpacktestapp.R
import com.example.jetpacktestapp.navigation.ScreenDestination
import com.example.jetpacktestapp.ui.screen.noteslist.data.NoteListViewData
import com.example.jetpacktestapp.ui.screen.noteslist.data.NotesListScreenState
import com.example.jetpacktestapp.ui.theme.Purple200
import com.example.jetpacktestapp.ui.theme.Purple700
import com.example.jetpacktestapp.ui.theme.Red900
import com.example.jetpacktestapp.ui.theme.White900

object NotesListScreen : ScreenDestination(path = "NOTES_LIST_SCREEN")

@Composable
fun notesListScreen(navController: NavHostController, notesListViewModel: NotesListViewModel) {

    LaunchedEffect(key1 = "key") { notesListViewModel.getNotes() }
    val notesListScreenState = notesListViewModel.viewState.collectAsState().value

    //val notesStr2: List<String> by notesListViewModel._viewState.collectAsState(emptyList<String>())
    //val favorites by postsRepository.observeFavorites().collectAsState(setOf())

    Scaffold(
        topBar = { toolBar() },

        content = { content(navController = navController, notesListScreenState = notesListScreenState) },

        floatingActionButton = { fab(navController = navController) }
    )

}


@Composable
fun toolBar() {
    val title = stringResource(id = R.string.notes_list)
    TopAppBar(
        title = { Text(text = title) },
        contentColor = White900
    )
}

@Composable
fun content(navController: NavHostController, notesListScreenState: NotesListScreenState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        when {
            notesListScreenState.isLoading -> loadingContent()
            notesListScreenState.noteLists.isNotEmpty() -> notEmptyContent(navController = navController, notesListViewDataList = notesListScreenState.noteLists)
            notesListScreenState.noteLists.isEmpty() -> emptyContent()
        }
    }
}

@Composable
fun notEmptyContent(navController: NavHostController, notesListViewDataList: List<NoteListViewData>) {
    noteItemsList(
        navController = navController,
        notesListViewDataList = notesListViewDataList)
}

@Composable
fun emptyContent() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        text = stringResource(id = R.string.notes_list_empty),
        textAlign = TextAlign.Center,
        fontSize = 16.sp)
}

@Composable
fun loadingContent() {
    CircularProgressIndicator()
}

@Composable
fun bottomSheet() {
    val cancelBtnImage = painterResource(id = R.drawable.ic_baseline_cancel_24)
    val cancelBtnText = stringResource(id = R.string.cancel)

    val deleteBtnImage = painterResource(id = R.drawable.ic_baseline_delete_24)
    val deleteBtnText = stringResource(id = R.string.delete)


    Column(modifier = Modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.padding(start = 8.dp),
                painter = cancelBtnImage,
                contentDescription = null)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = cancelBtnText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.padding(start = 8.dp),
                painter = deleteBtnImage,
                colorFilter = ColorFilter.tint(color = Red900),
                contentDescription = null)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = deleteBtnText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(color = Red900)
            )
        }

    }
}



@Composable
fun noteItemsList(navController: NavHostController, notesListViewDataList: List<NoteListViewData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
        items(notesListViewDataList.size) { index ->
            if(index != 0) Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f))
            noteItem(
                navController = navController,
                noteListViewData = notesListViewDataList[index])
        }
    }
}

@Composable
fun noteItem(navController: NavHostController, noteListViewData: NoteListViewData) {
    val image = painterResource(id = R.drawable.ic_baseline_note_24)
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { navController.navigate("NOTE_DETAIL_SCREEN/${noteListViewData.id}") })) {

        Row(
            modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(32.dp),
                painter = image,
                colorFilter = ColorFilter.tint(color = Purple200),
                contentDescription = null)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = noteListViewData.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            text = noteListViewData.text,
            fontSize = 16.sp)
    }
}

@Composable
fun fab(navController: NavHostController) {
    val image = painterResource(id = R.drawable.ic_baseline_add_24)
    FloatingActionButton(
        backgroundColor = Purple700,
        onClick = { navController.navigate("NOTE_DETAIL_SCREEN/${0}") }) {

        Image(
            painter = image,
            colorFilter = ColorFilter.tint(color = White900),
            contentDescription = null)

    }
}


/**
 * Previews..
 */

//@Preview("Home screen, open drawer")
//@Composable
//private fun previewNoteItem() {
//    noteItem(noteViewData = NoteViewData(name = "Nothing special", text = "Lorem ipsum dolor sit amet "))
//}
//
//@Preview("Home screen, open drawer")
//@Composable
//private fun previewNoteItemsLIst() {
//    val notesViewDataList = listOf<NoteViewData>(
//        NoteViewData(name = "Nothing special", text = "Lorem ipsum dolor sit amet "),
//        NoteViewData(name = "Recipe", text = "1) Boil water 2) throw spagetti into it 3) wait 7 minutes"),
//        NoteViewData(name = "SelfIdentification", text = "Who am I ? A tree , a rock or something else ? ")
//    )
//
//    noteItemsList(notesViewDataList = notesViewDataList)
//}