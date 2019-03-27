package ca.ucalgary.appcensus

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ca.ucalgary.appcensus.database.AppDatabase
import ca.ucalgary.appcensus.database.AppMasterApplication
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout


class OverviewActivity : AppCompatActivity() {

    var apps: ArrayList<App> = ArrayList()
    private lateinit var appDatabase: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)

        //Load Database
        loadDB()


        //Load Installed Apps
        getInstalledPackages()


        //Draw Pie Chart
        drawPieChart()
    }

    private fun drawPieChart(){
        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)


        val pie = AnyChart.pie()

        pie.setOnClickListener(object : ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                val intent = Intent(this@OverviewActivity, MainActivity::class.java)
                startActivity(intent)
            }
        })

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("High Risk", apps.size))
        data.add(ValueDataEntry("Low Risk", apps.size))
        data.add(ValueDataEntry("Healthy", apps.size))


        pie.data(data)

        pie.title("Application Sketchiness")


        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)


        anyChartView.setChart(pie)
    }

    private fun getInstalledPackages(){
        val pm = packageManager
        //get a list of installed apps.
        val packages = pm.getInstalledApplications(0)
        val itAppInfo = packages.iterator()
        while (itAppInfo.hasNext()){
            val appInfo = itAppInfo.next()
            if (!isSystemPackage(appInfo)){
                val name = pm.getApplicationLabel(appInfo).toString()
                val packageName = appInfo.packageName
                val picture = pm.getApplicationIcon(appInfo)

                apps.add(App(name, packageName, picture))
            }

        }
    }

    private fun isSystemPackage(appInfo: ApplicationInfo): Boolean {
        return appInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    private fun loadDB(){
        appDatabase = AppMasterApplication.database!!


    }
}
