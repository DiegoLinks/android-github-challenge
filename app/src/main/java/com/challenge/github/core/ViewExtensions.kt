package com.challenge.github.core

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import android.widget.TextView

fun isDarkTheme(context: Context): Boolean {
    val currentNightMode =
        context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun groupVisibility(text: String?, imageView: ImageView, textView: TextView) {
    if (text.isNullOrEmpty()) {
        imageView.gone()
        textView.gone()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}