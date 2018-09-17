package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_terbilang.*

class Terbilang : AppCompatActivity() {

    var anka = arrayOf("", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terbilang)
        btn_processing.setOnClickListener {
            tv_terbilang.text = terbilang(et_input.text.toString().toInt())
        }
    }

    private fun terbilang(angka: Int): String {
        return when {
            angka < 12 -> anka[angka]

            angka in 12..19 -> anka[angka % 10] + " belas "

            angka in 20..99 -> anka[angka / 10] + " puluh " + anka[angka % 10]

            angka in 100..199 -> "seratus " + terbilang(angka % 100)

            angka in 200..999 -> anka[angka / 100] + " ratus " + terbilang(angka % 100)

            angka in 1000..1999 -> "seribu " + terbilang(angka % 1000)

            angka in 2000..999999 -> terbilang((angka / 1000)) + " ribu " + terbilang(angka % 1000)

            angka in 1000000..999999999 -> terbilang((angka / 1000000)) + " juta " + terbilang(angka % 1000000)

            else -> ""
        }

    }
}
