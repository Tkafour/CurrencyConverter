package com.artka.currencyconverter.utils

import android.view.View
import android.widget.TextView
import com.artka.currencyconverter.R
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(id: Int) {
    val message = this.context.getString(id)
    snackbar(message)
}

fun View.snackbar(message: String) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.view.apply {
        background = this.resources.getDrawable(R.drawable.snackbar_error_background, null)
        this.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            maxLines = 5
            setTextColor(resources.getColor(android.R.color.white))
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    snackbar.show()
}

