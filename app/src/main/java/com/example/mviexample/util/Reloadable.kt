package com.example.mviexample.util

data class Reloadable<T>(val data: T, val state: State) {

    sealed class State {
        object Idle : State()
        object Loading : State()
        data class Error(val e: Exception) : State()
    }
}