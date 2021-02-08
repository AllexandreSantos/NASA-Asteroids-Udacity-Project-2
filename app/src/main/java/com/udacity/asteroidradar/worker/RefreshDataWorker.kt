package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import com.udacity.asteroidradar.repository.PictureOfDayRepository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)

        val asteroidsRepository = AsteroidsRepository(database)
        val pictureOfDayRepository = PictureOfDayRepository(database)

        return try {
            asteroidsRepository.refreshAsteroids()
            pictureOfDayRepository.refreshPictureOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

}