package com.android.ml_challenge.ui.search.domain

import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    fun isValidText(text: String): Flow<Boolean>
}