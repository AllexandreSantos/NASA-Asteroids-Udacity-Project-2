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
import org.json.JSONObject
import java.lang.Exception


//Tentar comunicar com a API e obter uma resposta. A resposta vem em formato String. Tentar transformar a resposta String em um
//objeto JSONObject. Transformando em objeto, utilizar a funcão pronta pra fazer o parse do objeto Json em uma lista de asteroides
//Adicionar essa lsita em uma livedata aqui no viewmodel.

class MainViewModel : ViewModel() {


    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _stringResponse = MutableLiveData<String>()

    val stringResponse: LiveData<String>
        get() = _stringResponse

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
                _stringResponse.value = AsteroidApi.retrofitAsteroidService.getAsteroids()
                Log.d(TAG, "getAsteroids: " + _stringResponse.value)

//                parseAsteroidResponse(_stringResponse.value!!)
                val jsonObject = JSONObject(_stringResponse.value)

                val list = parseAsteroidsJsonResult(jsonObject)
                Log.d(TAG, "getAsteroids: " + list[0])
            }
            catch (e: Exception){
                Log.e(TAG, "getAsteroids: Failed ", e)
            }
        }
    }

//    private suspend fun parseAsteroidResponse(stringResponse: String): JSONObject{
//
//        try {
//            _asteroids.value = parseAsteroidsJsonResult(stringResponse)
//        }catch (){
//
//        }
//        finally {
//            return
//        }
//    }


    private fun getPictureOfTheDay() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = PictureOfDayApi.retrofitPictureService.getPicture()
                Log.d(TAG, "getPictureOfTheDay: " + _pictureOfDay.value)
            }
            catch (e: Exception){
                Log.e(TAG, "getPictureOfTheDay: Failed ", e)
            }
        }
    }

    companion object{
        val TAG = MainViewModel::class.java.simpleName
    }
}