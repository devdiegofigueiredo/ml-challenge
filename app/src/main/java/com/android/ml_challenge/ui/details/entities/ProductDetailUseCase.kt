package com.android.ml_challenge.ui.details.entities

import android.content.Intent
import com.android.ml_challenge.model.Product

interface ProductDetailUseCase {
    fun onProductDataReceived(data: Intent): Product?
}