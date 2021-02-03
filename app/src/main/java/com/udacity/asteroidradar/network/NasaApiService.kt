package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.domainentities.PictureOfDay
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofitPicture = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

private val retrofitAsteroid = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface NasaApiService {
    @GET("planetary/apod?api_key=" + BuildConfig.API_KEY)
    suspend fun getPicture(): DataTransferPictureOfDay

    @GET("neo/rest/v1/feed?&api_key=" + BuildConfig.API_KEY)
    suspend fun getAsteroids(): String
}

object NasaApi {
    val retrofitPictureService: NasaApiService by lazy {
        retrofitPicture.create(NasaApiService::class.java)
    }

    val retrofitAsteroidService: NasaApiService by lazy {
        retrofitAsteroid.create(NasaApiService::class.java)
    }
}
