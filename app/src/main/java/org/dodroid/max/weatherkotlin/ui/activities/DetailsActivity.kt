package org.dodroid.max.weatherkotlin.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*
import org.dodroid.max.weatherkotlin.R
import org.dodroid.max.weatherkotlin.domain.commands.RequestDayForecastCommand
import org.dodroid.max.weatherkotlin.domain.model.Forecast
import org.dodroid.max.weatherkotlin.extentions.color
import org.dodroid.max.weatherkotlin.extentions.textColor
import org.dodroid.max.weatherkotlin.extentions.toDateString
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat


class DetailsActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)

        enableHomeAsUp { onBackPressed() }

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread { bindForecast(result) }
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}º"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}