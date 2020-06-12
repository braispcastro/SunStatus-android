package com.braispc.sunstatus.core

import SunModel
import android.annotation.SuppressLint
import com.braispc.sunstatus.model.SunDTO
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.ISODateTimeFormat
import java.net.HttpURLConnection
import java.net.URL
import java.text.ParseException
import java.time.Instant
import java.time.format.DateTimeFormatter


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
        var nextSunrise = ""
        var nextSunset = ""

        val today = sunModel?.features?.get(0)?.properties?.days?.get(0)?.variables?.get(0)
        val tomorrow = sunModel?.features?.get(0)?.properties?.days?.get(1)?.variables?.get(0)

        try {
            val todaySunrise = DateTime.parse(today?.sunrise)
            val todaySunset = DateTime.parse(today?.sunset)
            val tomorrowSunrise = DateTime.parse(tomorrow?.sunrise)
            val tomorrowSunset = DateTime.parse(tomorrow?.sunset)

            nextSunrise = if (DateTime.now() > todaySunrise) tomorrowSunrise.toString("yyyy-MM-dd HH:mm") else todaySunrise.toString("yyyy-MM-dd HH:mm")
            nextSunset = if (DateTime.now() > todaySunset) tomorrowSunset.toString("yyyy-MM-dd HH:mm") else todaySunset.toString("yyyy-MM-dd HH:mm")
        }
        catch (e: ParseException) {
            e.printStackTrace()
        }

        return SunDTO(nextSunrise, nextSunset)
    }
}