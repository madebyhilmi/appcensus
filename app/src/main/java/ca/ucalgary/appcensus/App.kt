package ca.ucalgary.appcensus

import android.graphics.drawable.Drawable
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class App() : Serializable {
    lateinit var  name: String
        private set
    lateinit var url: String
        private set
    lateinit var description: String
        private set
    lateinit var image: Drawable
        private set
    lateinit var packageName: String
        private set

    constructor(appJSON: JSONObject): this() {
        try{
            name        = appJSON.getString(APP_NAME)
            url         = appJSON.getString(APP_URL)
            description = appJSON.getString(APP_DESCRIPTION)
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }

    constructor(name: String, packageName: String, image: Drawable): this() {
        this.name = name
        this.packageName = packageName
        this.image = image

        this.description = "This is a test description"
        this.url = "fakeurl.com"
    }


    companion object {
        private val APP_NAME = "name"
        private val APP_DESCRIPTION = "description"
        private val APP_URL = "url"
    }


}