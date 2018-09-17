package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_kasier.*

class Kasier : AppCompatActivity() {

    var list: MutableList<Barang> = arrayListOf()
    var valueTotal = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kasier)

        btn_masukan.setOnClickListener {
            if (et_name.text.toString().isNotBlank()) {
                if (et_jumlah.text.toString().isNotBlank()) {
                    if (et_harga.text.toString().isNotBlank()) {
                        list.add(
                                Barang(
                                        et_name.text.toString(),
                                        et_jumlah.text.toString().toInt(),
                                        et_harga.text.toString().toInt()
                                        )
                        )

                        tv_history.append(et_name.text.toString() + " | " + et_jumlah.text.toString().toInt() + " | @"+et_harga.text.toString().toInt()+ "\n")
                        valueTotal += (et_jumlah.text.toString().toInt()*et_harga.text.toString().toInt())
                        tv_final_result.setText("Total Bayar:" + valueTotal)
                    }
                }
            }
        }

    }
}
