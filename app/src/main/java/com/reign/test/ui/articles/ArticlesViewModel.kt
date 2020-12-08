package com.reign.test.ui.articles

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.reign.test.base.LiveCoroutinesViewModel
import com.reign.test.data.models.Article
import com.reign.test.data.models.Hit
import com.reign.test.data.repositories.ArticlesRepository
import com.reign.test.util.SingleLiveEvent
import timber.log.Timber

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : LiveCoroutinesViewModel() {

    val showLoading = ObservableBoolean()
   //val showError = SingleLiveEvent<String>()

    private var hitFetchingLiveData: MutableLiveData<Boolean> = MutableLiveData(true)
    val hitListLiveData: LiveData<List<Hit>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    init {
        Timber.d("injection ArticlesViewModel")

        hitListLiveData = hitFetchingLiveData.switchMap {
            launchOnViewModelScope {
                repository.loadArticles(disposables) {
                    _toastLiveData.postValue(it)
                }
            }
        }
    }
}