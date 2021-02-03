package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {

    @Query("select * from databaseasteroid")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroids(vararg asteroids: DatabaseAsteroid)

}

@Dao
interface PictureOfDayDao{
    @Query("select * from databasepictureofday")
    fun getPictureOfDay(): LiveData<DatabasePictureOfDay>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertPicture(vararg pictureOfDay: DatabasePictureOfDay)
}

@Database(entities = [DatabaseAsteroid::class, DatabasePictureOfDay::class], version =1)
abstract class AsteroidsPodDatabase: RoomDatabase(){
    abstract val asteroidDao: AsteroidDao
    abstract val pictureOfDayDao: PictureOfDayDao

}

private lateinit var INSTANCE: AsteroidsPodDatabase

fun getDatabase(context: Context): AsteroidsPodDatabase{
    synchronized(AsteroidsPodDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext, AsteroidsPodDatabase::class.java, "asteroidspod").build()
        }
    }
    return INSTANCE
}

