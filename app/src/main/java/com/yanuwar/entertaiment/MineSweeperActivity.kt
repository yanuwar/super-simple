package com.yanuwar.entertaiment

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_mine_sweeper.*
import java.util.*

class MineSweeperActivity : AppCompatActivity() {

    private var arrLine = arrayListOf<LinearLayout>()
    private var arrText = arrayListOf<TextView>()
    private val random: Random = Random()
    private val arrBom = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_sweeper)

        btn_create.setOnClickListener {
            tv_keterangan.text = ""
            val longBoard = if (et_panjang.text.toString().isNotEmpty()) et_panjang.text.toString().toInt() else 0
            val amountBom = if (et_banyak.text.toString().isNotEmpty()) et_banyak.text.toString().toInt() else 0

            if (longBoard <= 0 || amountBom <= 0) {
                tv_keterangan.text = "Panjang papan dan Jumlah Bom tidak boleh kurang dari 1"
                return@setOnClickListener
            }

            val maxBom: Double = longBoard.toDouble()/3
            if ( maxBom <= amountBom.toDouble()) {
                tv_keterangan.text = "Jumlah bomb tidak boleh melebihi 1/3 panjang papan"
                return@setOnClickListener
            }

            it.isEnabled = false
            et_panjang.isEnabled = false
            et_banyak.isEnabled = false
            var i = 0
            arrBom.clear()
            arrLine.clear()
            arrText.clear()
            line_parent.removeAllViews()
            arrBom.addAll(getRandomBom(amountBom, longBoard))

            while (i < longBoard) {
                val lineChild = LinearLayout(this)
                val params = LinearLayout.LayoutParams(80, 80)
                params.setMargins(2,2,2,2)
                lineChild.layoutParams = params
                lineChild.orientation = LinearLayout.VERTICAL
                lineChild.gravity = Gravity.CENTER
                val border = GradientDrawable()
                border.setColor(-0x1) //white background
                border.setStroke(1, -0x1000000) //black border with full opacity
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    lineChild.setBackgroundDrawable(border)
                } else {
                    lineChild.background = border
                }
                lineChild.setPadding(5,5,5,5)

                val text = TextView(this)
                text.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                text.textSize = 12F
                text.tag = "0"
                lineChild.addView(text)

                line_parent.addView(lineChild)
                arrLine.add(lineChild)
                arrText.add(text)
                i++
            }

            initBom(longBoard)
            initOnclick()
        }

        btn_retry.setOnClickListener {
            it.visibility = View.GONE
            it.isEnabled = true
            et_panjang.isEnabled = true
            et_banyak.isEnabled = true
            et_banyak.setText("")
            et_panjang.setText("")
            tv_keterangan.text = ""
        }
    }

    private fun openWhenClick(position: Int, longBoard: Int) {
        arrText[position].text = arrText[position].tag.toString()

        var i = position
        while (i < longBoard) {
            if (arrText[i].tag.toString() == "*") {
                break
            } else if (arrText[i].tag.toString().toInt() > 0) {
                arrText[i].text = arrText[i].tag.toString()
                break
            } else {
                arrText[i].text = arrText[i].tag.toString()
            }
            i++
        }
        i = position
        while (-1 < i) {
            if (arrText[i].tag.toString() == "*") {
                break
            } else if (arrText[i].tag.toString().toInt() > 0) {
                arrText[i].text = arrText[i].tag.toString()
                break
            } else {
                arrText[i].text = arrText[i].tag.toString()
            }
            i--
        }

        if (checkingWin()) {
            disableField()
            btn_retry.visibility = View.VISIBLE
            tv_keterangan.text = "Selamat anda menang"
        }
    }

    private fun initOnclick() {
        arrLine.forEachIndexed { index, linearLayout ->
            linearLayout.setOnClickListener {
                if (arrText[index].tag.toString() != "*") {
                    openWhenClick(index, arrLine.size)
                } else {
                    setGameOver()
                }
                it.setOnClickListener {  }
            }
        }
    }

    private fun setGameOver() {
        tv_keterangan.text = "Game is Over"
        arrBom.forEach {
            arrText[it].text = arrText[it].tag.toString()
        }
        disableField()
        btn_retry.visibility = View.VISIBLE
    }

    private fun disableField() {
        arrLine.forEach {
            it.setOnClickListener {  }
        }
    }

    private fun checkingWin(): Boolean {
        var isWin = true
        arrText.forEachIndexed { index, textView ->
            if (!arrBom.contains(index)) {
                if (textView.text.toString().isBlank()) {
                    isWin = false
                }
            }
        }

        return isWin
    }

    private fun initBom(longBoard: Int) {
        arrBom.forEach {
            arrText[it].tag = "*"
            if (it-1 > -1 && arrText[it-1].tag.toString() != "*") arrText[it-1].tag = arrText[it-1].tag.toString().toInt() + 1
            if (it+1 < longBoard && arrText[it+1].tag.toString() != "*") arrText[it+1].tag = arrText[it+1].tag.toString().toInt() + 1
            e("this is cheat ", it.toString())
        }
    }

    private fun getRandomBom(amountBom: Int, longBoard: Int): MutableList<Int> {
        val result = arrayListOf<Int>()
        var tempLength = amountBom

        while (tempLength > 0) {
            val tempBomPos = getRandom(longBoard)
            if (!result.contains(tempBomPos)) {
                result.add(tempBomPos)
                tempLength--
            }
        }

        return result
    }

    private fun getRandom(length: Int) : Int {
        return random.nextInt(length)
    }
}
