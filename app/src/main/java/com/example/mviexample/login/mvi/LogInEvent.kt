package com.example.mviexample.login.mvi

import com.example.mviexample.mvi_core.Event

sealed class LogInEvent : Event() {
    data class ShowToast(val message: String) : LogInEvent()
}