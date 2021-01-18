package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface AsteroidApiService{
    @GET("neo/rest/v1/feed?&api_key=" + BuildConfig.API_KEY)
    suspend fun getAsteroids(): String
}

object AsteroidApi{
    val retrofitAsteroidService: AsteroidApiService by lazy {
        retrofit.create(AsteroidApiService::class.java)
    }
}
