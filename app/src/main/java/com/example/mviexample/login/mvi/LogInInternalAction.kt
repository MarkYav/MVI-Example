package com.example.mviexample.login.mvi

import com.example.mviexample.mvi_core.InternalAction

sealed class LogInInternalAction : InternalAction() {
    data class UpdateUserEmail(val newEmail: String) : LogInInternalAction()
    data class UpdateUserPassword(val newPassword: String) : LogInInternalAction()
    object TryLogIn : LogInInternalAction()
    data class ErrorLogIn(val e: Exception) : LogInInternalAction()
    object SuccessLogIn : LogInInternalAction()
}