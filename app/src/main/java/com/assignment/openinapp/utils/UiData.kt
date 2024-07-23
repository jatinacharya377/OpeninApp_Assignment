package com.assignment.openinapp.utils

sealed class UiData<T> {
    class Error<T>(val error: String): UiData<T>()
    class Loading<T>: UiData<T>()
    class Success<T>(val data: T): UiData<T>()

    fun getBindingData(): T? {
        return if (this is Success) data
        else null
    }
}