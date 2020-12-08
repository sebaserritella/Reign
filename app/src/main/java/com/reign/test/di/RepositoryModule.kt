package com.reign.test.di

import com.reign.test.data.repositories.ArticlesRepository
import org.koin.dsl.module


val repositoryModule = module {

    single { ArticlesRepository(get(), get(), get()) }

}