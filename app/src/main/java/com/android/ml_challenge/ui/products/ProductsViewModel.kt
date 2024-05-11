package com.android.ml_challenge.ui.products

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.ml_challenge.ui.products.domain.ProductsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProductsViewModel(private val productsUseCase: ProductsUseCase) : ViewModel() {

    private val _uiProductsState =
        MutableStateFlow<UiProductsState<String>>(UiProductsState.Loading)
    val uiProductsState: StateFlow<UiProductsState<String>> = _uiProductsState
    private lateinit var productName: String

    fun fetchProducts() {
        _uiProductsState.value = UiProductsState.Loading
        fetchMoreProducts()
    }

    fun fetchMoreProducts() {
        viewModelScope.launch {
            productsUseCase.products(productName)
                .flowOn(Dispatchers.IO)
                .catch { exception ->
                    Log.d("Products request: ", exception.message.toString())
                    _uiProductsState.value = UiProductsState.Error
                }
                .collect {
                    if (it.results.isNotEmpty()) {
                        _uiProductsState.value = UiProductsState.Success(it.results)
                    } else {
                        _uiProductsState.value = UiProductsState.Empty
                    }
                }
        }
    }

    fun setProductName(productName: String) {
        this.productName = productName
    }
}