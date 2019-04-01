package ca.ucalgary.appcensus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app.*
import ca.ucalgary.appcensus.database.App as AppDB

class AppActivity : AppCompatActivity() {

    companion object {
        lateinit var selectedApp: App
        lateinit var appInformation: AppDB
        fun start(context: Context, app: App, information: AppDB?){
            selectedApp = app
            if (information != null){
                appInformation = information
            }else{
                print("No app information found")
            }
            val intent = Intent(context, AppActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)

        //TODO: Add logic here to parse load JSON and get appropriate App
        appDescription?.text = selectedApp.description
        appName?.text = selectedApp.name
        appImage.background = selectedApp.image

        imei_transmission.text = "IMEI"
        wifi_transmission.text = "WiFi Address"
        advertising_transmission.text = "Advertising ID"
        android_transmission.text = "Android ID"
        name_transmission.text = "Real Name"
        services_transmission.text = "Google Services ID"
        location_transmission.text = "Location"
        sim_transmission.text = "SIM Serial"

        if (appInformation != null){
            if (!appInformation.wifi_mac!!){
                imei_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.aid!!){
                advertising_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.aaid!!){
                android_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.gsfid!!){
                services_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.sim_id!!){
                sim_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.geo!!){
                location_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }else if (!appInformation.real_name!!){
                name_transmission.setBackgroundResource(R.drawable.g_rounded_corner)
            }
        }


    }

}