package com.example.daggerhilt.retrofit

import com.example.daggerhilt.models.BlogNetworkEntity
import retrofit2.http.GET

interface BlogRetrofit {

    @GET("blogs")
    suspend fun getBlogs():List<BlogNetworkEntity>
}