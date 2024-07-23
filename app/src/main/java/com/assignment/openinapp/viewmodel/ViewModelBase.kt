package com.assignment.openinapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.openinapp.data.repository.RepositoryBase
import com.assignment.openinapp.utils.NetworkUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class ViewModelBase(application: Application) : AndroidViewModel(application) {

    private val _errorCallback = MutableLiveData<String>()
    val errorCallback: LiveData<String>
        get() = _errorCallback
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorCallback.postValue(NetworkUtils.exceptionHandler(throwable))
    }

    fun clearErrorCallback() = _errorCallback.postValue("")

    fun <T: RepositoryBase> getRepository(entityClass: Class<T>) : T {
        val instance = RepositoryBase.getInstance(entityClass)
        return instance
    }

    fun launchCoroutineScope(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler, CoroutineStart.DEFAULT, block)
    }

    abstract fun onCreate()
}