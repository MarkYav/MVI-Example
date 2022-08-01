package com.example.mviexample.login.mvi

import com.example.mviexample.mvi_core.Action

sealed class LogInAction : Action() {
    object LogIn : LogInAction()
    data class UpdateUserEmail(val newEmail: String) : LogInAction()
    data class UpdateUserPassword(val newPassword: String) : LogInAction()
}