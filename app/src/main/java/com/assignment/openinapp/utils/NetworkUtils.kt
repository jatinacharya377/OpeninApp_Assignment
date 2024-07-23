package com.assignment.openinapp.utils

import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object NetworkUtils {
    fun exceptionHandler(throwable: Throwable) : String {
        return when (throwable) {
            is HttpException -> {
                when (throwable.response()?.code()) {
                    400 -> "Bad data sent!"
                    401 -> "Unauthorized access!"
                    404 -> "Request not found!"
                    500 -> "Oops! Something went wrong!"
                    502 -> "Oops! Something went wrong!"
                    else -> "Something is broken!"
                }
            }
            is JsonParseException -> "Bad data received!"
            is ConnectException -> "Check your internet connection!"
            is SocketTimeoutException -> "Check your internet connection!"
            is TimeoutException -> "Request took too much time!"
            is UnknownHostException -> "Couldn't connect to server!"
            else -> "Oops! Something went wrong!"
        }
    }
}