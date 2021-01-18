package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET

//ntZ5nWFcadESXiMq7EPvURQRZQEbK7kIxec6AfnP


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



//private const val PICTURE_OF_DAY_BASE_URL = "https://api.nasa.gov/planetary/"
//
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(PICTURE_OF_DAY_BASE_URL)
//    .build()
//
//interface PictureOfDayApiService {
//    @GET("apod?api_key=" + BuildConfig.API_KEY)
//    suspend fun getPicture(): PictureOfDay
//}
//
//object PictureOfDayApi {
//    val retrofitPictureService: PictureOfDayApiService by lazy {
//        retrofit.create(PictureOfDayApiService::class.java)
//    }
//}


//private val retrofit = Retrofit.Builder()
//        .addConverterFactory(ScalarsConverterFactory.create())
//        .baseUrl(BASE_URL)
//        .build()
//
///**
// * A public interface that exposes the [getProperties] method
// */
//interface MarsApiService {
//    /**
//     * Returns a Retrofit callback that delivers a String
//     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
//     * HTTP method
//     */
//    @GET("realestate")
//    fun getProperties(): Call<String>
//}
//
///**
// * A public Api object that exposes the lazy-initialized Retrofit service
// */
//object MarsApi {
//    val retrofitService : MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
//}