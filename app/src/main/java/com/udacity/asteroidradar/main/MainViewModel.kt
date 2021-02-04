package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.utils.Event
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domainentities.Asteroid
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.repository.PictureOfDayRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)

    private val asteroidsRepository = AsteroidsRepository(database)

    private val pictureOfDayRepository = PictureOfDayRepository(database)

    private val _pictureStatus = MutableLiveData<Status>()

    val pictureStatus: LiveData<Status>
        get() = _pictureStatus

    private val _asteroidListStatus = MutableLiveData<Status>()

    val asteroidListStatus: LiveData<Status>
        get() = _asteroidListStatus

    val _navigateToAsteroidDetail = MutableLiveData<Event<Asteroid>>()

    val navigateToAsteroidDetail: LiveData<Event<Asteroid>>
        get() = _navigateToAsteroidDetail


    init {
        getPictureOfTheDay()
        getAsteroids()
//        viewModelScope.launch {
//            test()
//        }
    }

    val asteroids = asteroidsRepository.asteroids

    val pictureOfDay = pictureOfDayRepository.pictureOfDay


    private fun getAsteroids() {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    private fun getPictureOfTheDay(){
       viewModelScope.launch {
           pictureOfDayRepository.refreshPictureOfDay()
       }
    }

    fun navigateToAsteroidDetails(asteroid: Asteroid){
        _navigateToAsteroidDetail.value = Event(asteroid)
    }

//    fun retryDataFetch(){
//        getAsteroids()
//        getPictureOfTheDay()
//    }

    suspend fun test(){
        delay(2000)
        if (asteroids.value?.isEmpty() == true){
            Log.d("oi", "test: vazio")
        }
    }

    enum class Status{LOADING, ERROR, DONE}
}