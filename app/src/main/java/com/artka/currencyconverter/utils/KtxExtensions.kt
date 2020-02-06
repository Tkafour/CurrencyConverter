package com.artka.currencyconverter.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.artka.currencyconverter.R
import com.google.android.material.snackbar.Snackbar

fun View.snackbar(id: Int) {
    val snackbar = Snackbar.make(this, this.context.getString(id), Snackbar.LENGTH_LONG)
    snackbar.view.apply {
        background = this.resources.getDrawable(R.drawable.snackbar_error_background)
        this.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            setTextColor(resources.getColor(android.R.color.white))
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    snackbar.show()
}

fun View.snackbar(message: String, isBottomNavigationActive : Boolean = true) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snackbar.view.apply {
        background = this.resources.getDrawable(R.drawable.snackbar_error_background)
        this.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            maxLines = 5
            setTextColor(resources.getColor(android.R.color.white))
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    if (isBottomNavigationActive) {
        val snackbarView = snackbar.view
        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams

        params.setMargins(
            params.leftMargin,
            params.topMargin,
            params.rightMargin,
            params.bottomMargin + 200
        )

        snackbarView.layoutParams = params
    }

    snackbar.show()
}

