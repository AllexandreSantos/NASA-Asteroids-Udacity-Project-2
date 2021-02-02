package com.udacity.asteroidradar.network

import com.squareup.moshi.Json
import com.udacity.asteroidradar.domainentities.PictureOfDay


data class DataTransferPictureOfDay(@Json(name = "media_type") val mediaType: String?, val title: String?,
                                    val url: String?)

//TODO
//fun DataTransferPictureOfDay.asPictureOfDay(): PictureOfDay{}


//fun DataTransferAsteroidContainer.asDatabaseAsteroid(): Array<DatabaseAsteroid> {
//    return asteroids.map {
//        DatabaseAsteroid(
//            id = it.id,
//            codename = it.codename,
//            closeApproachDate = it.closeApproachDate,
//            absoluteMagnitude = it.absoluteMagnitude,
//            estimatedDiameter = it.estimatedDiameter,
//            relativeVelocity = it.relativeVelocity,
//            distanceFromEarth = it.distanceFromEarth,
//            isPotentiallyHazardous = it.isPotentiallyHazardous
//        )
//    }.toTypedArray()
//}