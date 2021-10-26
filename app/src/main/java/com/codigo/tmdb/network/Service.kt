package com.codigo.tmdb.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService{
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey : String) : Response<NetworkMovieContainer>

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("api_key") apiKey : String) : Response<NetworkMovieContainer>

}

object Network{
    // http log
    val logger = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    /**
     * This should only be in DEBUG MODE
     */
    val okhttpClient = OkHttpClient.Builder().also {
        it.addInterceptor(logger)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https:api.themoviedb.org/3/")
        .client(okhttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val tmdbNetwork = retrofit.create(MoviesService::class.java)
}