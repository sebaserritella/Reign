package com.reign.test.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.jetbrains.annotations.TestOnly
import kotlin.coroutines.CoroutineContext

abstract class ScopedViewModel : ViewModel() {

    /*
    protected val _loading = MutableLiveData<Loading>()
    val loading: LiveData<Loading> get() = _loading


     */
    // SupervisorJob allows children jobs to fail independently
    private val job = SupervisorJob()

    private var _coroutineScope = CoroutineScope(Dispatchers.Main + job)
    val coroutineScope
        get() = _coroutineScope

    private var _coroutineContext: CoroutineContext = Dispatchers.IO
    val coroutineContext get() = _coroutineContext

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    @TestOnly
    fun setCoroutineScope(coroutineScope: CoroutineScope) {
        this._coroutineScope = coroutineScope
    }

    @TestOnly
    fun setCoroutineContext(coroutineContext: CoroutineContext) {
        this._coroutineContext = coroutineContext
    }

    @TestOnly
    fun clear() {
        super.onCleared()
    }
}