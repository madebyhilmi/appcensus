package ca.ucalgary.appcensus


import ca.ucalgary.appcensus.database.App as AppDB
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.*



class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RiskAdapter
    private lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private var appsList: ArrayList<App> = ArrayList()
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()

    companion object {
        lateinit var appClassification: HashMap<App, ca.ucalgary.appcensus.database.App>
        lateinit var riskClicked: String

        fun start(context: Context, classification: HashMap<App, AppDB>, risk: String){
            appClassification = classification
            riskClicked = risk
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitleTextColor(Color.WHITE)
        when (riskClicked) {
            App.HIGH_RISK -> {
                toolbar.title = "High Risk Applications"
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorHigh))
            }
            App.LOW_RISK -> {
                toolbar.title = "Low Risk Applications"
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorLow))
            }
            App.NO_RISK -> {
                toolbar.title = "Healthy Applications"
                toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RiskAdapter(appsList, appClassification)
        recyclerView.adapter = adapter
        setRecyclerViewScrollListener()

    }
    override fun onStart() {
        super.onStart()
        if (appsList.size == 0) {
            //createSamplePhotos()
            addApplications()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.itemId.equals(android.R.id.home)) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
    private fun createSamplePhotos(){
        val appOne: App = App(JSONObject(getString(R.string.app_one_json)))
        appsList.add(appOne)
        val appTwo: App = App(JSONObject(getString(R.string.app_two_json)))
        appsList.add(appTwo)
        val appThree: App = App(JSONObject(getString(R.string.app_three_json)))
        appsList.add(appThree)

    }

    private fun addApplications(){
        for (app in appClassification){
            appsList.add(app.key)
        }
    }

    private fun receivedNewPhoto(newApp: App) {
        runOnUiThread {
            appsList.add(newApp)
            adapter.notifyItemInserted(appsList.size)

        }
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: androidx.recyclerview.widget.RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //createSamplePhotos()
                addApplications()

            }
        })
    }

}
