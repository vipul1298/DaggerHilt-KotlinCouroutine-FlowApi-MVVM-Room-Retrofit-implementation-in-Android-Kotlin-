package com.example.daggerhilt.repository

import com.example.daggerhilt.models.Blog
import com.example.daggerhilt.retrofit.BlogRetrofit
import com.example.daggerhilt.retrofit.NetworkMapper
import com.example.daggerhilt.room.BlogDao
import com.example.daggerhilt.room.CacheMapper
import com.example.daggerhilt.utils.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val retrofit:BlogRetrofit,
private val blogDao: BlogDao,
private val cacheMapper: CacheMapper,
private val networkMapper: NetworkMapper)
{
    suspend fun getBlog():Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = retrofit.getBlogs()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for(blog in blogs){
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlogs= blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlogs)))
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}