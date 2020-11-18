package com.reign.test

import android.app.Application
import com.reign.test.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                apiModule,
                viewModelModule,
                repositoryModule,
                networkModule,
                databaseModule
            )
        }
    }
}