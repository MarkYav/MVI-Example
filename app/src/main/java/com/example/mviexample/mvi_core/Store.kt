package com.example.mviexample.mvi_core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class Store<S: State>(
    initialState: S
) : ViewModel() {

    var state by mutableStateOf<S>(initialState)
        protected set

    protected val events = Channel<Event>()
    val eventsFlow = events.receiveAsFlow()

}