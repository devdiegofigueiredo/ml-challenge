package com.android.ml_challenge.ui.products.di

import com.android.ml_challenge.ui.products.ProductsViewModel
import com.android.ml_challenge.ui.products.domain.ProductsUseCase
import com.android.ml_challenge.ui.products.domain.ProductsUseCaseImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productsModule = module {
    viewModel { ProductsViewModel(get()) }
    single<ProductsUseCase> { ProductsUseCaseImp(get()) }
}