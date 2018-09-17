package com.yanuwar.entertaiment

import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.widget.TextView
import java.util.*
import android.app.Activity
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.activity_wtfspecial.*


class WTFSpecial : AppCompatActivity() {

    private var lokasi = arrayOfNulls<TextView>(3)
    private var fLokasi = 0
    private lateinit var random: Random
    private var totalKolom = 3
    private var zLokasi = arrayOfNulls<Int>(2)
    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wtfspecial)

        tv_keterangan.text = ""
        lokasi = arrayOf(tv_huruf1, tv_huruf2, tv_huruf3)

        reset.setOnClickListener {
            reset.visibility = View.GONE
            tv_keterangan.text = ""
            resetBattleField()
            initGame()
        }

        initGame()
    }

    private fun initGame() {
        clickCount = 0
        fLokasi = getRandom(3)

        zLokasi[0] = getFree(fLokasi, fLokasi)
        zLokasi[1] = getFree(fLokasi, zLokasi[0]?:5)
        zLokasi.map {
            e("cihuy", fLokasi.toString() + ", " + it.toString())
        }

        lokasi.mapIndexed { index, textView ->
            if (index < totalKolom) {
                textView?.visibility = View.VISIBLE
                textView?.isEnabled = true
                textView?.text = if (fLokasi == index) "M" else if (zLokasi[0] == index) "Z" else if (zLokasi[1] == index) "Z" else ""
                textView?.setOnClickListener {
                    if (clickCount in 0..1) {
                        showDialog(activity = this, message = "Anda Yakin ingin membuka nomer "+(index+1)+"?", index = index)
                    }
                }
            }
        }

    }

    private fun disableBattleField() {
        lokasi.map {
            it?.isEnabled = false
        }

        reset.visibility = View.VISIBLE
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

    private fun getFree(fLokasi: Int, secondZ: Int): Int {
        val tempRandom = getRandom(3)
        e("loging", secondZ.toString())
        return if (tempRandom != fLokasi && secondZ != tempRandom) tempRandom else getFree(fLokasi, secondZ)
    }

    private fun getRandom(length: Int) : Int {
        random = Random()
        return random.nextInt(length)
    }

    fun showDialog(activity: Activity, title: String? = null, message: CharSequence, index: Int) {
        val builder = AlertDialog.Builder(activity)

        if (title != null) builder.setTitle(title)

        builder.setMessage(message)
        builder.setPositiveButton("Ya") { dialog, id ->
            if (clickCount == 0) {
                if (zLokasi[0] == index) {
                    lokasi[zLokasi[1]?:0]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    tv_keterangan.text = "sebelumnya kita buka dulu nomer "+((zLokasi[1]?:0)+1).toString()
                    lokasi[zLokasi[1]?:0]?.isEnabled = false
                    clickCount++
                } else if (zLokasi[1] == index) {
                    lokasi[zLokasi[0]?:0]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    tv_keterangan.text = "sebelumnya kita buka dulu nomer "+((zLokasi[0]?:0)+1).toString()
                    lokasi[zLokasi[0]?:0]?.isEnabled = false
                    clickCount++
                } else if (fLokasi == index) {
                    lokasi[zLokasi[0]?:0]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    tv_keterangan.text = "sebelumnya kita buka dulu nomer "+((zLokasi[0]?:0)+1).toString()
                    lokasi[zLokasi[0]?:0]?.isEnabled = false
                    clickCount++
                }
            } else if (clickCount == 1) {
                if (fLokasi == index){
                    e("cihuy", "menang")
                    tv_keterangan.text = "Selamat Anda Menang"
                    lokasi[fLokasi]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    zLokasi.map {
                        lokasi[it?:0]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    }
                    disableBattleField()
                } else {
                    e("cihuy", "kalah")
                    tv_keterangan.text = "Zonk Anda Kalah..."
                    lokasi[fLokasi]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    zLokasi.map {
                        lokasi[it?:0]?.background = ColorDrawable(resources.getColor(android.R.color.white))
                    }
                    disableBattleField()
                }
            }

            dialog.dismiss() }
        builder.setNegativeButton("Tidak") { dialog, id ->
            dialog.dismiss() }
        builder.show()
    }
}
