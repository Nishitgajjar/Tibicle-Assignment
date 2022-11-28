package com.example.tibicleassignment.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.tibicleassignment.R
import com.example.tibicleassignment.models.DeviceImage
import com.example.tibicleassignment.utils.setImage

class SliderAdapter(private val listOfImages: List<DeviceImage>) : PagerAdapter() {

    override fun getCount() = listOfImages.size

    override fun isViewFromObject(view: View, any: Any) = view == any as FrameLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = container.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)
        val imgView = view.findViewById(R.id.iv_slider) as ImageView
        imgView.setImage(listOfImages[position].mobileImgUrl)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) = container.removeView(any as FrameLayout)
}