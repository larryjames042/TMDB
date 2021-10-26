package com.codigo.tmdb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.database.FavouriteMovie
import com.codigo.tmdb.database.TmdbDatabase
import com.codigo.tmdb.database.asDomainModel
import com.codigo.tmdb.network.Network.tmdbNetwork
import com.codigo.tmdb.network.NetworkState
import com.codigo.tmdb.network.asPopularDatabaseModel
import com.codigo.tmdb.network.asUpcomingDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Repository for fetching TMDB movies from the network and storing them on the disk
 */
class TmdbRepository @Inject constructor(
    private val database: TmdbDatabase) {

    /**
     * in production. api_key will be placed somewhere else. ie.. gradle or other safe place.
     */
    val apiKey = "5fa2eaf4020be641353707ce7a4a8cce"

    val popularMovies : LiveData<List<Movie>> = Transformations.map(database.movieDao.getPopularMovies(Utils.POPULAR_MOVIE)){
        it.asDomainModel()
    }

    val upcomingMovies : LiveData<List<Movie>> = Transformations.map(database.movieDao.getUpcomingMovies(Utils.UPCOMING_MOVIE)){
        it.asDomainModel()
    }

    suspend fun refreshPopularMovies() : NetworkState{
        return try{
            withContext(Dispatchers.IO){
                val response = tmdbNetwork.getPopularMovies(apiKey)

                if(response.isSuccessful && response.body()!=null){
                    val movieList = response.body()!!.asPopularDatabaseModel()
                    database.movieDao.insertAllMovies(*movieList!!)
                    NetworkState.Success("Success")
                }else{
                      when(response.code()){
                        // many code can be checked here depending on the requriement
                        in 403..599 -> NetworkState.NetworkException(response.message())
                        else -> NetworkState.Error(response.message())
                    }
                }
            }
        }catch (error : Exception){
            Timber.tag("networkerror").d(error.message)
            NetworkState.Error("error")
        }
    }

    suspend fun refreshUpcomingMovies() : NetworkState{
        return try{
            withContext(Dispatchers.IO){
                val response = tmdbNetwork.getUpComingMovies(apiKey)
                if(response.isSuccessful && response.body()!=null){
                    val movieList = response.body()!!.asUpcomingDatabaseModel()
                    database.movieDao.insertAllMovies(*movieList!!)
                    NetworkState.Success("Success")
                }else{
                    when(response.code()){
                        // many code can be checked here depending on the requriement
                        in 403..599 -> NetworkState.NetworkException(response.message())
                        else -> NetworkState.Error(response.message())
                    }
                }
            }
        }catch (error : Exception){
            Timber.tag("networkerror").d(error.message)
            NetworkState.Error("error")
        }
    }

    /**
     * helper method for favourite movie operation
     *
     */

    suspend fun addToFavourite(movie : FavouriteMovie): Long{
        return withContext(Dispatchers.IO){
            database.movieDao.insertFavouriteMovie(movie)
        }
    }

    suspend fun removeFromFavourite(movie : FavouriteMovie) : Int{
        return withContext(Dispatchers.IO){
            database.movieDao.removeFavouriteMovie(movie)
        }
    }

    suspend fun findMovieInFavourite(movieId : Long) : Boolean{
        return withContext(Dispatchers.IO){
             database.movieDao.findInFavouriteMovie(movieId) != null
        }
    }



}