package com.example.mviexample.mvi_core

// Internal Action Actor
interface Actor<out I : InternalAction, S : State, A : InternalAction, E : Event> {

    fun isApplicable(state: S): Boolean

    fun @UnsafeVariance I.perform(
        state: S,
        internalActionConsumer: (A) -> Unit,
        eventConsumer: (E) -> Unit
    ): S

}