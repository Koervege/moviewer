package com.carce.moviewer.repository

import com.carce.moviewer.model.Movie
import com.carce.moviewer.networkService.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepository {
    suspend fun getPopularMovieList(): Flow<List<Movie>> = flow {
        emit(RetrofitClient.retrofit.listMovies().results)
    }.flowOn(Dispatchers.IO)
}