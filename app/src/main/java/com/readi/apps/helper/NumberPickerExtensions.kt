package com.readi.apps.helper

import com.shawnlin.numberpicker.NumberPicker
import androidx.core.content.res.ResourcesCompat
import android.content.Context

fun NumberPicker.setCustomFont(context: Context, fontRes: Int) {
    val typeface = ResourcesCompat.getFont(context, fontRes)
    this.typeface = typeface
}
