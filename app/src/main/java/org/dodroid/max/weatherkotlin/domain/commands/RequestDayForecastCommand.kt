package org.dodroid.max.weatherkotlin.domain.commands

import org.dodroid.max.weatherkotlin.domain.model.Forecast
import org.dodroid.max.weatherkotlin.domain.datasource.ForecastProvider


class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<Forecast> {

    override fun execute() = forecastProvider.requestForecast(id)
}