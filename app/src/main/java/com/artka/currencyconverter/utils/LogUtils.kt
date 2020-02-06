package com.artka.currencyconverter.utils

import android.util.Log
import com.artka.currencyconverter.BuildConfig

object LogUtils {

    fun Any.debugLog(message: String) {
        LogUtils.d(this.javaClass, message)
    }

    fun Any.infoLog(message: String) {
        LogUtils.i(this.javaClass, message)
    }

    fun Any.errorLog(message: String) {
        LogUtils.e(this.javaClass, message)
    }

    const val ERROR = 0
    const val INFO = 1
    const val DEBUG = 2

    object LogUtils {

        private val info = { tag: String, message: String -> Log.i(tag, message) }
        private val debug = { tag: String, message: String -> Log.d(tag, message) }
        private val error = { tag: String, message: String -> Log.e(tag, message) }

        fun log(tag: String, message: String, type: Int) {
            when (type) {
                ERROR -> error.invoke(tag, message)
                INFO -> info.invoke(tag, message)
                DEBUG -> debug.invoke(tag, message)
                else -> debug.invoke(tag, message)
            }
        }

        private fun log(
            clazz: Class<Any>,
            message: String,
            type: Int
        ) {
            if (BuildConfig.DEBUG) {
                log(clazz.simpleName + " " + Thread.currentThread(), message, type)
            }
        }

        fun d(clazz: Class<Any>, message: String) {
            log(clazz, message, DEBUG)
        }

        fun i(clazz: Class<Any>, message: String) {
            log(clazz, message, INFO)
        }

        fun e(clazz: Class<Any>, message: String) {
            log(clazz, message, ERROR)
        }
    }
}