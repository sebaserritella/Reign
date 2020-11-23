package com.reign.test.di

import com.reign.test.ui.articles.HitsViewModel
import com.reign.test.ui.hit.HitViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HitsViewModel(get()) }
    viewModel { HitViewModel() }
}                                                   