package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tukar_uang.*

class TukarUang : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukar_uang)

        var arrPecahan = arrayOfNulls<Int>(10)
        arrPecahan = arrayOf(1000, 10000, 20000, 50000, 100000)

        btn_tukarkan.setOnClickListener {
            if (et_pecahan.text.toString().isNotBlank()){
                arrPecahan.map {
                    tukar(it?:0, et_pecahan.text.toString().toInt())
                }
            }
        }
    }

    private fun tukar(pecahan: Int ,value: Int){
        val jumlah = value/pecahan
        val sisa = value%pecahan
        tv_result.append("Tukar $pecahan: $jumlah Sisa: $sisa\n")
    }
}
