package com.braispc.sunstatus.ui.views.sunstatus

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.braispc.sunstatus.R
import com.braispc.sunstatus.common.Constants
import com.braispc.sunstatus.common.Constants.Companion.GPS_REQUEST
import com.braispc.sunstatus.common.Constants.Companion.LOCATION_REQUEST
import com.braispc.sunstatus.core.GpsUtils
import com.braispc.sunstatus.databinding.SunStatusFragmentBinding
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class SunStatusFragment: Fragment() {

    companion object {
        fun newInstance() = SunStatusFragment()
    }

    private lateinit var imgSun: ImageView
    private lateinit var binding: SunStatusFragmentBinding
    private val viewModel: SunStatusViewModel by navGraphViewModels(R.id.sunStatusFragment)

    private var isGPSEnabled = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        GpsUtils(requireContext()).turnGPSOn(object : GpsUtils.OnGpsListener {

            override fun gpsStatus(isGPSEnable: Boolean) {
                isGPSEnabled = isGPSEnable
            }
        })

        imgSun = (activity as AppCompatActivity).findViewById(R.id.imgSun)
        Picasso.get()
            .load(Constants.SUN_URL)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .centerInside()
            .fit()
            .into(imgSun)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel.apiKey = getString(R.string.meteoSixApi)

        binding = DataBindingUtil.inflate(inflater, R.layout.sun_status_fragment, container, false)

        // Properties

        viewModel.locationText.observe(viewLifecycleOwner, Observer { x ->
            binding.tvLocation.text = x
        })

        viewModel.latitudeText.observe(viewLifecycleOwner, Observer { x ->
            binding.tvLatitude.text = x
        })

        viewModel.longitudeText.observe(viewLifecycleOwner, Observer { x ->
            binding.tvLongitude.text = x
        })

        viewModel.sunsetText.observe(viewLifecycleOwner, Observer { x ->
            binding.tvSunset.text = x
        })

        viewModel.sunriseText.observe(viewLifecycleOwner, Observer { x ->
            binding.tvSunrise.text = x
        })

        binding.imgSun.setOnClickListener { invokeLocationAction() }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        invokeLocationAction()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GPS_REQUEST) {
                isGPSEnabled = true
                invokeLocationAction()
            }
        }
    }

    private fun invokeLocationAction() {
        when {

            isPermissionsGranted() -> startLocationUpdate()

            shouldShowRequestPermissionRationale() -> { }//latLong.text = getString(R.string.permission_request)

            else -> ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_REQUEST
            )
        }
    }

    private fun startLocationUpdate() {
        viewModel.getLocationData().observe(this, Observer {
            viewModel.updateLocation()
        })
    }

    private fun isPermissionsGranted() =
        ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    private fun shouldShowRequestPermissionRationale() =
        ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) && ActivityCompat.shouldShowRequestPermissionRationale(
            requireActivity(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_REQUEST -> {
                invokeLocationAction()
            }
        }
    }
}