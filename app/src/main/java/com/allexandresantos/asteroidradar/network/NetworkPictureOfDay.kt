package com.allexandresantos.asteroidradar.network

import com.squareup.moshi.Json
import com.allexandresantos.asteroidradar.database.DatabasePictureOfDay


data class DataTransferPictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                                    val url: String)

fun DataTransferPictureOfDay.asDatabasePicture(): DatabasePictureOfDay{
    return DatabasePictureOfDay(
            mediaType = this.mediaType,
            title = this.title,
            url = this.url
    )
}

//fun DataTransferPictureOfDay.asPictureOfDay(): PictureOfDay{
//    return PictureOfDay(
//            mediaType = this.mediaType,
//            title = this.title,
//            url = this.url
//            )
//}
