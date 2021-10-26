package com.codigo.tmdb.data.domain

import java.io.Serializable


data class Movie(
    val movieId : Long,
    val moviePosterUrl : String,
    val movieTitle : String,
    val movieOverview : String,
) : Serializable