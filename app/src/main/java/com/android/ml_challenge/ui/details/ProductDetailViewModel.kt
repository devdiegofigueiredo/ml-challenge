package com.android.ml_challenge.ui.details

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ml_challenge.model.Product
import com.android.ml_challenge.ui.details.entities.ProductDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailViewModel(private val productDetailUseCase: ProductDetailUseCase) : ViewModel() {

    private val _uiProductDetailState =
        MutableStateFlow<UiProductDetailState<String>>(UiProductDetailState.Loading)
    val uiProductDetailState: StateFlow<UiProductDetailState<String>> = _uiProductDetailState

    fun onProductDataReceived(data: Intent) {
        viewModelScope.launch {
            val product = fetchProduct(data)
            product?.apply {
                _uiProductDetailState.value = UiProductDetailState.Success(this)
            } ?: run {
                _uiProductDetailState.value = UiProductDetailState.Error
            }
        }
    }

    private suspend fun fetchProduct(data: Intent): Product? = withContext(Dispatchers.IO) {
        return@withContext productDetailUseCase.onProductDataReceived(data)
    }
}