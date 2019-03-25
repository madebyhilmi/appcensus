package ca.ucalgary.appcensus

import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_app.view.*
import kotlinx.android.synthetic.main.risk_item_row.view.*

class RiskAdapter(private val apps: ArrayList<App>) : RecyclerView.Adapter<RiskAdapter.AppHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiskAdapter.AppHolder {
        val inflatedView = parent.inflate(R.layout.risk_item_row, false)
        return AppHolder(inflatedView)
    }

    override fun getItemCount() = apps.size

    override fun onBindViewHolder(holder: AppHolder, position: Int) {
        val itemApp = apps[position]
        holder.bindApp(itemApp)
    }


    class AppHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
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
        }
        fun bindApp(app: App){
            this.app = app
            view.rowAppDescription.text = app.description
            view.rowAppName.text = app.name
            view.rowCategoryOne.text = "Test Category One"
            view.rowCategoryTwo.text = "Test Category Two"
            view.rowCategoryThree.text = "Test Category Three"
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