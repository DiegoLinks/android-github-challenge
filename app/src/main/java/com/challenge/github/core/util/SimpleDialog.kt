package com.challenge.github.core.util

import android.app.AlertDialog
import android.content.Context

class SimpleDialog {
    companion object {
        fun showSimpleDialog(context: Context, message: String) {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setMessage(message)
            alertDialogBuilder.setPositiveButton("Ok") { dialog, _ -> dialog.dismiss() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }
}