package com.codigo.tmdb.di

import android.content.Context
import com.codigo.tmdb.database.TmdbDatabase
import com.codigo.tmdb.repository.TmdbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext app : Context) = TmdbDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideRepository(database: TmdbDatabase) = TmdbRepository(database)

}