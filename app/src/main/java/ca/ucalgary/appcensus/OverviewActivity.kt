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
import com.mikepenz.materialdrawer.DrawerBuilder





class OverviewActivity : AppCompatActivity() {

    companion object {
        lateinit var appClassification: HashMap<App, AppDB>
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

    }

    private fun classifyApps(){
        //Add Applications to Dictionary (prep for classification)
        appClassification = HashMap()
        for (app in apps){
            val information = appInformation.filter { it.package_name == app.packageName}
            if (information.isNotEmpty()){
                //Classify
                when {
                    information[0].geo!! or information[0].router_mac!! or information[0].hwid!! or
                            information[0].real_name!! or information[0].sim_id!! or information[0].aid!! or
                            information[0].wifi_mac!! or information[0].email!! or information[0].hwid!! -> app.classification = App.HIGH_RISK
                    information[0].phone!! or information[0].gsfid!! or information[0].aaid!! -> app.classification = App.LOW_RISK
                    else -> app.classification = App.NO_RISK
                }
                appClassification[app] = information[0]
            }
        }
    }

    private fun drawPieChart(){
        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)


        val pie = AnyChart.pie()

        var highRiskAppCount = 0
        var lowRiskAppCount = 0
        var healthyAppCount = 0
        apps.forEach { app ->
            when (app.classification){
                App.HIGH_RISK -> highRiskAppCount++
                App.LOW_RISK -> lowRiskAppCount++
                App.NO_RISK -> healthyAppCount++
            }
        }


        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("High Risk", highRiskAppCount))
        data.add(ValueDataEntry("Low Risk", lowRiskAppCount))
        data.add(ValueDataEntry("Healthy", healthyAppCount))


        pie.data(data)

        pie.title("Application Sketchiness (Currently using " + appInformation.size + " applications to protect you)")


        pie.legend()
            .position("center-bottom")
            .itemsLayout(LegendLayout.HORIZONTAL)
            .align(Align.CENTER)


        anyChartView.setChart(pie)


        pie.setOnClickListener(object : ListenersInterface.OnClickListener(arrayOf("x", "value")) {
            override fun onClick(event: Event) {
                val risk_val = event.data["x"]
                var risk = ""
                when (risk_val) {
                    "Healthy" -> risk = App.NO_RISK
                    "Low Risk" -> risk = App.LOW_RISK
                    "High Risk" -> risk = App.HIGH_RISK
                }
                val it = appClassification.keys.iterator()
                while(it.hasNext()){
                    val app = it.next()
                    if (app.classification != risk){
                        it.remove()
                    }
                }
                MainActivity.start(this@OverviewActivity, appClassification, risk)
            }
        })
    }

}
