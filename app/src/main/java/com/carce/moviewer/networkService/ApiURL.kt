package com.carce.moviewer.networkService

import com.carce.moviewer.BuildConfig

object ApiURL {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val POPULAR_MOVIES_URL = "movie/popular?api_key=${BuildConfig.API_KEY}&language=en-US"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w780"
}
