package com.yanuwar.entertaiment

import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_weather.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.BitmapFactory
import android.util.Log.e
import android.widget.ImageView
import java.net.URL


class WeatherAdapter(val list: MutableList<WeatherItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather, parent, false)
        return WeatherAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        LoadImageFromUrl(holder.itemView.iv_cloud_icon).execute(list[position].icon+".png")
        val calendar = Calendar.getInstance()
        calendar.time = Date(list[position].dt*1000)
        calendar.add(Calendar.HOUR, 7)
        holder.itemView.tv_time.text = Date(list[position].dt*1000).toSimpleTimeWithoutSecond()
        holder.itemView.tv_nama_awan.text = list[position].description
        holder.itemView.tv_temp.text = list[position].temp.toString() +" "+ 0x00B0.toChar() + "C"
        holder.itemView.tv_kecepatan_angin.text = list[position].wind_speed.toString() + " m/s"
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

    private fun String.fullDateTimeToDate(): Date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)

    private fun Date.toSimpleDateString(): String = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)

    private fun Date.toSimpleTimeWithoutSecond(): String = SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)

    inner class LoadImageFromUrl(private val imageView: ImageView) : AsyncTask<String, String, Bitmap>() {
        override fun doInBackground(vararg params: String?): Bitmap {
            val url = URL("http://openweathermap.org/img/w/"+params[0])
            return BitmapFactory.decodeStream(url.openConnection().getInputStream())
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            imageView.setImageBitmap(result)
        }
    }
}