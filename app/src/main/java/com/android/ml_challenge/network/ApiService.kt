package com.android.ml_challenge.network

import com.android.ml_challenge.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("MLB/search")
    suspend fun searchItems(
        @Query("q") value: String,
        @Query("offset") offset: String
    ): List<SearchResponse>
}