package com.artka.currencyconverter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.artka.currencyconverter.R
import com.artka.currencyconverter.adapter.CurrencyAdapter
import com.artka.currencyconverter.base.BaseFragment
import com.artka.currencyconverter.databinding.CurrencyDialogLayoutBinding
import com.artka.currencyconverter.databinding.MainFragmentBinding
import com.artka.currencyconverter.utils.AnimUtils
import com.artka.currencyconverter.utils.NetworkUtils
import com.artka.currencyconverter.utils.snackbar
import com.artka.currencyconverter.viewmodels.MainViewModel
import io.reactivex.disposables.Disposable

const val ACRONYM_SIZE = 3

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class.java) {

    private lateinit var binding: MainFragmentBinding

    private var subscription: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater)

        viewModel.getRates().observe(viewLifecycleOwner, Observer {
            setAmountListener()
            setCurrencyListeners()
        })

        binding.sync.setOnClickListener {
            if (!NetworkUtils.isNetworkAvailable()) {
                view?.snackbar(R.string.error_network_error)
            } else {
                AnimUtils.rotate(it)
            }
            viewModel.getData()
        }

        viewModel.getLoadingState().observe(viewLifecycleOwner, Observer {
            setLoading(it)
        })

        return binding.root
    }

    private fun setCurrencyListeners() {
        binding.apply {

            val watcher = {
                if (fromCurrency.text?.length == ACRONYM_SIZE && toCurrency.text?.length == ACRONYM_SIZE) {
                    val amount = fromAmount.text.toString().toDoubleOrNull()
                    checkAmount(amount)
                }
            }

            fromCurrency.addTextChangedListener {
                watcher()
            }
            fromCurrency.setOnClickListener {
                showDialog(it as EditText)
            }

            toCurrency.addTextChangedListener {
                watcher()
            }
            toCurrency.setOnClickListener {
                showDialog(it as EditText)
            }
        }
    }

    private fun setAmountListener() {
        binding.fromAmount.addTextChangedListener {
            val amount = it.toString().toDoubleOrNull()
            checkAmount(amount)
        }

    }

    private fun checkAmount(amount: Double?) {
        if (amount != null) {
            calculateAmount(amount)
        } else {
            binding.toAmount.text = ""
        }
    }

    private fun calculateAmount(
        amount: Double
    ) {
        val firstCurrency = binding.fromCurrency.text.toString()
        val secondCurrency = binding.toCurrency.text.toString()
        subscription?.dispose()
        subscription = viewModel.calculateAmount(firstCurrency, secondCurrency, amount).subscribe({
            binding.toAmount.text = it
        }, { it.printStackTrace() })
        subscription?.let { compositeDisposable.add(it) }
    }

    private fun showDialog(editTex: EditText) {
        val builder = AlertDialog.Builder(activity)
        val binding = CurrencyDialogLayoutBinding.inflate(LayoutInflater.from(activity))
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.apply {
            val adapter = CurrencyAdapter {
                editTex.setText(it.name)
                dialog.dismiss()
            }
            recycler.adapter = adapter
            adapter.setData(viewModel.getRates().value?.sortedBy { it.name })

        }
        dialog.show()
    }

    private fun setLoading(loadingVisibility: Int) {
        val visibility = if (loadingVisibility == View.VISIBLE) loadingVisibility else View.GONE
        binding.progress.visibility = visibility
    }

}