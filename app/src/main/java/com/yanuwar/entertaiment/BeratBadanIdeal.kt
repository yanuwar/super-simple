package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import kotlinx.android.synthetic.main.activity_berat_badan_ideal.*

class BeratBadanIdeal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berat_badan_ideal)

        btn_processing.setOnClickListener {
            val berat = et_berat.text.toString().toDouble()
            tv_value.text = if (rb_laki.isChecked) getBeratPria(berat).toString()+" Kg" else if (rb_cewek.isChecked) getBeratWanita(berat).toString()+" Kg" else ""
        }
    }

    private fun getBeratPria(value: Double): Double {
        return (value - 100) - (((value - 100) * 10)/100)
    }

    private fun getBeratWanita(value: Double): Double {
        return (value - 100) - (((value - 100) * 15)/100)
    }
}
