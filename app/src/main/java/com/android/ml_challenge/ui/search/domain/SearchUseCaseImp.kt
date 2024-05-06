package com.android.ml_challenge.ui.search.domain

import kotlinx.coroutines.flow.flow

class SearchUseCaseImp : SearchUseCase {

    override fun isValidText(text: String) = flow {
        if (text.trim().isNotEmpty()) {
            emit(true)
        } else {
            emit(false)
        }
    }
}