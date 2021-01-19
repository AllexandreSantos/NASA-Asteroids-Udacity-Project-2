package com.udacity.asteroidradar.main

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.entities.PictureOfDay

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
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imageView)
        }
    }else if(pictureOfDay?.mediaType == "video") {
        Glide.with(imageView.context)
            .load(R.drawable.ic_broken_image)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation))
            .into(imageView)
    }
}

@BindingAdapter("pictureApiStatus")
fun bindPictureApiStatus(statusImageView: ImageView, status: MainViewModel.PictureOfDayApiStatus?){
    when (status) {
        MainViewModel.PictureOfDayApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MainViewModel.PictureOfDayApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_broken_image)
        }
        MainViewModel.PictureOfDayApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

@BindingAdapter("asteroidStatusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}