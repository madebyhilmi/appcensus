package ca.ucalgary.appcensus.database

import android.app.Application
import androidx.room.Room


class AppMasterApplication: Application() {

    companion object {
        var database: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        AppMasterApplication.database = Room.databaseBuilder(this, AppDatabase::class.java,
            "master-db").build()
    }
}