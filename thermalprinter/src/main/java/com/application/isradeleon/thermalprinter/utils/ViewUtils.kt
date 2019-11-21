package com.application.isradeleon.thermalprinter.utils

import android.view.View
import android.widget.Toast

fun View.toast(message: String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}