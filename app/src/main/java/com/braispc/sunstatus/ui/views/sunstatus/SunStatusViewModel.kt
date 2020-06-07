package com.braispc.sunstatus.ui.views.sunstatus

import androidx.lifecycle.MutableLiveData
import com.braispc.sunstatus.ui.views.MainViewModel

class SunStatusViewModel: MainViewModel() {

    var locationText: MutableLiveData<String> = MutableLiveData()
    var latitudeText: MutableLiveData<String> = MutableLiveData()
    var longitudeText: MutableLiveData<String> = MutableLiveData()
    var sunriseText: MutableLiveData<String> = MutableLiveData()
    var sunsetText: MutableLiveData<String> = MutableLiveData()

    init {
        locationText.value = "A Veiga, Galicia, Spain"
        latitudeText.value = "42.249638"
        longitudeText.value = "-7.025272"
        sunriseText.value = "2020-06-07 06:53"
        sunsetText.value = "2020-06-07 22:12"
    }
}