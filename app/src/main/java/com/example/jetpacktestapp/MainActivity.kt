package com.example.jetpacktestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpacktestapp.repo.NotesRepository
import com.example.jetpacktestapp.ui.screen.root
import com.example.jetpacktestapp.ui.theme.JetpackTestAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var notesRepository: NotesRepository
    lateinit var mainActivitySubComponent: MainActivitySubComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSubComponent()
        mainActivitySubComponent.inject(this)


        setContent {
            JetpackTestAppTheme {
                root(
                    mainActivitySubComponent = mainActivitySubComponent
                )
            }
        }
    }

    private fun initSubComponent() {
        mainActivitySubComponent = App
            .get()
            .appComponent
            .mainActivitySubComponent()
            .create(this)
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackTestAppTheme {
        Greeting("Android")
    }
}