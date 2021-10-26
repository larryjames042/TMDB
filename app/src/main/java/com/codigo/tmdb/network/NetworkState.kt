package com.codigo.tmdb.network

sealed class NetworkState{
    data class Success(val success: String) : NetworkState()
    data class NetworkException(val error : String) : NetworkState()
    data class Error(val error: String) : NetworkState()
}