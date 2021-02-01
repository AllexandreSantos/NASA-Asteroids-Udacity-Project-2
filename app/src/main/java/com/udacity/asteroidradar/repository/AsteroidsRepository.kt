package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidsDatabase
import com.udacity.asteroidradar.database.asDomainEntity
import com.udacity.asteroidradar.domainentities.Asteroid
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.asDatabaseAsteroid
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidsRepository (private val database: AsteroidsDatabase){

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it.asDomainEntity()
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try {
                val stringResponse = NasaApi.retrofitAsteroidService.getAsteroids()
                val jsonObject = JSONObject(stringResponse)
                val dataTransferAsteroids = parseAsteroidsJsonResult(jsonObject)
                database.asteroidDao.insertAll(*dataTransferAsteroids.asDatabaseAsteroid())
            }

            catch (e: Exception){
                Log.e(TAG, "refreshAsteroids: ", e)
            }

        }
    }

    companion object{
        val TAG = AsteroidsRepository::class.java.simpleName
    }

}