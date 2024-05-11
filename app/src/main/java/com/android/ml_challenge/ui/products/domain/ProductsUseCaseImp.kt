package com.android.ml_challenge.ui.products.domain

import com.android.ml_challenge.model.SearchResponse
import com.android.ml_challenge.network.api.ApiNetwork
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ProductsUseCaseImp(private val apiNetwork: ApiNetwork) : ProductsUseCase {

    private var currentOffset = 0
    private var currentTotal = 0
    private var currentLimit = 0
    private var isLoading = false

    override fun products(name: String): Flow<SearchResponse> {
        if (!shouldBeRequest()) {
            return flowOf()
        }

        isLoading = true
        val response = apiNetwork.products(name, currentOffset).map {
            it.paging.run {
                updateOffset(it.paging)
                isLoading = false
            }
            return@map it
        }
        return response
    }

    private fun shouldBeRequest(): Boolean {
        return !(currentOffset != 0 && currentOffset == currentTotal) && !isLoading
    }

    private fun updateOffset(paging: SearchResponse.Paging) {
        paging.run {
            currentOffset = offset
            currentTotal = total
            currentLimit = limit

            val difference = total - currentOffset
            if (difference > limit) {
                currentOffset += limit
            } else {
                currentOffset = difference
            }
        }
    }
}