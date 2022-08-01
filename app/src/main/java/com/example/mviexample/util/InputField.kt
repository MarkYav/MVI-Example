package com.example.mviexample.util

import androidx.annotation.StringRes

data class InputField(
    val value: String,
    @StringRes val error: Int? = null
)