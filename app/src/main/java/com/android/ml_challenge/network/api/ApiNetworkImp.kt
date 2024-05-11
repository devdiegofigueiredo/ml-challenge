package com.android.ml_challenge.network.api

import com.android.ml_challenge.model.SearchResponse
import com.android.ml_challenge.network.ApiService
import kotlinx.coroutines.flow.flow

class ApiNetworkImp(private val apiService: ApiService) : ApiNetwork {

    override fun products(name: String, offset: Int) = flow<SearchResponse> {
        val response = apiService.searchItems(name, offset.toString())
        emit(response)
    }
}