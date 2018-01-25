package org.dodroid.max.weatherkotlin.extentions

import android.content.Context
import android.view.View

// For consistency with Anko extensions
val View.ctx: Context
    get() = context