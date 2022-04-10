package com.pds.proyectone.util

sealed class DataState<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : DataState<T>(data, null)
    class Failure<T>(exception: String) : DataState<T>(null, exception)
}