package com.yanuwar.entertaiment

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_jadwal_sholat.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class JadwalSholat : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var jsonObjectMaps: JSONObject
    lateinit var jsonObjectTime: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal_sholat)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        btn_show.setOnClickListener {
            progressDialog.show()
            if (et_wilayah.text.toString().isNotBlank()) getLatLong().execute(et_wilayah.text.toString()) else progressDialog.hide()
        }
    }

    inner class getLatLong : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            return URL("https://maps.googleapis.com/maps/api/geocode/json?address="+params[0]).readText()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            jsonObjectMaps = JSONObject(result)
            e("cihuy", result)
//            try {
                val latitude = jsonObjectMaps.getJSONArray("results").getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location").getLong("lat").toString()
                val longitude = jsonObjectMaps.getJSONArray("results").getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location").getLong("lng").toString()
                getTime().execute(latitude, longitude)
//            } catch (err: JSONException) {
//                Toast.makeText(baseContext, "Something wrong", Toast.LENGTH_SHORT).show()
//                progressDialog.hide()
//            }
        }
    }

    inner class getTime : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            return URL("http://api.aladhan.com/timings?latitude="+params[0]+"&longitude="+params[1]+"&method=1").readText()
        }

        override fun onPostExecute(result: String?) {
            progressDialog.hide()
            super.onPostExecute(result)
            jsonObjectTime = JSONObject(result)
            e("cihuy", result)
//            try {
                et_magrib.text = "Maghrib: " + jsonObjectTime.getJSONObject("data").getJSONObject("timings").getString("Maghrib")
                et_isya.text = "Isha: " + jsonObjectTime.getJSONObject("data").getJSONObject("timings").getString("Isha")
                et_subuh.text = "Fajr: " + jsonObjectTime.getJSONObject("data").getJSONObject("timings").getString("Fajr")
                et_dhuhur.text = "Dhuhr: " + jsonObjectTime.getJSONObject("data").getJSONObject("timings").getString("Dhuhr")
                et_ashar.text = "Asr: " + jsonObjectTime.getJSONObject("data").getJSONObject("timings").getString("Asr")
//            } catch (err: JSONException) {
//                Toast.makeText(baseContext, "Something wrong", Toast.LENGTH_SHORT).show()
//                progressDialog.hide()
//            }
        }
    }
}
