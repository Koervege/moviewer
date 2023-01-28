package com.carce.moviewer.networkService

sealed class RequestState {
    object Loading : RequestState()
    class Failure(val e: Throwable) : RequestState()
    class Success(val data: Any) : RequestState()
    object Empty : RequestState()
}
