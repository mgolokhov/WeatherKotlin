package org.dodroid.max.weatherkotlin.extentions

import android.content.Context
import android.view.View
import android.widget.TextView

// For consistency with Anko extensions
val View.ctx: Context
    get() = context


var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)