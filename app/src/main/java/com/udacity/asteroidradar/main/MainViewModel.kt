package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Event
import com.udacity.asteroidradar.domainentities.DataTransferAsteroid
import com.udacity.asteroidradar.domainentities.PictureOfDay
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {

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

    private val _asteroids = MutableLiveData<List<DataTransferAsteroid>>()

    val asteroids: LiveData<List<DataTransferAsteroid>>
        get() = _asteroids

    val _navigateToAsteroidDetail = MutableLiveData<Event<DataTransferAsteroid>>()

    val navigateToAsteroidDetail: LiveData<Event<DataTransferAsteroid>>
        get() = _navigateToAsteroidDetail


    init {
        getPictureOfTheDay()
        getAsteroids()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
//            _asteroidListStatus.value = Status.LOADING
//            try {
//                stringResponse = NasaApi.retrofitAsteroidService.getAsteroidsAsync()
//
//                if (stringResponse.isNullOrEmpty()) throw Exception("Empty or null string response from API")
//
//                val jsonObject = JSONObject(stringResponse!!)
//                _asteroids.value = parseAsteroidsJsonResult(jsonObject)
//                _asteroidListStatus.value = Status.DONE
//            }
//            catch (e: Exception){
//                _asteroidListStatus.value = Status.ERROR
//                Log.e(TAG, "getAsteroids: Failed ", e)
//            }
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
        getAsteroids()
        getPictureOfTheDay()
    }

    fun navigateToAsteroidDetails(dataTransferAsteroid: DataTransferAsteroid){
        _navigateToAsteroidDetail.value = Event(dataTransferAsteroid)
    }

    enum class Status{LOADING, ERROR, DONE}

    companion object{
        val TAG = MainViewModel::class.java.simpleName
    }
}