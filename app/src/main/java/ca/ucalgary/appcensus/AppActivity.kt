package ca.ucalgary.appcensus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_app.*

class AppActivity : AppCompatActivity() {
    private var selectedApp: App? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_app)

        selectedApp = intent.getSerializableExtra(APP_KEY) as App
        //TODO: Add logic here to parse load JSON and get appropriate App
        appDescription?.text = selectedApp?.description
        appName?.text = selectedApp?.name


    }

    companion object {
        private val APP_KEY = "APP"
    }
}