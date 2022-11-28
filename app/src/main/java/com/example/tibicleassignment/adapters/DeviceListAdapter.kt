package com.example.tibicleassignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView
import com.example.tibicleassignment.R
import com.example.tibicleassignment.models.Device
import com.example.tibicleassignment.utils.OnRecyclerViewItemClickListener
import com.example.tibicleassignment.utils.setImage
import com.google.android.material.textview.MaterialTextView

class DeviceListAdapter(
    private val dataList: MutableList<Device>,
    private val callback: OnRecyclerViewItemClickListener<Device>
) :
    RecyclerView.Adapter<DeviceListAdapter.BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return DeviceListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = dataList[position]
        when (holder) {
            is DeviceListViewHolder -> holder.bind(element)
        }
    }

    inner class DeviceListViewHolder(itemView: View) : BaseViewHolder<Device>(itemView) {

        private val ivDevice = itemView.findViewById(R.id.iv_device_img) as ImageView
        private val ivFavorite = itemView.findViewById(R.id.iv_favorite) as ImageView
        private val tvDeviceName = itemView.findViewById(R.id.tv_device_name) as MaterialTextView
        private val tvDeviceDescription =
            itemView.findViewById(R.id.tv_device_desc) as MaterialTextView
        private val ratingBar = itemView.findViewById(R.id.rating_bar) as RatingBar
        private val tvDevicePrice = itemView.findViewById(R.id.tv_price) as MaterialTextView

        override fun bind(item: Device) {
            ivDevice.setImage(item.thumbImageURL)
            tvDeviceName.text = item.deviceName
            tvDeviceDescription.text = item.description
            ratingBar.rating = item.rating.toFloat()
            tvDevicePrice.text = item.price.toString()
            when {
                item.isFavorite -> ivFavorite.setImageResource(R.drawable.favorite_filled)
                else -> ivFavorite.setImageResource(R.drawable.favorite)
            }
            ivFavorite.setOnClickListener { view -> callback.invoke(view, adapterPosition, item) }
            itemView.setOnClickListener { callback.invoke(itemView, adapterPosition, item) }
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    fun addList(list: List<Device>) {
        dataList.clear()
        dataList.addAll(list)
        notifyDataSetChanged()
    }

    fun deviceRemoved(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun markAsFavorite(position: Int) {
        dataList[position].isFavorite = !dataList[position].isFavorite
        notifyItemChanged(position)
    }

    fun removeMarkAsFavorite(device: Device) {
        dataList.forEach {
            if (device.id == it.id) {
                it.isFavorite = false
                notifyItemRangeChanged(0, dataList.size)
                return
            }
        }
    }

    fun sortingDeviceList(type : Int) {
        when (type) {
            1 -> dataList.sortBy { it.price }
            2 -> dataList.sortByDescending { it.price }
            3 -> dataList.sortByDescending { it.rating }
        }
        notifyDataSetChanged()
    }

    val listOfMobile = dataList
}
