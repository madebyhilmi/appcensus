package ca.ucalgary.appcensus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View



class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun toMainActivity(view: View){


        val intent = Intent(this, OverviewActivity::class.java)
        startActivity(intent)
    }
}
