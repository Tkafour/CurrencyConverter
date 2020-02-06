package com.artka.currencyconverter.base

import android.app.Activity
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.artka.currencyconverter.R
import com.artka.currencyconverter.utils.NetworkUtils
import com.artka.currencyconverter.utils.snackbar
import com.artka.currencyconverter.viewmodels.ViewModelFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.UnknownHostException

abstract class BaseFragment<VM : BaseViewModel>(private val clazz: Class<VM>) : Fragment() {

    protected lateinit var activity: AppCompatActivity

    protected val viewModel: VM by lazy {
        ViewModelProviders.of(activity, ViewModelFactory()).get(clazz)
    }

    private var networkSubscription: Disposable? = null

    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = getActivity() as AppCompatActivity
        viewModel.getErrorData().observe(this, Observer {
            processError(it)
        })
        viewModel.getErrorMessageData().observe(this, Observer {
            processErrorMessage(it)
        })

        networkSubscription?.dispose()

        networkSubscription =
            NetworkUtils.networkStateSubject.observeOn(AndroidSchedulers.mainThread()).subscribe {
                if (it == NetworkInfo.State.DISCONNECTED) {
                    setPlaceholderVisibility(View.VISIBLE)
                } else {
                    setPlaceholderVisibility(View.GONE)
                }
            }
        compositeDisposable.addAll(networkSubscription)
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
            if (isNetworkAvailable) {
                view?.snackbar(R.string.error_network_error)
            } else {
                view?.snackbar(R.string.error_occured)
            }
        } else {
            view?.snackbar(R.string.error_occured)
        }
    }

    private fun processErrorMessage(message: String) {
        when {
            message.isEmpty() -> {
            }
            else -> {
                view?.snackbar(message)
            }
        }
    }

    open fun setPlaceholderVisibility(visibility: Int) {}
}