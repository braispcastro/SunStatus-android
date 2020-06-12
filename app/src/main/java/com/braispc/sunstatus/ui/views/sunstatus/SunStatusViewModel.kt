package com.braispc.sunstatus.ui.views.sunstatus

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.braispc.sunstatus.core.LocationLiveData
import com.braispc.sunstatus.core.SunUtils
import com.braispc.sunstatus.ui.views.MainViewModel

class SunStatusViewModel(application: Application): MainViewModel(application) {

    private val locationData = LocationLiveData(application)
    private val sunUtils = SunUtils()

    lateinit var apiKey: String

    var locationText: MutableLiveData<String> = MutableLiveData()
    var latitudeText: MutableLiveData<String> = MutableLiveData()
    var longitudeText: MutableLiveData<String> = MutableLiveData()
    var sunriseText: MutableLiveData<String> = MutableLiveData()
    var sunsetText: MutableLiveData<String> = MutableLiveData()

    init {

        locationText.value = "A Veiga, Galicia, Spain"
        latitudeText.value = "-"
        longitudeText.value = "-"
        sunriseText.value = "-"
        sunsetText.value = "-"
    }

    fun getLocationData() = locationData

    fun updateLocation() {
        latitudeText.value = locationData.value?.latitude.toString()
        longitudeText.value = locationData.value?.longitude.toString()

        var sunStatus = sunUtils.getNextSunriseAndSunset(latitudeText.value.toString(), longitudeText.value.toString(), apiKey)
        sunriseText.value = sunStatus.nextSunrise
        sunsetText.value = sunStatus.nextSunset
    }
}