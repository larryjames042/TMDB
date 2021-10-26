package com.codigo.tmdb.network

import com.codigo.tmdb.Utils.Utils
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.database.DatabaseMovie
import com.google.gson.annotations.SerializedName

data class NetworkMovieContainer(
    @SerializedName("results")
    val movies: List<NetworkMovie>
)

// movie data that can be displayed
data class NetworkMovie(
    @SerializedName("id")
    val id : Long,
    @SerializedName("poster_path")
    val posterUrl : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("overview")
    val movieOverview : String
)


/**
 * convert network results to database objects asDatabaseModel
 */
fun NetworkMovieContainer.asDomainModel() : List<Movie>{
    return movies.map {
        Movie(
            movieId = it.id,
            moviePosterUrl = it.posterUrl,
            movieTitle = it.title,
            movieOverview = it.movieOverview,
        )
    }
}

fun NetworkMovieContainer.asPopularDatabaseModel() : Array<DatabaseMovie>{
    return movies.map {
        DatabaseMovie(
            movieId = it.id,
            posterUrl = it.posterUrl,
            movieTitle = it.title,
            movieOverview = it.movieOverview,
            movieType = Utils.POPULAR_MOVIE
        )
    }.toTypedArray()
}

fun NetworkMovieContainer.asUpcomingDatabaseModel() : Array<DatabaseMovie>{
    return movies.map {
        DatabaseMovie(
            movieId = it.id,
            posterUrl = it.posterUrl,
            movieTitle = it.title,
            movieOverview = it.movieOverview,
            movieType = Utils.UPCOMING_MOVIE
        )
    }.toTypedArray()
}
