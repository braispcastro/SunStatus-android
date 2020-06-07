package com.braispc.sunstatus.ui.views.sunstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.braispc.sunstatus.R
import com.braispc.sunstatus.common.Constants
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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

        return binding.root
    }
}