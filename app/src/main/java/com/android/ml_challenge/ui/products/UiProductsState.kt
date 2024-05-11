package com.android.ml_challenge.ui.products

import com.android.ml_challenge.model.Product

interface UiProductsState<out T> {

    data class Success(val products: List<Product>) : UiProductsState<Nothing>

    data object Error : UiProductsState<Nothing>

    data object Empty : UiProductsState<Nothing>

    data object Loading : UiProductsState<Nothing>

    data object DoNothing : UiProductsState<Nothing>
}