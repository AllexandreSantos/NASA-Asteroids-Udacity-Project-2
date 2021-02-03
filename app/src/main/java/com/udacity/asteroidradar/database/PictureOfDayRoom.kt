package com.udacity.asteroidradar.database
//
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import kotlinx.coroutines.selects.select
//
//@Dao
//interface PictureOfDayDao{
//   @Query("select * from databasepictureofday")
//   fun getPictureOfDay(): LiveData<DatabasePictureOfDay>
//
//   @Insert(onConflict =  OnConflictStrategy.REPLACE)
//   fun insertAllPictures(vararg pictureOfDay: DatabasePictureOfDay)
//}
//
//@Database(entities = [DatabasePictureOfDay::class], version = 1)
//abstract class PictureOfDayDatabase: RoomDatabase(){
//    abstract val pictureOfDayDao: PictureOfDayDao
//}
//
//private lateinit var PODINSTANCE: PictureOfDayDatabase
//
//fun getPictureDatabase(context: Context): PictureOfDayDatabase{
//    synchronized(PictureOfDayDatabase::class.java){
//        if (!::PODINSTANCE.isInitialized){
//            PODINSTANCE = Room.databaseBuilder(context.applicationContext, PictureOfDayDatabase::class.java, "asteroids").build()
//        }
//    }
//    return PODINSTANCE
//}