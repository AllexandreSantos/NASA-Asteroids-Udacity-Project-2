package com.allexandresantos.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.allexandresantos.asteroidradar.utils.Event
import com.allexandresantos.asteroidradar.database.getDatabase
import com.allexandresantos.asteroidradar.domainentities.Asteroid
import com.allexandresantos.asteroidradar.repository.AsteroidsRepository
import com.allexandresantos.asteroidradar.repository.PictureOfDayRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {

    private val database = getDatabase(application)

    private val asteroidsRepository = AsteroidsRepository(database)

    private val pictureOfDayRepository = PictureOfDayRepository(database)

    val _navigateToAsteroidDetail = MutableLiveData<Event<Asteroid>>()

    val navigateToAsteroidDetail: LiveData<Event<Asteroid>>
        get() = _navigateToAsteroidDetail


    init {
        getPictureOfTheDay()
        getAsteroids()
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

    companion object{
        val TAG: String = MainViewModel::class.java.simpleName
    }

}