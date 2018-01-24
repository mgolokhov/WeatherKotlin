package org.dodroid.max.weatherkotlin.domain.mappers

import org.dodroid.max.weatherkotlin.data.Forecast
import org.dodroid.max.weatherkotlin.data.ForecastResult
import org.dodroid.max.weatherkotlin.domain.model.ForecastList
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import org.dodroid.max.weatherkotlin.domain.model.Forecast as ModelForecast


class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult) =
            ForecastList(forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))

    private fun convertForecastListToDomain(list: List<Forecast>): List<ModelForecast> =
            list.mapIndexed { i, forecast ->
            val dt = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(i.toLong())
            convertForecastItemToDomain(forecast.copy(dt = dt))
        }

    private fun convertForecastItemToDomain(forecast: Forecast) =
            ModelForecast(convertDate(forecast.dt),
                    forecast.weather[0].description,
                    forecast.temp.max.toInt(),
                    forecast.temp.min.toInt(),
                    generateIconUrl(forecast.weather[0].icon)
            )

    private fun convertDate(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
        return df.format(date)
    }

    private fun generateIconUrl(iconCode: String) = "http://openweathermap.org/img/w/$iconCode.png"
}