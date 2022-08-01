package com.example.mviexample.login.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.util.UndefinedOutlinedTextField
import com.example.mviexample.R
import com.example.mviexample.login.LogInViewModel

@Composable
fun Fields(
    state: LogInState,
    viewModel: LogInViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // user email TextField
        UndefinedOutlinedTextField(
            field = state.userInput.data.email,
            label = R.string.email,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            onValueChange = { newValue ->
                //viewModel.handleEvent(event = SignInUiEvent.UpdateUserEmail(newValue))
                TODO()
            }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        // user password TextField
        UndefinedOutlinedTextField(
            field = state.userInput.data.password,
            label = R.string.password,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            onValueChange = { newValue ->
                //viewModel.handleEvent(event = SignInUiEvent.UpdateUserPassword(newValue))
                TODO()
            }
        )
    }
}