package ca.ucalgary.appcensus.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room


class AppMasterApplication(context: Context) {
    private val DB_NAME = "master-db"
    private var database: AppDatabase

    init {
        database = Room.databaseBuilder(context, AppDatabase::class.java,
            DB_NAME).allowMainThreadQueries().build()
    }





    val apps: List<App>
        get() = database.appDao().getAll()



    fun insertApp(app: App) {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                if (getCount() < 1287){
                    database.appDao().insertAll(app)
                }

                return null
            }
        }.execute()
    }


    fun getApp(package_name: String): App? {
        return database.appDao().getApp(package_name)
    }

    fun getCount(): Int{
        return database.appDao().getCount()
    }
}