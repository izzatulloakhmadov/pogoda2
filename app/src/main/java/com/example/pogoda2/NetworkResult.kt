package com.example.pogoda2

sealed class NetworkResult<T>(
    val data: T?=null,
    val messaga:String?=null
){
    class Success<T>(data: T?):NetworkResult<T>(data)
    class Error<T>(messaga: String?,data: T?=null):NetworkResult<T>(data,messaga)
    class Loading<T>():NetworkResult<T>()


}

