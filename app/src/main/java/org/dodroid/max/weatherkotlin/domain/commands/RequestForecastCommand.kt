package org.dodroid.max.weatherkotlin.domain.commands

import org.dodroid.max.weatherkotlin.data.ForecastRequest
import org.dodroid.max.weatherkotlin.domain.mappers.ForecastDataMapper
import org.dodroid.max.weatherkotlin.domain.model.ForecastList


class RequestForecastCommand(private val zipCode: Long): Command<ForecastList>{
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(zipCode)
        return ForecastDataMapper().convertFromDataModel(zipCode, forecastRequest.execute())
    }
}