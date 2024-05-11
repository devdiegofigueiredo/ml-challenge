package com.android.ml_challenge.ui.products.domain

import com.android.ml_challenge.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface ProductsUseCase {
    fun products(name: String): Flow<SearchResponse>
}