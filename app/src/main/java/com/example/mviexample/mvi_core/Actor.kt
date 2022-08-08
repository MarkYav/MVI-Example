package com.example.mviexample.mvi_core

// Internal Action Actor
/**
 * `Actor` is a class that can change the state based on paticular [InternalAction].
 * One [InternalAction] = one [Actor].
 */
interface Actor<out I : InternalAction, S : State, A : InternalAction, E : Event> {

    fun isApplicable(state: S): Boolean

    fun @UnsafeVariance I.perform(
        state: S,
        internalActionConsumer: (A) -> Unit,
        eventConsumer: (E) -> Unit
    ): S

}