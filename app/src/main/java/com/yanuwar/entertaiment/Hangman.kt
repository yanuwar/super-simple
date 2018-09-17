package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_hangman.*
import java.util.*

class Hangman : AppCompatActivity() {

    private val random = Random()
    private var kata = "random"
    private lateinit var arrKata: CharArray
    private var battleField = arrayOfNulls<EditText>(7)
    private var indexRandom = 0
    private var kesempatan = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hangman)

        arrKata = CharArray(kata.length)
        kata.toCharArray(arrKata)
        battleField = arrayOf(et_huruf1, et_huruf2, et_huruf3, et_huruf4, et_huruf5, et_huruf6, et_huruf7)

        arrKata.mapIndexed { index, c ->
            val huruf = if (random.nextInt(2) >= 1) c.toString() else { if (indexRandom < 3) { indexRandom++; "" } else {c.toString()} }
            battleField[index]?.visibility = View.VISIBLE
            battleField[index]?.setText(huruf)
        }

        btn_processing.setOnClickListener {
            var result = ""
            battleField.map {
                result += it?.text.toString()
            }

            if (result == kata) {
                tv_result.text = "Selamat Anda Menang"
                setDisable(false)
            } else {
                tv_result.text = if (kesempatan < 3) { kesempatan++; "Masih Salah Coba Lagi" } else { setDisable(false); "Anda Kalah" }
            }
        }
    }

    fun setDisable(isEnable: Boolean) {
        battleField.map {
            it?.isEnabled = isEnable
            btn_processing.isEnabled = false
        }
    }
}
