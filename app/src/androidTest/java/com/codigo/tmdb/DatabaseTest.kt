package com.codigo.tmdb

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.codigo.tmdb.data.domain.Movie
import com.codigo.tmdb.database.DatabaseMovie
import com.codigo.tmdb.database.TmdbDatabase
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest  {
    private lateinit var db  : TmdbDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TmdbDatabase::class.java).build()
    }

    @Test
    fun insertMovieText(){
        val movie = DatabaseMovie(1, "sample.png", "Scorpion", "Excellent")
        db.movieDao.insertAllMovies(movie)
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

}