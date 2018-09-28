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
    private val history = arrayListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mine_sweeper)

        btn_create.setOnClickListener {
            tv_keterangan.text = ""
            val longBoard = if (et_panjang.text.toString().isNotEmpty()) et_panjang.text.toString().toInt() else 0
            val amountBom = if (et_banyak.text.toString().isNotEmpty()) et_banyak.text.toString().toInt() else 0

            if (longBoard <= 0) {
                tv_keterangan.text = "Panjang papan tidak boleh kurang dari 1"
                return@setOnClickListener
            }

            if (amountBom < 0) {
                tv_keterangan.text = "Bom tidak boleh kurang dari 0"
                return@setOnClickListener
            }

            val maxBom: Double = longBoard.toDouble()/3
            if ( maxBom+1 <= amountBom.toDouble()) {
                tv_keterangan.text = "Jumlah bomb tidak boleh melebihi 1/3 panjang papan"
                return@setOnClickListener
            }

            onNewGame()
            arrBom.addAll(getRandomBom(amountBom, longBoard))
            var i = 0

            while (i < longBoard) {
                val lineChild = LineBoxSpecial(this).createBox(80, 80)

                val text = LineBoxSpecial(this).createText("", "0")
                lineChild.addView(text)

                line_parent.addView(lineChild)
                arrLine.add(lineChild)
                arrText.add(text)
                i++
            }

            initBom(longBoard)
            initOnclick(longBoard)
        }

        btn_retry.setOnClickListener {
            resetBattleField()
        }

        tv_keterangan.setOnClickListener {
            showCheat()
        }
    }

    private fun showCheat() {
        arrBom.forEach {
            tv_keterangan.append(" | " + (it+1).toString() + " | ")
        }
    }

    private fun onNewGame() {
        btn_create.isEnabled = false
        et_panjang.isEnabled = false
        et_banyak.isEnabled = false
        arrBom.clear()
        arrLine.clear()
        arrText.clear()
        history.clear()
        line_parent.removeAllViews()
    }

    private fun resetBattleField() {
        arrBom.clear()
        line_parent.removeAllViews()
        btn_retry.visibility = View.GONE
        btn_create.isEnabled = true
        et_panjang.isEnabled = true
        et_banyak.isEnabled = true
        et_banyak.setText("")
        et_panjang.setText("")
        tv_keterangan.text = ""
    }

    private fun openWhenClick(position: Int, longBoard: Int) {
        openAroundBox(position, longBoard)

        if (checkingWin()) {
            disableField()
            btn_retry.visibility = View.VISIBLE
            tv_keterangan.text = "Selamat anda menang"
        }
    }

    private fun openAroundBox(position: Int, longBoard: Int) {
        if (arrText[position].text.toString().isEmpty()) {
            if (arrText[position].tag.toString() != "*") {
                if (arrText[position].tag.toString().toInt() > 0) {
                    openBox(position)
                } else {
                    openBox(position)
                    if (position + 1 in 0 until longBoard) openAroundBox(position + 1, longBoard)
                    if (position - 1 in 0 until longBoard) openAroundBox(position - 1, longBoard)
                }
            }
        }
    }

    private fun openBox(position: Int) {
        arrText[position].text = arrText[position].tag.toString()
        addToHistory(position)
        arrLine[position].setOnClickListener {  }
    }

    private fun addToHistory(position: Int) {
        if (!history.contains(position)) history.add(position)
    }

    private fun initOnclick(longBoard: Int) {
        arrLine.forEachIndexed { index, linearLayout ->
            linearLayout.setOnClickListener {
                if (arrText[index].tag.toString() != "*") {
                    openWhenClick(index, longBoard)
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
        return arrText.size-history.size == arrBom.size
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
