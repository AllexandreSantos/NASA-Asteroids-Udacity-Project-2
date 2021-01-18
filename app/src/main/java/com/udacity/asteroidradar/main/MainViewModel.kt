package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.PictureOfDayApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {


    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _pictureStatus = MutableLiveData<PictureOfDayApiStatus>()

    val pictureStatus: LiveData<PictureOfDayApiStatus>
        get() = _pictureStatus

    private var stringResponse: String? = null

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids


    init {
        getPictureOfTheDay()
        getAsteroids()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                stringResponse = AsteroidApi.retrofitAsteroidService.getAsteroids()
                val jsonObject = JSONObject(stringResponse)
                _asteroids.value = parseAsteroidsJsonResult(jsonObject)
                Log.d(TAG, "getAsteroids: " + (_asteroids.value as ArrayList<Asteroid>)[0])
            }
            catch (e: Exception){
                Log.e(TAG, "getAsteroids: Failed ", e)
            }
        }
    }

    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            _pictureStatus.value = PictureOfDayApiStatus.LOADING
            try {
                _pictureOfDay.value = PictureOfDayApi.retrofitPictureService.getPicture()
                Log.d(TAG, "getPictureOfTheDay: " + _pictureOfDay.value)
                _pictureStatus.value = PictureOfDayApiStatus.DONE
            }
            catch (e: Exception){
                Log.e(TAG, "getPictureOfTheDay: Failed ", e)
                _pictureStatus.value = PictureOfDayApiStatus.ERROR
            }
        }
    }

    enum class PictureOfDayApiStatus{LOADING, ERROR, DONE}

    companion object{
        val TAG = MainViewModel::class.java.simpleName
    }
}