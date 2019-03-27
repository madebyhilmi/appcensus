package ca.ucalgary.appcensus

import android.content.Intent
import android.content.pm.ApplicationInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ca.ucalgary.appcensus.database.AppMasterApplication
import ca.ucalgary.appcensus.database.App as AppDB
import com.opencsv.CSVReader
import java.io.InputStreamReader


class StartActivity : AppCompatActivity() {

    private var apps = ArrayList<App>()
    private lateinit var appInformation: List<AppDB>
    private lateinit var appMasterApplication: AppMasterApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //Load Database
        initializeDB()

        val rowCount = appMasterApplication.getCount()

        if (rowCount > 1){
            appInformation = appMasterApplication.apps
        }else{
            readSampleDataFileAndPutInDatabase()
        }

        //Load Installed Apps
        getInstalledPackages()

    }

    fun toMainActivity(view: View){
        OverviewActivity.start(this, appMasterApplication, apps, appInformation)

    }

    private fun getInstalledPackages(){
        val pm = packageManager
        //get a list of installed apps.
        val packages = pm.getInstalledApplications(0)
        val itAppInfo = packages.iterator()
        var appInfo = itAppInfo.next()
        while (itAppInfo.hasNext()){
            appInfo = itAppInfo.next()
            if (!isSystemPackage(appInfo)){
                val name = pm.getApplicationLabel(appInfo).toString()
                val packageName = appInfo.packageName
                val picture = pm.getApplicationIcon(appInfo)

                apps.add(App(name, packageName, picture))
            }

        }
        apps.add(App("Poker Arena", "air.ru.mail.games.pokerarena", pm.getApplicationIcon(appInfo)))
        apps.add(App("Taxi Simulator", "com.baklabs.taxi.simulator", pm.getApplicationIcon(appInfo)))
    }

    private fun isSystemPackage(appInfo: ApplicationInfo): Boolean {
        return appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun initializeDB(){
        appMasterApplication = AppMasterApplication(this)
    }

    private fun readSampleDataFileAndPutInDatabase() {
        try {
            val reader = CSVReader(InputStreamReader(assets.open("sample_data.csv")))

            val appInformation = ArrayList<ca.ucalgary.appcensus.database.App>()
            var nextLine: Array<String>?
            reader.readNext() //Header line
            nextLine = reader.readNext()
            while (nextLine != null){
                val app = ca.ucalgary.appcensus.database.App(
                    nextLine[0],
                    nextLine[1].toInt().asBoolean(),
                    nextLine[2].toInt().asBoolean(),
                    nextLine[3].toInt().asBoolean(),
                    nextLine[4].toInt().asBoolean(),
                    nextLine[5].toInt().asBoolean(),
                    nextLine[6].toInt().asBoolean(),
                    nextLine[7].toInt().asBoolean(),
                    nextLine[8].toInt().asBoolean(),
                    nextLine[9].toInt().asBoolean(),
                    nextLine[10].toInt().asBoolean(),
                    nextLine[11].toInt().asBoolean(),
                    nextLine[12].toInt().asBoolean(),
                    nextLine[13].toInt().asBoolean()
                )
                appInformation.add(app)
                appMasterApplication.insertApp(app)
                nextLine = reader.readNext()
            }
            this.appInformation = appInformation

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "The specified file was not found", Toast.LENGTH_SHORT).show()
        }

    }
    private fun loadAppInformation() {
        appInformation = appMasterApplication.apps
    }
}
