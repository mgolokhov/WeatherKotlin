package org.dodroid.max.weatherkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.dodroid.max.weatherkotlin.R
import org.dodroid.max.weatherkotlin.domain.commands.RequestForecastCommand
import org.dodroid.max.weatherkotlin.ui.adapters.ForecastListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        forecastList.layoutManager = LinearLayoutManager(this)

        doAsync {
            val result = RequestForecastCommand(141503).execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter(result,  {toast(it.description)} )
            }
        }
    }
}
