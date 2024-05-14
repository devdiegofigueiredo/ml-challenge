package com.android.ml_challenge.ui.details.entities

import android.content.Intent
import android.os.Build
import com.android.ml_challenge.model.Product

class ProductDetailUseCaseImp : ProductDetailUseCase {

    override fun onProductDataReceived(data: Intent): Product? {

        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            data.getSerializableExtra("product", Product::class.java)
        } else {
            data.getSerializableExtra("product") as Product
        }

        return product
    }
}