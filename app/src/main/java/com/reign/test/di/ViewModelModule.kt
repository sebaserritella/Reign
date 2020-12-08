package com.reign.test.di

import com.reign.test.ui.articles.ArticlesViewModel
import com.reign.test.ui.hit.HitViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { ArticlesViewModel(get()) }
    viewModel { HitViewModel() }
}