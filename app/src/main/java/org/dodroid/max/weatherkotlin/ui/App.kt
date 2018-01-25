package org.dodroid.max.weatherkotlin.ui

import android.app.Application
import org.dodroid.max.weatherkotlin.extentions.DelegatesExt


class App: Application() {
    companion object {
        var instance: App by DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}