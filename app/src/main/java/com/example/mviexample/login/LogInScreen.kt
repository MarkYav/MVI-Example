package com.example.mviexample.login

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.util.Reloadable
import kotlinx.coroutines.flow.collect

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = LogInViewModel()
) {
    val state by viewModel.state
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Log In") }
            )
        }
    ) {
        if (state.userInput.state is Reloadable.State.Loading) {
            TODO("show loading")
        }

        if (state.userInput.state is Reloadable.State.Error) {
            TODO("show error")
        }

        TODO("here go input fields")

        TODO("here goes the log in button")
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LogInEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}