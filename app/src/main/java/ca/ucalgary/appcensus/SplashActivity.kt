package ca.ucalgary.appcensus

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Do Stuff

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
        finish()
    }
}
