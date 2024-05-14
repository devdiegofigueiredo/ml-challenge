package com.android.ml_challenge.ui.details.di

import com.android.ml_challenge.ui.details.ProductDetailViewModel
import com.android.ml_challenge.ui.details.entities.ProductDetailUseCase
import com.android.ml_challenge.ui.details.entities.ProductDetailUseCaseImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productDetailModule = module {
    viewModel { ProductDetailViewModel(get()) }

    single<ProductDetailUseCase> { ProductDetailUseCaseImp() }

}