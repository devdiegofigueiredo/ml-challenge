package com.android.ml_challenge.ui.search.di

import com.android.ml_challenge.ui.search.SearchViewModel
import com.android.ml_challenge.ui.search.domain.SearchUseCase
import com.android.ml_challenge.ui.search.domain.SearchUseCaseImp
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val searchModule = module {
    viewModel { SearchViewModel(get()) }
    single<SearchUseCase> { SearchUseCaseImp() }
}