package ca.ucalgary.appcensus

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.pm.ResolveInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.support.v4.app.FragmentActivity
import android.util.Log


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme_FullScreen)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun toMainActivity(view: View){


        val intent = Intent(this, OverviewActivity::class.java)
        startActivity(intent)
    }
}
