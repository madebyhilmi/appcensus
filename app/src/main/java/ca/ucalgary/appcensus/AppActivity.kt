package ca.ucalgary.appcensus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app.*
import java.util.HashMap
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


    }

}