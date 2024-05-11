package com.android.ml_challenge.network.api

import com.android.ml_challenge.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ApiNetwork {
    fun products(name: String, offset: Int): Flow<SearchResponse>
}