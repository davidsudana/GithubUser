package com.david.githubmobile.utils

import android.view.View

interface StateApp<T> {
    fun onLoading()

    fun onSuccess(data: T)

    fun onFailed(message: String?)

    val gone: Int
        get() = View.GONE

    val visible: Int
        get() = View.VISIBLE
}