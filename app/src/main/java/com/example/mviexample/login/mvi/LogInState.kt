package com.example.mviexample.login.mvi

import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.mvi_core.State
import com.example.mviexample.util.Reloadable

data class LogInState(
    val userInput: Reloadable<LogInItem>
) : State() {

    val isLogInButtonActive =
        userInput.data.email.error == null &&
        userInput.data.password.error == null
}