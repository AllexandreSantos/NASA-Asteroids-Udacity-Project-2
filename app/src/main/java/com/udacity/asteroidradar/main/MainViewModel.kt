package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Event
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domainentities.Asteroid
import com.udacity.asteroidradar.domainentities.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)

    private val asteroidsRepository = AsteroidsRepository(database)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _pictureStatus = MutableLiveData<Status>()

    val pictureStatus: LiveData<Status>
        get() = _pictureStatus

    private val _asteroidListStatus = MutableLiveData<Status>()

    val asteroidListStatus: LiveData<Status>
        get() = _asteroidListStatus

    private var stringResponse: String? = null

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    val _navigateToAsteroidDetail = MutableLiveData<Event<Asteroid>>()

    val navigateToAsteroidDetail: LiveData<Event<Asteroid>>
        get() = _navigateToAsteroidDetail


    init {
//        getAsteroids()
        getPictureOfTheDay()

        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    val test = asteroidsRepository.asteroids.value

    private fun getAsteroids() {
        viewModelScope.launch {

            _asteroidListStatus.value = Status.LOADING
            try {
                asteroidsRepository.refreshAsteroids()
//                if (stringResponse.isNullOrEmpty()) throw Exception("Empty or null string response from API")
//
//                val jsonObject = JSONObject(stringResponse!!)
                _asteroids.value = asteroidsRepository.asteroids.value
                _asteroidListStatus.value = Status.DONE

            }
            catch (e: Exception){
                _asteroidListStatus.value = Status.ERROR
                Log.e(TAG, "getAsteroids: Failed ", e)
            }
            finally {

            }
        }
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            _pictureStatus.value = Status.LOADING
            try {
                _pictureOfDay.value = NasaApi.retrofitPictureService.getPicture()
                Log.d(TAG, "getPictureOfTheDay: " + _pictureOfDay.value)
                _pictureStatus.value = Status.DONE
            }
            catch (e: Exception){
                _pictureStatus.value = Status.ERROR
                Log.e(TAG, "getPictureOfTheDay: Failed ", e)
            }
        }
    }

    fun retryDataFetch(){
//        getAsteroids()
        getPictureOfTheDay()
    }

    fun navigateToAsteroidDetails(asteroid: Asteroid){
        _navigateToAsteroidDetail.value = Event(asteroid)
    }

    enum class Status{LOADING, ERROR, DONE}

    companion object{
        val TAG = MainViewModel::class.java.simpleName
    }

        class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}