package com.example.mviexample.login.mvi.actor

import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.mvi_core.Actor
import com.example.mviexample.util.Reloadable

class SuccessLogInActor : Actor<LogInInternalAction.SuccessLogIn, LogInState, LogInInternalAction, LogInEvent> {

    override fun isApplicable(state: LogInState): Boolean {
        return when (state.userInput.state) {
            is Reloadable.State.Loading -> true

            is Reloadable.State.Error,
            is Reloadable.State.Idle -> false
        }
    }

    override fun LogInInternalAction.SuccessLogIn.perform(
        state: LogInState,
        internalActionConsumer: (LogInInternalAction) -> Unit,
        eventConsumer: (LogInEvent) -> Unit
    ): LogInState {
        require(state.userInput.state !is Reloadable.State.Error)
        require(state.userInput.state !is Reloadable.State.Idle)

        eventConsumer(LogInEvent.ShowToast(message = "Successfully logged in!"))
        return state
    }

}