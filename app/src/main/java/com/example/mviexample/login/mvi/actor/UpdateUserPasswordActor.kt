package com.example.mviexample.login.mvi.actor

import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.login.domain.CheckPasswordErrorsUseCase
import com.example.mviexample.login.mvi.LogInEvent
import com.example.mviexample.mvi_core.Actor
import com.example.mviexample.util.Reloadable

class UpdateUserPasswordActor(
    private val checkPasswordErrorsUseCase: CheckPasswordErrorsUseCase
) : Actor<LogInInternalAction.UpdateUserPassword, LogInState, LogInInternalAction, LogInEvent> {

    override fun isApplicable(state: LogInState): Boolean {
        return when (state.userInput.state) {
            is Reloadable.State.Error,
            is Reloadable.State.Idle -> true

            is Reloadable.State.Loading -> false
        }
    }

    override fun LogInInternalAction.UpdateUserPassword.perform(
        state: LogInState,
        internalActionConsumer: (LogInInternalAction) -> Unit,
        eventConsumer: (LogInEvent) -> Unit
    ): LogInState {
        require(state.userInput.state !is Reloadable.State.Loading)

        return LogInState(
            userInput = state.userInput.copy(
                data = LogInItem(
                    email = checkPasswordErrorsUseCase(this.newPassword),
                    password = state.userInput.data.password
                )
            )
        )
    }

}