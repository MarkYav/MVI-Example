package com.example.mviexample.login.mvi.actor

import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.mvi_core.Actor
import com.example.mviexample.util.Reloadable

class ErrorLogInActor : Actor<LogInInternalAction.ErrorLogIn, LogInState, LogInInternalAction, LogInEvent> {

    override fun isApplicable(state: LogInState): Boolean {
        return when (state.userInput.state) {
            is Reloadable.State.Loading -> true

            is Reloadable.State.Error,
            is Reloadable.State.Idle -> false
        }
    }

    override fun LogInInternalAction.ErrorLogIn.perform(
        state: LogInState,
        internalActionConsumer: (LogInInternalAction) -> Unit,
        eventConsumer: (LogInEvent) -> Unit
    ): LogInState {
        require(state.userInput.state !is Reloadable.State.Error)
        require(state.userInput.state !is Reloadable.State.Idle)

        return LogInState(
            userInput = state.userInput.copy(
                state = Reloadable.State.Error(this.e)
            )
        )
    }

}