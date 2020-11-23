package com.reign.test.ui.articles

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.reign.test.data.models.Hit
import com.reign.test.data.repositories.ArticleRepository
import com.reign.test.network.AppResult
import com.reign.test.ui.ScopedViewModel
import com.reign.test.util.SingleLiveEvent
import kotlinx.coroutines.launch


class HitsViewModel(
    private val articleRepository: ArticleRepository
) : ScopedViewModel() {

    val showLoading = ObservableBoolean()
    private var _articlesLiveData = MutableLiveData<MutableList<Hit>?>()
    val articlesLiveData: LiveData<MutableList<Hit>?> = _articlesLiveData

    val showError = SingleLiveEvent<String>()

    fun getArticles() {
        showLoading.set(true)
        if (_articlesLiveData.value == null) {
            _articlesLiveData.value = mutableListOf()

        }
        loadArticles()
    }


    private fun loadArticles() {
        showLoading.set(true)
        viewModelScope.launch {
            when (val result = articleRepository.getHits()) {
                is AppResult.Success -> if (result.successData != null) {
                    _articlesLiveData.value = result.successData
                    showLoading.set(false)
                }

                is AppResult.Error -> showError.postValue(result.message)
            }

        }
    }

    fun deleteItem(position: String, hit: Hit, hitList: MutableList<Hit>) {
        viewModelScope.launch {
            val result = articleRepository.updateHit(position, hit, hitList)
            loadArticles()
        }
    }
}

/*
fun getAllArticles() {
   showLoading.set(true)
   viewModelScope.launch {
       val result = repository.getHits()
       showLoading.set(false)
       when (result) {
           is AppResult.Success<*> -> {
               articlesLiveData.postValue(result.successData as List<Hit>?)
           }

           is AppResult.Error -> showError.value = result.exception.message
       }
   }
}

fun deleteItem(hit: Hit) {
   viewModelScope.launch {
       val result = repository.deleteItem(hit)
       if (result) {
           getAllArticles()
       } else {
            showError.postValue("Cannot delete item")
       }
   }
}


 */
