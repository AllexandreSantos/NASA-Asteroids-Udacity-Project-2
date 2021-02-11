package com.allexandresantos.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.allexandresantos.asteroidradar.database.AsteroidsPodDatabase
import com.allexandresantos.asteroidradar.database.asDomainEntity
import com.allexandresantos.asteroidradar.domainentities.Asteroid
import com.allexandresantos.asteroidradar.network.NasaApi
import com.allexandresantos.asteroidradar.network.asDatabaseAsteroid
import com.allexandresantos.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidsRepository (private val database: AsteroidsPodDatabase){

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it?.asDomainEntity()
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try {
                val stringResponse = NasaApi.retrofitAsteroidService.getAsteroids()
                val jsonObject = JSONObject(stringResponse)
                val dataTransferAsteroids = parseAsteroidsJsonResult(jsonObject)
                database.asteroidDao.insertAllAsteroids(*dataTransferAsteroids.asDatabaseAsteroid())
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