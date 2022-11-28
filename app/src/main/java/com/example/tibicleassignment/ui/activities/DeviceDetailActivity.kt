package com.example.tibicleassignment.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.tibicleassignment.R
import com.example.tibicleassignment.adapters.SliderAdapter
import com.example.tibicleassignment.databinding.ActivityDeviceDetailBinding
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.models.DeviceImage
import com.example.tibicleassignment.retrofit.ApiResult
import com.example.tibicleassignment.utils.convertToList
import com.example.tibicleassignment.utils.forHtmlText
import com.example.tibicleassignment.utils.showToast
import com.example.tibicleassignment.viewModels.MobileViewModel

class DeviceDetailActivity : AppCompatActivity() {

    private val _binding: ActivityDeviceDetailBinding by lazy { ActivityDeviceDetailBinding.inflate(layoutInflater) }
    private val _mobileViewModel: MobileViewModel by viewModels()
    private var textViewArray = arrayOfNulls<TextView>(1)
    private val _device by lazy { intent.getSerializableExtra("device") as Device }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(_binding.root)
        getImagesOfDevices()
        setupDetails()
    }

    private fun getImagesOfDevices() {
        _mobileViewModel.getMobileImages(_device.id).observe(this) {
            when (it) {
                is ApiResult.Success -> {
                    when (it.data) {
                        is List<*> -> {
                            val response = convertToList<DeviceImage>(it.data)
                            textViewArray = arrayOfNulls(response.size)
                            setupImagesDots(response)
                        }
                        else -> showToast(resources.getString(R.string.error_server))
                    }
                }
                is ApiResult.Failure -> showToast(resources.getString(R.string.error_server))
                is ApiResult.NoData -> showToast(resources.getString(R.string.no_data))
                is ApiResult.InProgress -> {}
            }
        }
    }

    private fun setupImagesDots(response: List<DeviceImage>) {
        addDotIndicator(0)

        _binding.sliderViewPager.adapter = SliderAdapter(response)
        _binding.sliderViewPager.addOnPageChangeListener(viewListener)
    }

    private fun setupDetails() {
        _binding.tvDeviceHeading.text = resources.getString(R.string.heading_desc)
        _binding.tvDeviceDesc.text = _device.description
        _binding.ratingBar.rating = _device.rating.toFloat()
        _binding.tvPrice.text = _device.price.toString()
    }

    private val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}
        override fun onPageSelected(position: Int) {
            addDotIndicator(position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addDotIndicator(position: Int) {

        _binding.dotLinear.removeAllViews()
        for (pos in textViewArray.indices) {
            textViewArray[pos] = TextView(this)
            textViewArray[pos]?.text = "&#8226;".forHtmlText()
            textViewArray[pos]?.textSize = 35.0F
            textViewArray[pos]?.setTextColor(ContextCompat.getColor(this, R.color.fade_background))
            _binding.dotLinear.addView(textViewArray[pos])
        }

        if (textViewArray.isNotEmpty()) {
            textViewArray[position]?.setTextColor(ContextCompat.getColor(this, R.color.purple_200))
        }
    }
}