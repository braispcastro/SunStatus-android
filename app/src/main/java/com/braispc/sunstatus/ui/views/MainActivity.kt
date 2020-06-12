package com.braispc.sunstatus.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.braispc.sunstatus.R
import net.danlew.android.joda.JodaTimeAndroid

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        JodaTimeAndroid.init(this);
    }
}