package com.android.ml_challenge.ui.search

sealed interface UiSearchState<out T> {

    data object Success : UiSearchState<Nothing>

    data class Error(val message: String) : UiSearchState<Nothing>

    data object Empty : UiSearchState<Nothing>

    data object Loading : UiSearchState<Nothing>
}