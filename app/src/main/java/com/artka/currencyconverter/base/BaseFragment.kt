package com.artka.currencyconverter.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.artka.currencyconverter.R
import com.artka.currencyconverter.utils.NetworkUtils
import com.artka.currencyconverter.utils.snackbar
import com.artka.currencyconverter.viewmodels.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import java.net.UnknownHostException

abstract class BaseFragment<VM : BaseViewModel>(private val clazz: Class<VM>) : Fragment() {

    protected lateinit var activity: AppCompatActivity

    protected val viewModel: VM by lazy {
        ViewModelProvider(activity, ViewModelFactory()).get(clazz)
    }

    protected var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as AppCompatActivity
        viewModel.getErrorData().observe(this, Observer {
            processError(it)
        })
        viewModel.getErrorMessageData().observe(this, Observer {
            processErrorMessage(it)
        })
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    private fun processError(t: Throwable) {
        if (t is UnknownHostException) {
            val isNetworkAvailable = NetworkUtils.isNetworkAvailable()
            if (!isNetworkAvailable) {
                view?.snackbar(R.string.error_network_error)
            } else {
                view?.snackbar(R.string.error_occured)
            }
        } else {
            view?.snackbar(R.string.error_occured)
        }
    }

    private fun processErrorMessage(message: Int) {
        view?.snackbar(message)
    }
}