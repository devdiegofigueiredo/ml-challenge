package com.android.ml_challenge

import android.app.Application
import com.android.ml_challenge.network.RetrofitBuilder.retrofitModule
import com.android.ml_challenge.network.di.networkModule
import com.android.ml_challenge.ui.products.di.productsModule
import com.android.ml_challenge.ui.search.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(searchModule, networkModule, retrofitModule)
        }
    }
}