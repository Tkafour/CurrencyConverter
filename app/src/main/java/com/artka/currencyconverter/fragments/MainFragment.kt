package com.artka.currencyconverter.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.artka.currencyconverter.adapter.CurrencyAdapter
import com.artka.currencyconverter.R
import com.artka.currencyconverter.base.BaseFragment
import com.artka.currencyconverter.databinding.CurrencyDialogLayoutBinding
import com.artka.currencyconverter.databinding.MainFragmentBinding
import com.artka.currencyconverter.utils.snackbar
import com.artka.currencyconverter.viewmodels.MainViewModel
import java.lang.Exception
import java.text.DecimalFormat

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class.java) {

    private lateinit var binding: MainFragmentBinding

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

        viewModel.getLoadingState().observe(viewLifecycleOwner, Observer {
            setLoading(it)
        })

        return binding.root
    }

    private fun setCurrencyListeners() {
        binding.apply {
            val watcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (fromCurrency.text?.length == 3 && toCurrency.text?.length == 3) {
                        val amount = fromAmount.text.toString().toDoubleOrNull()
                        toAmount.text = calculateAmount(amount)
                    }
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            }
            fromCurrency.addTextChangedListener(watcher)
            fromCurrency.setOnClickListener {
                showDialog(it as EditText)
            }

            toCurrency.addTextChangedListener(watcher)
            toCurrency.setOnClickListener {
                showDialog(it as EditText)
            }
        }
    }

    private fun setAmountListener() {
        binding.apply {
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        val amount = s.toString().toDoubleOrNull()
                        toAmount.text = calculateAmount(amount)
                    } catch (e: Exception) {
                        view?.snackbar(R.string.wrong_format)
                    }
                }
            }
            fromAmount.addTextChangedListener(textWatcher)
        }
    }

    private fun calculateAmount(
        amount: Double?
    ): String {
        return if (amount != null) {
            val firstCurrency = binding.fromCurrency.text.toString()
            val secondCurrency = binding.toCurrency.text.toString()
            val rates = viewModel.getRates().value
            val firstRate = rates?.find { it.name == firstCurrency }?.rate ?: 1.0
            val secondRate = rates?.find { it.name == secondCurrency }?.rate ?: 1.0
            val df = DecimalFormat("###.#")
            df.format(amount * (secondRate / firstRate))
        } else {
            ""
        }
    }

    private fun showDialog(editTex: EditText) {
        val builder = AlertDialog.Builder(activity)
        val binding = CurrencyDialogLayoutBinding.inflate(LayoutInflater.from(activity))
        builder.setView(binding.root)
        val dialog = builder.create()
        binding.apply {
            val adapter = CurrencyAdapter() {
                editTex.setText(it.name)
                dialog.dismiss()
            }
            recycler.adapter = adapter
            adapter.setData(viewModel.getRates().value)

        }
        dialog.show()
    }

    private fun setLoading(loadingVisibility: Int) {
        val visibility = if (loadingVisibility == View.VISIBLE) loadingVisibility else View.GONE
        binding.progress.visibility = visibility
    }

}