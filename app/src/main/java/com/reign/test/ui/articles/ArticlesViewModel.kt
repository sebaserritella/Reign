package com.reign.test.ui.articles

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.reign.test.data.models.Article
import com.reign.test.data.models.Hit
import com.reign.test.data.repositories.ArticlesRepository
import com.reign.test.network.AppResult
import com.reign.test.ui.ScopedViewModel
import com.reign.test.util.SingleLiveEvent
import kotlinx.coroutines.launch

class ArticlesViewModel(
    private val repository: ArticlesRepository
) : ScopedViewModel() {

    val showLoading = ObservableBoolean()
    val articlesLiveData = MutableLiveData<Article?>()
    val showError = SingleLiveEvent<String>()

    fun getAllArticles() {
        showLoading.set(true)
        viewModelScope.launch {
            val result = repository.getArticles()
            showLoading.set(false)
            when (result) {
                is AppResult.Success<*> -> {
                    articlesLiveData.postValue(result.successData as Article?)
                }

                is AppResult.Error -> showError.value = result.exception.message
            }
        }
    }

    fun deleteItem(hit: Hit){
    }
}
