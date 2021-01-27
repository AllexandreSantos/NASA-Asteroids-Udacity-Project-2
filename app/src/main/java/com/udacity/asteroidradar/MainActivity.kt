package com.udacity.asteroidradar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.udacity.asteroidradar.database.AsteroidRoom
import com.udacity.asteroidradar.database.DatabaseAsteroid
import com.udacity.asteroidradar.database.test

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val y = test(this)
//        Log.d("OI", "onCreate: " + y.toString())

//        val z = AsteroidRoom.test(this)
//        Log.d("OI", "onCreate: " + z.toString())

    }
}
