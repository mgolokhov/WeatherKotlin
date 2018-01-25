package org.dodroid.max.weatherkotlin.data.server

import org.dodroid.max.weatherkotlin.data.db.ForecastDb
import org.dodroid.max.weatherkotlin.domain.datasource.ForecastDataSource
import org.dodroid.max.weatherkotlin.domain.model.ForecastList


class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }

    override fun requestDayForecast(id: Long) = throw UnsupportedOperationException()
}