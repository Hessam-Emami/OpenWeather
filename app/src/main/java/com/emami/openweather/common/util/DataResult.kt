package com.emami.openweather.common.util

/**
 * Creating sort of data state management using kotlin sealed classes
 */
sealed class DataResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Error(val errorMsg: String, val e: Exception? = null) : DataResult<Nothing>()
}


