package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import kotlinx.android.synthetic.main.activity_calc.*

class Calc : AppCompatActivity() {
    var value: Double = 0.0
    var operator = 1
    var valueFirst: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calc)

        tv_plus.setOnClickListener {
            if (operator > 0) {
                when (operator) {
                    1 -> valueFirst += if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    2 -> valueFirst -= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    3 -> valueFirst *= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    4 -> valueFirst /= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                }
            }
            e("cihuy", valueFirst.toString())
            operator = 1
            et_value.setText("0")
        }

        tv_min.setOnClickListener {
            if (operator > 0) {
                when (operator) {
                    1 -> valueFirst += if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    2 -> valueFirst -= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    3 -> valueFirst *= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    4 -> valueFirst /= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                }
            }
            operator = 2
            et_value.setText("0")
        }

        tv_kali.setOnClickListener {
            if (operator > 0) {
                when (operator) {
                    1 -> valueFirst += if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    2 -> valueFirst -= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    3 -> valueFirst *= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    4 -> valueFirst /= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                }
            }
            operator = 3
            et_value.setText("0")
        }

        tv_bagi.setOnClickListener {
            if (operator > 0) {
                when (operator) {
                    1 -> valueFirst += if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    2 -> valueFirst -= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    3 -> valueFirst *= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                    4 -> valueFirst /= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                }
            }
            operator = 4
            et_value.setText("0")
        }

        tv_sama_dengan.setOnClickListener {
            when (operator) {
                1 -> valueFirst += if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                2 -> valueFirst -= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                3 -> valueFirst *= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
                4 -> valueFirst /= if (et_value.text.toString().isNotBlank()) et_value.text.toString().toDouble() else 0.0
            }
            et_value.setText(valueFirst.toString())
        }
    }
}
