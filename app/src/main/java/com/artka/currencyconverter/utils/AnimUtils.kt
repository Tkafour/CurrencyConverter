package com.artka.currencyconverter.utils

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation

object AnimUtils {

    @JvmStatic
    fun rotate(view : View) {
        val rotate = RotateAnimation(
            0F, 360f, Animation.RELATIVE_TO_SELF,
            0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 500
        rotate.repeatCount = 1
        rotate.interpolator = AccelerateDecelerateInterpolator()
        view.startAnimation(rotate)
    }
}