package com.reign.test.ui.articles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.reign.test.base.LiveCoroutinesViewModel
import com.reign.test.data.models.Hit
import com.reign.test.data.repositories.ArticlesRepository
import timber.log.Timber

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : LiveCoroutinesViewModel() {

    var fetching: MutableLiveData<Boolean> = MutableLiveData(false)

    private var hitFetchingLiveData: MutableLiveData<Boolean> = MutableLiveData(true)
    var hitListLiveData: LiveData<List<Hit>>

    private val _toastLiveData: MutableLiveData<String> = MutableLiveData()
    val toastLiveData: LiveData<String> get() = _toastLiveData

    init {
        Timber.d("injection ArticlesViewModel")

        fetching.postValue(true)
        hitListLiveData = hitFetchingLiveData.switchMap {
            launchOnViewModelScope {
                repository.loadArticles(false, disposables) {
                    _toastLiveData.postValue(it)
                }
            }
        }

        fetching.postValue(false)
    }

    fun forceUpdate() {
        fetching.postValue(true)
        hitListLiveData = hitFetchingLiveData.switchMap {
            launchOnViewModelScope {
                repository.loadArticles(true, disposables) {
                    _toastLiveData.postValue(it)
                }
            }
        }

        fetching.postValue(false)
    }
}