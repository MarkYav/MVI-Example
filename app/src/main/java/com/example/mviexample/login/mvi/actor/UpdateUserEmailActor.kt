package com.example.mviexample.login.mvi.actor

import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.login.domain.CheckEmailErrorsUseCase
import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.mvi_core.Actor
import com.example.mviexample.util.Reloadable

class UpdateUserEmailActor(
    private val checkEmailErrorsUseCase: CheckEmailErrorsUseCase
) : Actor<LogInInternalAction.UpdateUserEmail, LogInState, LogInInternalAction, LogInEvent> {

    override fun isApplicable(state: LogInState): Boolean {
        return when (state.userInput.state) {
            is Reloadable.State.Error,
            is Reloadable.State.Idle -> true

            is Reloadable.State.Loading -> false
        }
    }

    override fun LogInInternalAction.UpdateUserEmail.perform(
        state: LogInState,
        internalActionConsumer: (LogInInternalAction) -> Unit,
        eventConsumer: (LogInEvent) -> Unit
    ): LogInState {
        require(state.userInput.state !is Reloadable.State.Loading)

        return LogInState(
            userInput = state.userInput.copy(
                data = LogInItem(
                    email = checkEmailErrorsUseCase(this.newEmail),
                    password = state.userInput.data.password
                )
            )
        )
    }
}