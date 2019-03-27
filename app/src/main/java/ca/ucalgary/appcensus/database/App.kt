package ca.ucalgary.appcensus.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_list")
data class App(
    @PrimaryKey var package_name: String,
    @ColumnInfo(name = "aaid") var aaid: Boolean?,
    @ColumnInfo(name = "aid") var aid: Boolean?,
    @ColumnInfo(name = "geo") var geo: Boolean?,
    @ColumnInfo(name = "router_ssid") var router_ssid: Boolean?,
    @ColumnInfo(name = "wifi_mac") var wifi_mac: Boolean?,
    @ColumnInfo(name = "router_macc") var router_mac: Boolean?,
    @ColumnInfo(name = "hwid") var hwid: Boolean?,
    @ColumnInfo(name = "phone") var phone: Boolean?,
    @ColumnInfo(name = "email") var email: Boolean?,
    @ColumnInfo(name = "real_name") var real_name: Boolean?,
    @ColumnInfo(name = "package_dump") var package_dump: Boolean?,
    @ColumnInfo(name = "sim_id") var sim_id: Boolean?,
    @ColumnInfo(name = "gsfid") var gsfid: Boolean?
)
