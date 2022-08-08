package com.example.mviexample.login.mvi.actor

import com.example.mviexample.Repository
import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.mvi_core.Actor
import com.example.mviexample.util.Reloadable
import com.example.mviexample.util.result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TryLogInActor(
    private val repository: Repository
) : Actor<LogInInternalAction.TryLogIn, LogInState, LogInInternalAction, LogInEvent> {

    override fun isApplicable(state: LogInState): Boolean {
        return when (state.userInput.state) {
            is Reloadable.State.Error,
            is Reloadable.State.Idle -> true

            is Reloadable.State.Loading -> false
        }
    }

    override fun LogInInternalAction.TryLogIn.perform(
        state: LogInState,
        internalActionConsumer: (LogInInternalAction) -> Unit,
        eventConsumer: (LogInEvent) -> Unit
    ): LogInState {
        require(state.userInput.state !is Reloadable.State.Loading)

        CoroutineScope(Dispatchers.IO).launch {
            repository.logIn().result(
                onSuccess = { internalActionConsumer(LogInInternalAction.SuccessLogIn) },
                onFailure = { internalActionConsumer(LogInInternalAction.ErrorLogIn(e = it.error)) }
            )
        }

        return LogInState(
            userInput = state.userInput.copy(
                state = Reloadable.State.Loading
            )
        )
    }

}