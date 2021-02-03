package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domainentities.PictureOfDay

@Entity
data class DatabasePictureOfDay constructor(
        val mediaType: String,
        @PrimaryKey
        val title: String,
        val url: String)

fun DatabasePictureOfDay.asPictureOfDay(): PictureOfDay{
    return PictureOfDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}
