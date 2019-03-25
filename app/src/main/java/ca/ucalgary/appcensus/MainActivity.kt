package ca.ucalgary.appcensus

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.activity_app.view.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RiskAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var appsList: ArrayList<App> = ArrayList()
    private val lastVisibleItemPosition: Int
        get() = linearLayoutManager.findLastVisibleItemPosition()


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RiskAdapter(appsList)
        recyclerView.adapter = adapter
        setRecyclerViewScrollListener()
    }
    override fun onStart() {
        super.onStart()
        if (appsList.size == 0) {
            createSamplePhotos()
        }
    }
    private fun createSamplePhotos(){

        val appOne: App = App(JSONObject(getString(R.string.app_one_json)))
        appsList.add(appOne)
        val appTwo: App = App(JSONObject(getString(R.string.app_two_json)))
        appsList.add(appTwo)
        val appThree: App = App(JSONObject(getString(R.string.app_three_json)))
        appsList.add(appThree)

    }

    private fun receivedNewPhoto(newApp: App) {
        runOnUiThread {
            appsList.add(newApp)
            adapter.notifyItemInserted(appsList.size)

        }
    }

    private fun setRecyclerViewScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                createSamplePhotos()
            }
        })
    }

}
