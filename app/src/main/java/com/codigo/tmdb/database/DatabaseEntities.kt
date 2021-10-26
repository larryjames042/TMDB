package com.codigo.tmdb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codigo.tmdb.data.domain.Movie

@Entity
data class DatabaseMovie(
    @PrimaryKey
    val movieId : Long,
    val posterUrl : String,
    val movieTitle : String,
    val movieOverview : String,
    val movieType : String  // popular or upcoming
)

@Entity
data class FavouriteMovie(
    @PrimaryKey
    val movieId : Long,
    val posterUrl : String,
    val movieTitle : String,
    val movieOverview : String
)


fun List<DatabaseMovie>.asDomainModel() : List<Movie>{
    return map{
        Movie(
            movieId = it.movieId,
            moviePosterUrl = it.posterUrl,
            movieTitle = it.movieTitle,
            movieOverview = it.movieOverview
        )
    }
}

fun FavouriteMovie.toDatabaseMovie(type : String) = DatabaseMovie(
    movieId = movieId,
    posterUrl = posterUrl,
    movieTitle = movieTitle,
    movieOverview = movieOverview,
    movieType = type
)

fun Movie.toFavouriteMovie() = FavouriteMovie(
    movieId = movieId,
    posterUrl = moviePosterUrl,
    movieTitle = movieTitle,
    movieOverview = movieOverview
)
