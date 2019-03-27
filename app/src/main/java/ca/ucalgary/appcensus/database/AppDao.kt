package ca.ucalgary.appcensus.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.ArrayList


@Dao
interface AppDao {
    @Query("SELECT * FROM app_list")
    fun getAll(): List<App>


    @Query("SELECT * FROM app_list WHERE package_name IN (:appName)")
    fun loadAllByIds(appName: List<String>): List<App>


    @Insert
    fun insertAll(vararg apps: App)

    @Delete
    fun delete(app: App)

    @Query("SELECT * FROM app_list WHERE package_name = :appName")
    fun getApp(appName: String): App

    @Query("SELECT COUNT(*) FROM app_list")
    fun getCount(): Int
}