package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tebak_angka.*
import java.util.*

class TebakAngka : AppCompatActivity() {

    val random = Random()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tebak_angka)

        var kesempatan = 0
        val history = arrayListOf<Int>()
        var maxNumber: Double
        var random = 0

        btn_submit.setOnClickListener {
            maxNumber = et_panjang.text.toString().toDouble()
            random = getRandom(maxNumber.toInt())
            val temp = Math.log(maxNumber)/Math.log(2.toDouble())
            kesempatan = temp.toInt()+1

            tv_keterangan.text = "kesempatan: "+(kesempatan).toString()
            et_input_anka.visibility = View.VISIBLE
            btn_submit2.visibility = View.VISIBLE
            btn_submit.isEnabled = false
        }

        btn_submit2.setOnClickListener {
            if (kesempatan > 0) {
                kesempatan -= 1
                tv_keterangan.text = "kesempatan: "+(kesempatan).toString()
                val inputan = et_input_anka.text.toString().toInt()
                history.add(inputan)

                if (inputan != random) {
                    if (inputan > random) {
                        tv_keterangan2.text = "Tebakan angka anda terlalu besar"
                    } else {
                        tv_keterangan2.text = "Tebakan angka anda terlalu kecil"
                    }
                } else {
                    btn_submit2.isEnabled = false
                    tv_keterangan2.text = "anda menang"
                }
            } else {
                tv_keterangan2.text = "kesempatan habis anda kalah"
            }
        }
    }

    private fun getRandom(length: Int) : Int {
        return random.nextInt(length)
    }
}
