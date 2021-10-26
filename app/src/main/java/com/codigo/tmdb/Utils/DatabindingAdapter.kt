package com.codigo.tmdb.Utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImageFromUrl")
fun ImageView.loadImageFromUrl( movieImage : String ) {
    Glide.with(context)
        .load(Utils.TMDB_IMAGE_BASE_URL+movieImage)
        .override(300, 400)
        .into(this)
}

