package com.zakrodionov.common.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.zakrodionov.common.R
import com.zakrodionov.common.extensions.load

class LoopingPhotoAdapter(private val onPhotoClick: (String) -> Unit) : PagerAdapter() {

    companion object {
        const val ENDLESS_OFFSET = 10_000
    }

    private val photos = mutableListOf<String>()

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val photo = photos[getRealIndex(position)]
        val inflater = LayoutInflater.from(collection.context)
        val ivPhoto = inflater.inflate(R.layout.layout_photo_page, collection, false) as ImageView
        with(ivPhoto) {
            load(photo)
            setOnClickListener { onPhotoClick.invoke(photo) }
        }
        collection.addView(ivPhoto)
        return ivPhoto
    }

    override fun getCount(): Int = photos.size * ENDLESS_OFFSET

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = view == obj

    fun getRealIndex(position: Int) = if (photos.size == 0) 0 else position % photos.size

    fun getRealCount(): Int = photos.size

    fun getCenterPage(position: Int = 0) = count / 2 + position

    fun setData(newPhotos: List<String>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }
}
