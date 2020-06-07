package com.braispc.sunstatus.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.braispc.sunstatus.R
import com.braispc.sunstatus.common.Constants
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

lateinit var imgSun: ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgSun = findViewById(R.id.imgSun)
    }

    override fun onResume() {
        super.onResume()

        Picasso.get()
            .load(Constants.SUN_URL)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerInside()
            .fit()
            .into(imgSun)
    }
}