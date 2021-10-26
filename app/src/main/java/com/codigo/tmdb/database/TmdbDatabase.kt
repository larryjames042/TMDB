package com.codigo.tmdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


/**
 * Stores a collection of database movies in a room database
 */
@Database(entities = [DatabaseMovie::class, FavouriteMovie::class], version = 1, exportSchema = false)
abstract class TmdbDatabase  : RoomDatabase(){

    abstract val movieDao : MovieDao

    companion object{
        @Volatile
        private var INSTANCE: TmdbDatabase? = null

        fun getInstance(context: Context): TmdbDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TmdbDatabase::class.java,
                        "tmdb_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}