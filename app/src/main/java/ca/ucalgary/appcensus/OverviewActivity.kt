package ca.ucalgary.appcensus

import android.content.Context
import ca.ucalgary.appcensus.database.AppMasterApplication
import ca.ucalgary.appcensus.database.App as AppDB

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.chart.common.listener.Event
import com.anychart.chart.common.listener.ListenersInterface
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout



class OverviewActivity : AppCompatActivity() {

    companion object {
        lateinit var appMasterApplication: AppMasterApplication
        lateinit var apps: ArrayList<App>
        lateinit var appInformation: List<AppDB>

        fun start(context: Context, application: AppMasterApplication, iApps: ArrayList<App>, information: List<AppDB>){
            appMasterApplication = application
            apps = iApps
            appInformation = information
            val intent = Intent(context, OverviewActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        classifyApps()

        //Draw Pie Chart
        drawPieChart()
        print(apps.size)
        print(appInformation.size)

    }

    private fun classifyApps(){
        val appClassification = HashMap<App, AppDB>()
        for (app in apps){
            val information = appInformation.filter { it.package_name == app.packageName}
            if (information.isNotEmpty()){
                appClassification[app] = information[0]
            }
        }

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

        pie.title("Application Sketchiness (Currently using " + appInformation.size + " applications to protect you)")


        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)


        anyChartView.setChart(pie)
    }

}
