package ca.ucalgary.appcensus

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    override fun onBackPressed() {
        finish()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)
        toolbar_app.title = selectedApp.name
        toolbar_app.setTitleTextColor(Color.WHITE)
        val colorValue = ContextCompat.getColor(this, R.color.colorHigh)
        toolbar_app.setBackgroundColor(colorValue)
        setSupportActionBar(toolbar_app)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)



        //TODO: Add logic here to parse load JSON and get appropriate App
        appDescription?.text = selectedApp.description
        appName?.text = selectedApp.name
        appImage.background = selectedApp.image

        label_1.text = "Real Name"
        label_2.text = "WiFi Address"
        label_3.text = "Location"
        label_5.text = "IMEI"
        label_8.text = "Advertising ID"
        label_7.text = "Google Services ID"
        label_6.text = "Android ID"
        label_4.text = "SIM Serial"

        if (appInformation != null){
            if (!appInformation.wifi_mac!!) {
                label_2.setBackgroundResource(R.drawable.g_rounded_corner)
            }
             if (!appInformation.aid!!){
                label_6.setBackgroundResource(R.drawable.g_rounded_corner)
            }
            if (!appInformation.aaid!!){
                label_8.setBackgroundResource(R.drawable.g_rounded_corner)
            }
            if (!appInformation.gsfid!!){
                label_7.setBackgroundResource(R.drawable.g_rounded_corner)
            }
            if (!appInformation.sim_id!!){
                label_4.setBackgroundResource(R.drawable.g_rounded_corner)
            }
            if (!appInformation.geo!!){
                label_3.setBackgroundResource(R.drawable.g_rounded_corner)
            }
            if (!appInformation.real_name!!){
                label_1.setBackgroundResource(R.drawable.g_rounded_corner)
            }
        }


    }

    fun onClick_1(v: View) {
        Toast.makeText(this, "This is the name of the phone's owner.", Toast.LENGTH_SHORT).show()
    }
    fun onClick_2(v: View) {
        Toast.makeText(this, "The Wi-Fi MAC address is a fixed serial number that is used to identify your " +
                "phone when transmitting and receiving data using Wi-Fi. It is also a globally unique identifier that " +
                "could be used to track you over time and across apps. It cannot be reset.", Toast.LENGTH_LONG).show()
    }
    fun onClick_3(v: View) {
        Toast.makeText(this, "This is the current location of the phone, down to at least street level.", Toast.LENGTH_SHORT).show()
    }
    fun onClick_4(v: View) {
        Toast.makeText(this, "This is a fixed serial number that is reported by your phone's SIM card, " +
                "which is used to route calls to your phone. It is also a globally unique identifier that could be " +
                "used to track you over time and across apps. It can only be reset by replacing your SIM card.",
            Toast.LENGTH_LONG).show()
    }
    fun onClick_5(v: View) {
        Toast.makeText(this, "The International Mobile Equipment Identity (IMEI) is a fixed serial number " +
                "that is used to route calls to your phone. It is also a globally unique identifier that could be used " +
                "to track you over time and across apps. It cannot be reset.", Toast.LENGTH_LONG).show()
    }
    fun onClick_6(v: View) {
        Toast.makeText(this, "The Android ID is a random serial number that is created when you first " +
                "configure your phone. It is a globally unique identifier that could be used to track you over time and " +
                "across apps, and can only be reset by performing a factory reset of your phone.",
            Toast.LENGTH_LONG).show()
    }
    fun onClick_7(v: View) {
        Toast.makeText(this, "The Google Services Framework (GSF) ID is a number that uniquely identifiers " +
                "your Google account. It is a globally unique identifier that could be used to track you over time and " +
                "across apps and devices, and can only be reset by deleting your Google account.",
            Toast.LENGTH_LONG).show()
    }
    fun onClick_8(v: View) {
        Toast.makeText(this, "The Android Advertising ID (AAID) is used for tracking and behavioral advertising. You can modify the settings of your phone to reset this identifier, to prevent tracking over time, or opt-out of behavioral advertising altogether.", Toast.LENGTH_LONG).show()
    }

}