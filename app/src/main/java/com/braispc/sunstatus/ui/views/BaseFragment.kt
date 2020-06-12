package com.braispc.sunstatus.ui.views

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.braispc.sunstatus.common.Constants

open class BaseFragment: Fragment() {
    companion object {

        @BindingAdapter("drawableName")
        fun ImageView.setDrawableName(drawableName: String) {
            setImageURI(Uri.parse("${Constants.IMAGE_RESOURCE_PATH}${drawableName}"))
        }
    }
}