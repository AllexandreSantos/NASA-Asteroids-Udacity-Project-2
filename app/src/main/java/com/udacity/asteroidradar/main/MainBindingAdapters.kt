package com.udacity.asteroidradar.main

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.R.*
import com.udacity.asteroidradar.domainentities.DataTransferAsteroid
import com.udacity.asteroidradar.domainentities.PictureOfDay

//It's very important to mark the object as nullable
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

@BindingAdapter("pictureApiStatus")
fun bindPictureApiStatus(statusImageView: ImageView, status: MainViewModel.Status?){
    when (status) {
        MainViewModel.Status.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(drawable.loading_animation)
        }
        MainViewModel.Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(drawable.ic_broken_image)
        }
        MainViewModel.Status.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("asteroidStatusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(drawable.ic_status_normal)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<DataTransferAsteroid>?){
    val adapter = recyclerView.adapter as AsteroidListAdapter
    adapter.submitList(data)
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
fun bindAsteroidApiStatus(progressBar: ProgressBar, status: MainViewModel.Status?){
    when(status){
        MainViewModel.Status.LOADING -> progressBar.visibility = View.VISIBLE
        else -> progressBar.visibility = View.INVISIBLE
    }
}

@BindingAdapter("brokenDataImage")
fun bindBrokenData(imageView: ImageView, status: MainViewModel.Status?){
    when(status){
        MainViewModel.Status.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(drawable.ic_connection_error)
        }
        else -> imageView.visibility = View.INVISIBLE
    }
}

