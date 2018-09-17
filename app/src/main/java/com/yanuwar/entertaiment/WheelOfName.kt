package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_wheel_of_name.*
import java.util.*

class WheelOfName : AppCompatActivity() {

    private var random = Random()
    private var textPlayerList = arrayOfNulls<TextView>(10)
    private var playerList: MutableList<String> = arrayListOf()
    private var indexTotal: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wheel_of_name)
        textPlayerList = arrayOf(name1, name2, name3, name4, name5, name6, name7, name8, name9, name10)

        btn_masukkan.setOnClickListener {
            if (indexTotal < 10) playerList.add(indexTotal, et_name.text.toString()) else { indexTotal = 0; playerList.add(indexTotal, et_name.text.toString()) }
            textPlayerList[indexTotal]?.visibility = View.VISIBLE
            textPlayerList[indexTotal]?.text = et_name.text.toString()
            indexTotal++
        }
        btn_puter.setOnClickListener {
            if (indexTotal >= 0) {
                val randomVal = getRandom(indexTotal)
                tv_val_name.text = playerList[randomVal]
                playerList.removeAt(randomVal)
                reFill()
                indexTotal--
            }
        }
    }

    private fun reFill() {
        textPlayerList.map {
            it?.visibility = View.GONE
        }
        playerList.mapIndexed { index, s ->
            textPlayerList[index]?.visibility = View.VISIBLE
            textPlayerList[index]?.text = et_name.text.toString()
        }
    }

    private fun getRandom(length: Int) : Int {
        random = Random()
        return random.nextInt(length)
    }
}
