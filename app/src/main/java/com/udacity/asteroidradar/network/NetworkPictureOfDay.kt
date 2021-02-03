package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.udacity.asteroidradar.database.DatabasePictureOfDay
import com.udacity.asteroidradar.domainentities.PictureOfDay
import kotlinx.coroutines.test.withTestContext


data class DataTransferPictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                                    val url: String)

fun DataTransferPictureOfDay.asPictureOfDay(): PictureOfDay{
    return PictureOfDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
            )
}

fun DataTransferPictureOfDay.asDatabasePicture(): DatabasePictureOfDay{
    return DatabasePictureOfDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}
