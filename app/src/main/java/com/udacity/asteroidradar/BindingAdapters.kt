package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.udacity.asteroidradar.main.MainViewModel

@BindingAdapter("asteroidStatusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

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
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MainViewModel.PictureOfDayApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}

