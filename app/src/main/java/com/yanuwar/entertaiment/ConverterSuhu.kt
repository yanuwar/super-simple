package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import kotlinx.android.synthetic.main.activity_converter_suhu.*

class ConverterSuhu : AppCompatActivity() {

    private var valueFirst = 0.0
    private var valueSecond = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter_suhu)

        btn_convert.setOnClickListener {
            justConvertIt()
        }
    }

    private fun justConvertIt() {
        if (rb_1.isChecked) {
            valueFirst = et_first.text.toString().toDouble()
        }
        if (rb_2.isChecked) {
            valueFirst = convertKelvinTonCelcius(et_first.text.toString().toDouble())
        }
        if (rb_3.isChecked) {
            valueFirst = convertFahrenheitToCelcius(et_first.text.toString().toDouble())
        }
        if (rb_4.isChecked) {
            valueFirst = convertReaumurToCelcius(et_first.text.toString().toDouble())
        }

        if (rb_5.isChecked) {
            valueSecond = valueFirst
        }
        if (rb_6.isChecked) {
            valueSecond = convertCelciusToKelvin(valueFirst)
        }
        if (rb_7.isChecked) {
            valueSecond = convertCelciusToFahrenheit(valueFirst)
        }
        if (rb_8.isChecked) {
            valueSecond = convertCelciusToReaumur(valueFirst)
        }

        et_second.setText(valueSecond.toString())
    }

    private fun convertCelciusToKelvin(value: Double): Double = (value + 273)

    private fun convertCelciusToFahrenheit(value: Double): Double = ((9 * value)/5)

    private fun convertCelciusToReaumur(value: Double): Double = ((4 * value)/5)

    private fun convertReaumurToCelcius(value: Double): Double = (5 * value)/4

    private fun convertFahrenheitToCelcius(value: Double): Double = ((5 * (value - 32))/9)

    private fun convertKelvinTonCelcius(value: Double): Double = (value - 273)
}
