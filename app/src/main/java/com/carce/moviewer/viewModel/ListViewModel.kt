package com.carce.moviewer.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.carce.moviewer.networkService.RequestState
import com.carce.moviewer.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListViewModel(): ViewModel() {

    val wMessage: MutableStateFlow<RequestState> = MutableStateFlow(RequestState.Empty)
    private val movieRepository = MovieRepository()

    @SuppressLint("LongLogTag")
    fun getMovieList() {

        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getPopularMovieList()
                .catch { e ->
                    wMessage.value = RequestState.Failure(e)
                    Log.e("THIS IS MY ERRORRRRRRRRRRRRR", e.message!!)
                }.collect { data ->
                    wMessage.value = RequestState.Success(data)
                }
        }
    }
}