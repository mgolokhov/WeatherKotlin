package org.dodroid.max.weatherkotlin.domain.commands

import org.dodroid.max.weatherkotlin.domain.datasource.ForecastProvider
import org.dodroid.max.weatherkotlin.domain.model.ForecastList


class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()) :
        Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)
}