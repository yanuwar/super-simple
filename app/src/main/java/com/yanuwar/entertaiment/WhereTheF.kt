package com.yanuwar.entertaiment

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_where_the_f.*
import java.util.*

class WhereTheF : AppCompatActivity() {

    private var lokasi = arrayOfNulls<TextView>(8)
    private var fLokasi = 0
    private lateinit var random: Random
    private var totalKolom = 7
    private var nyawa = 10
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_where_the_f)

        lokasi = arrayOf(tv_huruf1, tv_huruf2, tv_huruf3, tv_huruf4, tv_huruf5, tv_huruf6, tv_huruf7)

        getSoal()
    }

    private fun getSoal() {
        fLokasi = getRandom(totalKolom)
        lokasi.mapIndexed { index, textView ->
            if (index < totalKolom) {
                textView?.visibility = View.VISIBLE
                textView?.isEnabled = true
                textView?.text = if (fLokasi == index) "F" else ""
                textView?.setOnClickListener {
                    textView.background = ColorDrawable(resources.getColor(android.R.color.white))
                    if (textView.text == "F") {
                        disableBattleField()
                        tv_nyawa.text = "F Ditemukan Anda Menang"
                        score++
                        tv_ulangi.visibility = View.VISIBLE
                        tv_ulangi.setOnClickListener {
                            resetBattleField()
                            getSoal()
                            tv_ulangi.visibility = View.GONE
                        }
                    } else {
                        nyawa--
                        tv_nyawa.text = "Kesempatan: "+ nyawa
                    }
                    if (nyawa == 0) {
                        disableBattleField()
                        tv_nyawa.text = "Game Over"
                    }
                    textView.isEnabled = false
                    tv_score.text = "Total Score: "+score
                }
            }
        }
    }

    private fun disableBattleField() {
        lokasi.map {
            it?.isEnabled = false
        }
    }

    private fun resetBattleField() {
        lokasi.map {
            it?.visibility = View.GONE
            it?.isEnabled = false
            it?.text = ""
            it?.setOnClickListener { }
            it?.background = ColorDrawable(resources.getColor(android.R.color.black))
        }
    }

    private fun getRandom(length: Int) : Int {
        random = Random()
        return random.nextInt(length)
    }
}
