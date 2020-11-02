package com.example.daggerhilt.di

import com.example.daggerhilt.repository.MainRepository
import com.example.daggerhilt.retrofit.BlogRetrofit
import com.example.daggerhilt.retrofit.NetworkMapper
import com.example.daggerhilt.room.BlogDao
import com.example.daggerhilt.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ):MainRepository{
        return MainRepository(retrofit,blogDao,cacheMapper,networkMapper)
    }
}