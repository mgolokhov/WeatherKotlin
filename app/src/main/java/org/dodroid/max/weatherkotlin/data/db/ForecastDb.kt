package org.dodroid.max.weatherkotlin.data.db

import org.dodroid.max.weatherkotlin.domain.datasource.ForecastDataSource
import org.dodroid.max.weatherkotlin.domain.model.ForecastList
import org.dodroid.max.weatherkotlin.extentions.clear
import org.dodroid.max.weatherkotlin.extentions.parseList
import org.dodroid.max.weatherkotlin.extentions.parseOpt
import org.dodroid.max.weatherkotlin.extentions.toVarargArray
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select


class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()): ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = {id} AND ${DayForecastTable.DATE} >= {date}"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereArgs(dailyRequest, "id" to zipCode, "date" to date)
                .parseList { DayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        if (city != null) dataMapper.convertToDomain(city) else null
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            insert(CityForecastTable.NAME, *map.toVarargArray())
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}