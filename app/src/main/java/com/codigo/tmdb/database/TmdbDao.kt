package com.codigo.tmdb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.codigo.tmdb.Utils.Utils

@Dao
interface MovieDao{
    @Query("select * from DatabaseMovie")
    fun getMovies() : LiveData<List<DatabaseMovie>>

    // popular movie
    @Query("select * from DatabaseMovie where movieType = :movieType")
    fun getPopularMovies(movieType : String) : LiveData<List<DatabaseMovie>>

    // upcoming movie
    @Query("select * from DatabaseMovie where movieType = :movieType")
    fun getUpcomingMovies(movieType: String) : LiveData<List<DatabaseMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(vararg movies: DatabaseMovie)


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavouriteMovie(movie : FavouriteMovie) : Long

    @Query("select * from FavouriteMovie where movieId=:movieId")
    fun findInFavouriteMovie(movieId : Long) : FavouriteMovie

    @Delete
    fun removeFavouriteMovie(movie : FavouriteMovie) :Int

    @Query("select * from FavouriteMovie")
    fun getAllFavouriteMovies() : LiveData<List<FavouriteMovie>>

}