package com.braispc.sunstatus.core

import com.braispc.sunstatus.model.SunModel
import com.braispc.sunstatus.model.SunDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException


class SunUtils {

    fun getNextSunriseAndSunset(latitude: String, longitude: String, apiKey: String): SunDTO {
        var sunriseAndSunset = requestSunriseAndSunset(latitude, longitude, apiKey)
        return parseSunriseAndSunset(sunriseAndSunset)
    }

    private fun requestSunriseAndSunset(latitude: String, longitude: String, apiKey: String): SunModel? {
        var sunModel: SunModel? = null
        val url = "http://servizos.meteogalicia.es/apiv3/getSolarInfo?coords=${longitude},${latitude}&API_KEY=${apiKey}"

        runBlocking {
            withContext(Dispatchers.Default) {
                try {
                    val connection = URL(url).openConnection() as HttpURLConnection
                    val json = connection.inputStream.bufferedReader().readText()
                    sunModel = Gson().fromJson(json, SunModel::class.java)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
            }
        }

        return sunModel
    }

    private fun parseSunriseAndSunset(sunModel: SunModel?): SunDTO {
        lateinit var nextSunrise: DateTime
        lateinit var nextSunset: DateTime

        val today = sunModel?.features?.get(0)?.properties?.days?.get(0)?.variables?.get(0)
        val tomorrow = sunModel?.features?.get(0)?.properties?.days?.get(1)?.variables?.get(0)

        try {
            val todaySunrise = DateTime.parse(today?.sunrise)
            val todaySunset = DateTime.parse(today?.sunset)
            val tomorrowSunrise = DateTime.parse(tomorrow?.sunrise)
            val tomorrowSunset = DateTime.parse(tomorrow?.sunset)

            nextSunrise = if (DateTime.now() > todaySunrise) tomorrowSunrise else todaySunrise
            nextSunset = if (DateTime.now() > todaySunset) tomorrowSunset else todaySunset
        }
        catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (nextSunset > nextSunrise)
            SunDTO(nextSunrise.toString("yyyy-MM-dd HH:mm"), nextSunset.toString("yyyy-MM-dd HH:mm"), true)
        else
            SunDTO(nextSunset.toString("yyyy-MM-dd HH:mm"), nextSunrise.toString("yyyy-MM-dd HH:mm"), false)
    }
}