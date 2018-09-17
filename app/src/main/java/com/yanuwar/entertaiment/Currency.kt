package com.yanuwar.entertaiment

import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import kotlinx.android.synthetic.main.activity_currency.*
import org.json.JSONObject
import java.net.URL

class Currency : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var jsonObject: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency)

        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.show()

        getCurrency().execute()

        btn_convert.setOnClickListener {
            proccessing()
        }
    }

    private fun proccessing() {
        val dollar = jsonObject.getJSONObject("IDR_USD").getDouble("val")
        val jpy = jsonObject.getJSONObject("IDR_JPY").getDouble("val")
        val rp = et_rupiah.text.toString().toDouble()
        et_dollar.setText((rp*dollar).toString())
        et_yen.setText((rp*jpy).toString())
    }

    inner class getCurrency : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            return URL("http://free.currencyconverterapi.com/api/v5/convert?q=IDR_USD,IDR_JPY&compact=y").readText()
        }

        override fun onPostExecute(result: String?) {
            progressDialog.hide()
            super.onPostExecute(result)
            jsonObject = JSONObject(result)
        }
    }
}
