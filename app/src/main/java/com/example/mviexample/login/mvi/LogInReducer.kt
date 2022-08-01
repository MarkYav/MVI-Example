package com.example.mviexample.login.mvi

import androidx.compose.runtime.MutableState
import com.example.mviexample.Repository
import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.util.InputField
import com.example.mviexample.util.Reloadable
import com.example.mviexample.util.result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LogInReducer(
    private val repository: Repository
) {
    operator fun invoke(
        internalAction: LogInInternalAction,
        state: MutableState<LogInState>,
        eventChannel: Channel<LogInEvent>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val currentState = state.value
            when(internalAction) {
                is LogInInternalAction.UpdateUserEmail -> {
                    state.value = LogInState(
                        userInput = currentState.userInput.copy(
                            data = LogInItem(
                                email = checkEmailErrors(internalAction.newEmail),
                                password = currentState.userInput.data.password
                            )
                        )
                    )
                }
                is LogInInternalAction.UpdateUserPassword -> {
                    state.value = LogInState(
                        userInput = currentState.userInput.copy(
                            data = LogInItem(
                                email = currentState.userInput.data.email,
                                password = checkPasswordErrors(internalAction.newPassword)
                            )
                        )
                    )
                }
                is LogInInternalAction.TryLogIn -> {
                    state.value = LogInState(
                        userInput = currentState.userInput.copy(
                            state = Reloadable.State.Loading
                        )
                    )
                    repository.logIn().result(
                        onSuccess = {
                            this@LogInReducer(
                                LogInInternalAction.SuccessLogIn,
                                state,
                                eventChannel
                            )
                        },
                        onFailure = {
                            this@LogInReducer(
                                LogInInternalAction.ErrorLogIn(e = it.error),
                                state,
                                eventChannel
                            )
                        }
                    )
                }
                is LogInInternalAction.ErrorLogIn -> {
                    state.value = LogInState(
                        userInput = currentState.userInput.copy(
                            state = Reloadable.State.Error(internalAction.e)
                        )
                    )
                }
                is LogInInternalAction.SuccessLogIn -> {
                    eventChannel.send(LogInEvent.ShowToast(message = "Successfully logged in!"))
                    //TODO("navigate to another screen")
                }
            }
        }
    }

    private fun checkEmailErrors(email: String): InputField {
        TODO()
    }

    private fun checkPasswordErrors(email: String): InputField {
        TODO()
    }
}