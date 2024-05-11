package com.android.ml_challenge.network.di

import com.android.ml_challenge.network.api.ApiNetwork
import com.android.ml_challenge.network.api.ApiNetworkImp
import org.koin.dsl.module

val networkModule = module {
    single<ApiNetwork> { ApiNetworkImp(get()) }
}