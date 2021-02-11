package com.allexandresantos.asteroidradar.main

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.allexandresantos.asteroidradar.R.*
import com.allexandresantos.asteroidradar.domainentities.Asteroid
import com.allexandresantos.asteroidradar.domainentities.PictureOfDay
import com.allexandresantos.asteroidradar.main.adapters.AsteroidListAdapter

//It's very important to mark the objects as nullable
@BindingAdapter("pictureOfDay")
fun bindPictureOfDay(imageView: ImageView, pictureOfDay: PictureOfDay?){
    if (pictureOfDay?.mediaType == "image"){
        pictureOfDay.url?.let {
            val imgUri = pictureOfDay.url.toUri().buildUpon().scheme("https").build()
            Glide.with(imageView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(drawable.loading_animation)
                        .error(drawable.ic_broken_image))
                .into(imageView)
        }
    }else if(pictureOfDay?.mediaType == "video") {
        Glide.with(imageView.context)
            .load(drawable.ic_broken_image)
            .into(imageView)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?){
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
}

@BindingAdapter("asteroidStatusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(drawable.ic_status_normal)
    }
}

@BindingAdapter("backgroundColor")
fun bindBackgroundColor(background: ConstraintLayout, isHazardous: Boolean) {
    if (isHazardous) {
        background.setBackgroundColor(background.resources.getColor(color.background_potencially_hazardous))
    } else {
        background.setBackgroundColor(background.resources.getColor(color.app_background))
    }
}

@BindingAdapter("asteroidApiStatus")
fun bindAsteroidApiStatus(progressBar: ProgressBar, data: List<Asteroid>?){
    when(data.isNullOrEmpty()){
        true -> progressBar.visibility = View.VISIBLE
        else -> progressBar.visibility = View.INVISIBLE
    }
}