package com.yanuwar.entertaiment

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.util.Log.e
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_weather.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener



class WeatherActivity : AppCompatActivity() {

    private lateinit var jsonResponse: JSONObject
    private val listWeather = arrayListOf<WeatherItem>()
    private val listAllWeather = arrayListOf<MutableList<WeatherItem>>()
    private lateinit var adapter: WeatherAdapter
    private lateinit var adapter2: WeatherAdapter
    private lateinit var adapter3: WeatherAdapter
    private lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        progress = ProgressDialog(this)
        progress.setMessage("Tunggu Sebentar...")
        progress.show()

        rv_list_weather.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_list_weather.itemAnimator = DefaultItemAnimator()

        rv_list_weather2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_list_weather2.itemAnimator = DefaultItemAnimator()

        rv_list_weather3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_list_weather3.itemAnimator = DefaultItemAnimator()

        getResponse().execute("id=1621177")

        tv_title.setOnEditorActionListener { v, actionId, event ->
                    var handled = false
                    if (actionId == EditorInfo.IME_ACTION_GO) {
                        progress.show()
                        getResponse().execute("q="+tv_title.text.toString())
                        e("cihuy", tv_title.text.toString())
                        handled = true
                    }
                    handled
                }
    }

    inner class getResponse : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String {
            return URL("https://api.openweathermap.org/data/2.5/forecast?${params[0]}&appid=ba7f4e5b90befbb701a523379ad94d10&units=metric").readText()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progress.dismiss()
            try {
                listWeather.clear()
                jsonResponse = JSONObject(result)
                val name = jsonResponse.getJSONObject("city").getString("name")
                val countryCode = jsonResponse.getJSONObject("city").getString("country")
                val long = jsonResponse.getJSONObject("city").getJSONObject("coord").getString("lon")
                val lat = jsonResponse.getJSONObject("city").getJSONObject("coord").getString("lat")

                val list = jsonResponse.getJSONArray("list")
                if (list.length() > 0) {
                    var i = 0
                    while (i < list.length()) {
                        val weather = list.getJSONObject(i).getJSONArray("weather")
                        val id = weather.getJSONObject(0).getString("id")
                        val main = weather.getJSONObject(0).getString("main")
                        val description = weather.getJSONObject(0).getString("description")
                        val icon = weather.getJSONObject(0).getString("icon")

                        val main_item = list.getJSONObject(i).getJSONObject("main")
                        val temp = main_item.getDouble("temp")
                        val temp_min = main_item.getDouble("temp_min")
                        val temp_max = main_item.getDouble("temp_max")
                        val pressure = main_item.getDouble("pressure")
                        val sea_level = main_item.getDouble("sea_level")
                        val grnd_level = main_item.getDouble("grnd_level")
                        val humidity = main_item.getDouble("humidity")

                        val wind = list.getJSONObject(i).getJSONObject("wind")
                        val speed = wind.getDouble("speed")
                        val deg = wind.getDouble("deg")

                        val rain = list.getJSONObject(i).getJSONObject("rain")
//                        val rain_all = rain.getDouble("3h")
//
                        val dt_txt = list.getJSONObject(i).getString("dt_txt")
                        val dt = list.getJSONObject(i).getLong("dt")

                        listWeather.add(WeatherItem(
                                temp,
                                temp_min,
                                temp_max,
                                pressure,
                                sea_level,
                                grnd_level,
                                humidity,
                                main,
                                description,
                                icon,
                                speed,
                                deg,
                                0.0,
                                dt_txt,
                                dt
                        ))
                        i++
                    }

                    mappingList()
                }
            } catch (err: JSONException) {
                e("cihuy", "error: "+err.message)
                Toast.makeText(baseContext, "Something wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun mappingList(){
        listAllWeather.clear()
        var i = 0
        var tempDate = Date()
        var tempString = tempDate.toSimpleDateString()
        listAllWeather.add(arrayListOf())
        listWeather.forEachIndexed { index, weatherItem ->
            val cal = Calendar.getInstance()
            cal.time = Date(weatherItem.dt*1000)
            tempDate = cal.time
            if (tempString == Date(weatherItem.dt*1000).toSimpleDateString()) {
                val tempTime = Date(weatherItem.dt*1000).toSimpleTimeWithoutSecond()
                if (tempTime == "07:00" || tempTime == "16:00" || tempTime == "10:00" || tempTime == "22:00" || tempTime == "19:00") listAllWeather[i].add(weatherItem)
            } else {
                i++
                listAllWeather.add(arrayListOf())
                tempString = Date(weatherItem.dt*1000).toSimpleDateString()
                val tempTime = Date(weatherItem.dt*1000).toSimpleTimeWithoutSecond()
                if (tempTime == "07:00" || tempTime == "16:00" || tempTime == "10:00" || tempTime == "22:00" || tempTime == "19:00") listAllWeather[i].add(weatherItem)
            }
        }

        tv_title_date.text = listAllWeather[0][0].dt_txt.fullDateTimeToDate().toSimpleDateString()
        tv_title_date2.text = listAllWeather[1][0].dt_txt.fullDateTimeToDate().toSimpleDateString()
        tv_title_date3.text = listAllWeather[2][0].dt_txt.fullDateTimeToDate().toSimpleDateString()

        adapter = WeatherAdapter(listAllWeather[0])
        rv_list_weather.adapter = adapter

        adapter2 = WeatherAdapter(listAllWeather[1])
        rv_list_weather2.adapter = adapter2

        adapter3 = WeatherAdapter(listAllWeather[2])
        rv_list_weather3.adapter = adapter3
    }

    private fun String.fullDateTimeToDate(): Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", resources.configuration.locale).parse(this)

    private fun Date.toSimpleDateString(): String = SimpleDateFormat("dd MMMM yyyy", resources.configuration.locale).format(this)

    private fun Date.toSimpleTimeWithoutSecond(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
}
