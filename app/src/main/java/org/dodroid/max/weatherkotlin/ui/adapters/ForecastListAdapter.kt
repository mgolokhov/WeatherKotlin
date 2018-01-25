package org.dodroid.max.weatherkotlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*
import org.dodroid.max.weatherkotlin.R
import org.dodroid.max.weatherkotlin.domain.model.Forecast
import org.dodroid.max.weatherkotlin.domain.model.ForecastList
import org.dodroid.max.weatherkotlin.extentions.ctx
import org.dodroid.max.weatherkotlin.extentions.toDateString
import java.text.DateFormat
import java.util.*


class ForecastListAdapter(private val weekForecast: ForecastList,
                          private val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bindForecast(weekForecast[position])

    override fun getItemCount() = weekForecast.size

    class ViewHolder(view: View, private val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                // synthetic find views outside of Activity or Fragment is not cached
                Picasso.with(itemView.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = date.toDateString()
                itemView.description.text = description
                itemView.maxTemperature.text = "${high}º"
                itemView.minTemperature.text = "${low}º"
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}