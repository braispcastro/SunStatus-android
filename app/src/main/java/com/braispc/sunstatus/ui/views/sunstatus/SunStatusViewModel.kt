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
    var sunFirstText: MutableLiveData<String> = MutableLiveData()
    var sunFirstImage: MutableLiveData<String> = MutableLiveData()
    var sunSecondText: MutableLiveData<String> = MutableLiveData()
    var sunSecondImage: MutableLiveData<String> = MutableLiveData()

    init {

        locationText.value = "A Veiga, Galicia, Spain"
        latitudeText.value = "-"
        longitudeText.value = "-"
        sunFirstText.value = "-"
        sunSecondText.value = "-"
    }

    fun getLocationData() = locationData

    fun updateLocation() {
        latitudeText.value = locationData.value?.latitude.toString()
        longitudeText.value = locationData.value?.longitude.toString()

        var sunStatus = sunUtils.getNextSunriseAndSunset(latitudeText.value.toString(), longitudeText.value.toString(), apiKey)
        sunFirstText.value = sunStatus.nextSunFirst
        sunSecondText.value = sunStatus.nextSunSecond
        sunFirstImage.value = if (sunStatus.isSunriseFirst) "ic_sunrise" else "ic_sunset"
        sunSecondImage.value = if (sunStatus.isSunriseFirst) "ic_sunset" else "ic_sunrise"
    }
}