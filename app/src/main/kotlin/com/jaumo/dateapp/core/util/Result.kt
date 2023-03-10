package com.jaumo.dateapp.core.util

/***
 * Response class, which has the states of the response of fetching/getting classes.
 * Loading: when the response is currently loading. Typically show the loading indicator in this case.
 * Success: when there is no error during getting data. Typically show the values.
 * Error when the response goes on error state. Typically show the error.
 */
sealed class Response<T>(val data: T? = null, val errorType: ErrorType? = null) {
    class Loading<T> : Response<T>()
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(errorType: ErrorType, data: T? = null) : Response<T>(data, errorType)
}