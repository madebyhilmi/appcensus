package ca.ucalgary.appcensus

import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class App(appJSON: JSONObject) : Serializable {
    lateinit var  name: String
        private set
    lateinit var url: String
        private set
    lateinit var description: String
        private set

    init {
        try{
            name        = appJSON.getString(APP_NAME)
            url         = appJSON.getString(APP_URL)
            description = appJSON.getString(APP_DESCRIPTION)
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }


    companion object {
        private val APP_NAME = "name"
        private val APP_DESCRIPTION = "description"
        private val APP_URL = "url"
    }
}