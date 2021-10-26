package com.codigo.tmdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.database.toFavouriteMovie
import com.codigo.tmdb.network.NetworkState
import com.codigo.tmdb.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesFragmentViewModel @Inject constructor(val app : Application, val movieRepository: TmdbRepository) : AndroidViewModel(app) {

    private var viewModelJob = SupervisorJob()

    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main )

    val networkResponseState = MutableLiveData<NetworkState>()

    val favInsertResult = MutableLiveData<Long>()

    val favRemoveResult = MutableLiveData<Int>()

    init {
        viewModelScope.launch {
            networkResponseState.value = movieRepository.refreshPopularMovies()
        }
    }

    val movies = movieRepository.popularMovies

    /**
     * Cancel job
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // method to insert favourite movie
     fun  insertFavouriteData(movie: Movie){
        // transform the domain object to favourite object
        viewModelScope.launch {
            favInsertResult.value =  movieRepository.addToFavourite(movie.toFavouriteMovie())
        }

    }

    fun removeFavouriteData(movie: Movie){
        viewModelScope.launch {
            favRemoveResult.value = movieRepository.removeFromFavourite(movie.toFavouriteMovie())
        }
    }

}

