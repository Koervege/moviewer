package com.carce.moviewer.networkService

import com.carce.moviewer.BuildConfig
import com.carce.moviewer.model.Movie
import com.carce.moviewer.model.PopularMoviesJSONResult
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET(ApiURL.POPULAR_MOVIES_URL)
    suspend fun listMovies(): PopularMoviesJSONResult
}