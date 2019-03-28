package ca.ucalgary.appcensus


import ca.ucalgary.appcensus.database.App as AppDB

import android.content.Intent
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_app.view.*
import kotlinx.android.synthetic.main.risk_item_row.view.*
import kotlin.reflect.full.memberProperties

class RiskAdapter(private val apps: List<App>, private val appWithInformation: HashMap<App, AppDB>) : androidx.recyclerview.widget.RecyclerView.Adapter<RiskAdapter.AppHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskAdapter.AppHolder {
        val inflatedView = parent.inflate(R.layout.risk_item_row, false)
        return AppHolder(inflatedView)
    }

    override fun getItemCount() = apps.size

    override fun onBindViewHolder(holder: AppHolder, position: Int) {
        val app = apps[position]
        val appInformation = appWithInformation[app]
        holder.bindApp(app, appInformation)
    }


    class AppHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var app : App? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = itemView.context
            val showAppIntent = Intent(context, AppActivity::class.java)
            showAppIntent.putExtra(APP_KEY, app)
            context.startActivity(showAppIntent)

        }
        companion object {
            private val APP_KEY = "APP"
            private val INFORMATION_TYPES = hashMapOf(
                "aaid" to "Android Advertising ID"
                ,"aid" to "Android ID",
                "geo" to "Location",
                "router_ssid" to "Router SSID",
                "wifi_mac" to "Wifi MAC",
                "router_mac" to "Router MAC",
                "hwid" to "Hardware ID",
                "phone" to "Phone",
                "email" to "Email",
                "real_name" to "Name",
                "package_dump" to "Installed Applications",
                "simid" to "SIM",
                "gsfid" to "Google Services Framework ID")
        }
        fun bindApp(app: App, information: AppDB?){
            this.app = app
            view.rowAppDescription.text = app.description
            view.rowAppName.text = app.name
            var counter = 0
            for (prop in AppDB::class.memberProperties){
                val propValue = prop.get(information!!).toString().toBoolean()
                when {
                    propValue and (counter == 0) -> {
                        view.rowCategoryOne.text = INFORMATION_TYPES[prop.name]
                        counter++

                    }
                    propValue and (counter == 1) -> {
                        view.rowCategoryTwo.text = INFORMATION_TYPES[prop.name]
                        counter++

                    }
                    propValue and (counter == 2) -> {
                        view.rowCategoryThree.text = INFORMATION_TYPES[prop.name]
                        counter++
                    }
                }
            }
        }
        private fun createResources(resources: java.util.ArrayList<String>){
            for (resource: String in resources){
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)

                val textView = TextView(this.view.context)
                textView.background = this.view.context.getDrawable(R.drawable.hr_rounded_corner)
                textView.setPadding(4,4,4,4)
                textView.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                textView.text = resource
                textView.setTextColor(Color.WHITE)
                val id_ = textView.id

                this.view.linearLayout.addView(textView)

//                val constraintSet = ConstraintSet()
//                constraintSet.clone(this.linearLayout)
//                constraintSet.connect(id_, ConstraintSet.BOTTOM, R.id.parent, ConstraintSet.BOTTOM, 8)
//                constraintSet.connect(id_, ConstraintSet.START, R.id.parent, ConstraintSet.START, 8)
            }
        }

    }

}