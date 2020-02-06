package com.artka.currencyconverter.base

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private var loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    protected var errorLiveData : MutableLiveData<Throwable> = MutableLiveData()

    private var errorMessageLiveData : MutableLiveData<String> = MutableLiveData()

    protected val compositeDisposable = CompositeDisposable()

    protected fun onLoadStart() {
        loadingVisibility.postValue(View.VISIBLE)
    }

    protected fun onLoadFinish() {
        loadingVisibility.postValue(View.GONE)
    }

    protected fun processError(t : Throwable) {
        errorLiveData.postValue(t)
    }

    protected fun processErrorMessage(message : String) {
        errorMessageLiveData.postValue(message)
    }

    fun getLoadingState() : LiveData<Int> {
        return loadingVisibility
    }

    fun getErrorData() : LiveData<Throwable> {
        return errorLiveData
    }

    fun getErrorMessageData() : LiveData<String> {
        return errorMessageLiveData
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}