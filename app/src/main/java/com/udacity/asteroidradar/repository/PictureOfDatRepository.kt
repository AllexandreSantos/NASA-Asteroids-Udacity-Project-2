package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidsPodDatabase
import com.udacity.asteroidradar.database.asPictureOfDay
import com.udacity.asteroidradar.domainentities.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.asDatabasePicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class PictureOfDatRepository (private val database: AsteroidsPodDatabase){

    val pictureOfDay: LiveData<PictureOfDay> = Transformations.map(database.pictureOfDayDao.getPictureOfDay()){
        it.asPictureOfDay()
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
        val TAG = PictureOfDatRepository::class.java.simpleName
    }

}