package com.zakrodionov.common.custom.viewpager2

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.common.R
import com.zakrodionov.common.custom.viewpager2.LoopingPhotoAdapter.PhotoViewHolder
import com.zakrodionov.common.extensions.inflate
import com.zakrodionov.common.extensions.load

// Looping adapter for ViewPager2 and RecyclerView
// Typical usage:
// adapter.setItems(photos)
// vp2.adapter = adapter
// vp2.setCurrentItem(adapter.getCenterPage(), false)
class LoopingPhotoAdapter(private val onPhotoClick: (String) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {

    companion object {
        private const val ENDLESS_OFFSET = 1_000_000
    }

    private val photos = mutableListOf<String>()

    override fun getItemCount(): Int = if (photos.size <= 1) photos.size else photos.size * ENDLESS_OFFSET

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(viewGroup.inflate(R.layout.layout_photo_page))
    }

    override fun onBindViewHolder(viewHolder: PhotoViewHolder, position: Int) {
        viewHolder.bind(photos[getRealIndex(position)])
    }

    fun getRealIndex(position: Int) = if (photos.size <= 1) 0 else position % photos.size

    fun getRealCount(): Int = photos.size

    // @param position - position in photos list. Default 0 (first).
    fun getCenterPage(position: Int = 0) = itemCount / 2 + position

    fun setItems(newPhotos: List<String>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var photo: String

        init {
            view.setOnClickListener {
                onPhotoClick.invoke(photo)
            }
        }

        fun bind(photo: String) {
            this.photo = photo
            itemView.findViewById<ImageView>(R.id.ivPhoto).load(photo)
        }
    }
}
