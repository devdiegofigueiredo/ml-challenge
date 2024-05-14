package com.android.ml_challenge.ui.details

import com.android.ml_challenge.model.Product

interface UiProductDetailState<out T> {

    data class Success(val products: Product) : UiProductDetailState<Nothing>
    data object Loading : UiProductDetailState<Nothing>
    data object Error : UiProductDetailState<Nothing>
}