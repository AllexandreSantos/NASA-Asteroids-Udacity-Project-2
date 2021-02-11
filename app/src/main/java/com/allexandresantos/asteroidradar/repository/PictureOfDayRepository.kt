package com.allexandresantos.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.allexandresantos.asteroidradar.database.AsteroidsPodDatabase
import com.allexandresantos.asteroidradar.database.asPictureOfDay
import com.allexandresantos.asteroidradar.domainentities.PictureOfDay
import com.allexandresantos.asteroidradar.network.NasaApi
import com.allexandresantos.asteroidradar.network.asDatabasePicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PictureOfDayRepository (private val database: AsteroidsPodDatabase){

    val pictureOfDay: LiveData<PictureOfDay> = Transformations.map(database.pictureOfDayDao.getPictureOfDay()){
        it?.asPictureOfDay()
    }

    suspend fun refreshPictureOfDay(){
        withContext(Dispatchers.IO){
            try {
                val dataTransferPictureOfDay = NasaApi.retrofitPictureService.getPicture()
                database.pictureOfDayDao.insertPicture(dataTransferPictureOfDay.asDatabasePicture())
            }

            catch (e: Exception){
                Log.e(TAG, "refreshPictureOfDay ", e)
            }

        }
    }

    companion object{
        val TAG = PictureOfDayRepository::class.java.simpleName
    }

}