package ca.ucalgary.appcensus.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [App::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao



}