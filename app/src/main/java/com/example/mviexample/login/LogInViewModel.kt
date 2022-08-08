package com.example.mviexample.login

import androidx.lifecycle.viewModelScope
import com.example.mviexample.login.item.LogInItem
import com.example.mviexample.login.mvi.LogInAction
import com.example.mviexample.login.mvi.LogInInternalAction
import com.example.mviexample.login.mvi.LogInState
import com.example.mviexample.login.mvi.actor.*
import com.example.mviexample.mvi_core.Store
import com.example.mviexample.util.InputField
import com.example.mviexample.util.Reloadable
import kotlinx.coroutines.launch

class LogInViewModel(
    private val errorLogInActor: ErrorLogInActor,
    private val successLogInActor: SuccessLogInActor,
    private val tryLogInActor: TryLogInActor,
    private val updateUserEmailActor: UpdateUserEmailActor,
    private val updateUserPasswordActor: UpdateUserPasswordActor
) : Store<LogInState>(initialState = initialState) {

    companion object {
        private val initialState = LogInState(
            userInput = Reloadable(
                data = LogInItem(
                    email = InputField(value = ""),
                    password = InputField(value = "")
                ),
                state = Reloadable.State.Idle
            )
        )
    }

    fun handleAction(action: LogInAction) {
        when (action) {
            is LogInAction.LogIn -> reduce(internalAction = LogInInternalAction.TryLogIn)
            is LogInAction.UpdateUserEmail -> reduce(internalAction = LogInInternalAction.UpdateUserEmail(action.newEmail))
            is LogInAction.UpdateUserPassword -> reduce(internalAction = LogInInternalAction.UpdateUserPassword(action.newPassword),)
        }
    }

    private fun reduce(internalAction: LogInInternalAction) {
        val actor = when (internalAction) {
            is LogInInternalAction.ErrorLogIn -> errorLogInActor
            is LogInInternalAction.SuccessLogIn -> successLogInActor
            is LogInInternalAction.TryLogIn -> tryLogInActor
            is LogInInternalAction.UpdateUserEmail -> updateUserEmailActor
            is LogInInternalAction.UpdateUserPassword -> updateUserPasswordActor
        }

        state =
            if (actor.isApplicable(state)) {
                with(actor) {
                    internalAction.perform(state, this@LogInViewModel::reduce, /*this@LogInViewModel.events::send*/) {
                        viewModelScope.launch { events.send(it) }
                    }
                }
            } else state // we do not change the state (or we can possibly throw an exception)
    }

}