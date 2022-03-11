package com.app.themoviedb.di

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import com.app.themoviedb.repository.data.local.dao.FavouriteDao
import com.app.themoviedb.repository.data.local.db.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app:Application)=Room.databaseBuilder(app,MoviesDataBase::class.java,"MovieDb")
        .fallbackToDestructiveMigration().build()
    @Provides
    fun provideFavouriteDao(database: MoviesDataBase)=database.getFavouriteDao()
}