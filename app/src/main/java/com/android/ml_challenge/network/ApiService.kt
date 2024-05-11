package com.android.ml_challenge.network

import com.android.ml_challenge.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("MLB/search")
    suspend fun searchItems(
        @Query("q") name: String,
        @Query("offset") offset: String
    ): SearchResponse
}