package com.example.mviexample.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mviexample.Repository
import com.example.mviexample.util.InputField
import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.login.mvi.*
import com.example.mviexample.util.Reloadable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class LogInViewModel : ViewModel() {
    private val _state: MutableState<LogInState> = mutableStateOf(
        LogInState(
            userInput = Reloadable(
                data = LogInItem(
                    email = InputField(value = ""),
                    password = InputField(value = "")
                ),
                state = Reloadable.State.Idle
            )
        )
    )
    val state: androidx.compose.runtime.State<LogInState> = _state
    private val _events = Channel<LogInEvent>()
    val events = _events.receiveAsFlow()

    val logInReducer = LogInReducer(
        repository = Repository()
    )

    fun reduce(action: LogInAction) {
        when (action) {
            is LogInAction.LogIn -> {
                logInReducer(
                    internalAction = LogInInternalAction.TryLogIn,
                    state = _state,
                    eventChannel = _events
                )
            }
            is LogInAction.UpdateUserEmail -> logInReducer(
                internalAction = LogInInternalAction.UpdateUserEmail(action.newEmail),
                state = _state,
                eventChannel = _events
            )
            is LogInAction.UpdateUserPassword -> logInReducer(
                internalAction = LogInInternalAction.UpdateUserPassword(action.newPassword),
                state = _state,
                eventChannel = _events
            )
        }
    }
}