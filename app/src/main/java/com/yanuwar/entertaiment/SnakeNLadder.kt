package com.yanuwar.entertaiment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log.e
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_snake_nladder.*
import java.util.*
import android.os.Build
import android.graphics.drawable.GradientDrawable
import android.util.Log


class SnakeNLadder : AppCompatActivity() {

    private var arrLineButton = arrayOfNulls<MutableList<LinearLayout>>(5)
    private var arrLineTextPlayer = arrayOfNulls<MutableList<TextView>>(5)
    private var arrLineTextNumber = arrayOfNulls<MutableList<TextView>>(5)
    private var arrLineTextState = arrayOfNulls<MutableList<TextView>>(5)
    private var arrKotak = arrayListOf<LinearLayout>()
    private var arrTextNumber = arrayListOf<TextView>()
    private var arrTextPlayer = arrayListOf<TextView>()
    private var arrTextState = arrayListOf<TextView>()
    private var arrLadder = arrayListOf<Ladder>()
    private var arrSnake = arrayListOf<Ladder>()
    private var random: Random = Random()
    private var isCreateBattleField = true
    private var playerTurn = 1
    private var positionP1 = 0
    private var positionP2 = 0

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snake_nladder)

        var y = 0
        var x = 0
        while (y < 5) {
            val lineParent = LinearLayout(this)
            lineParent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lineParent.orientation = LinearLayout.HORIZONTAL

            val arrLineParent = arrayListOf<LinearLayout>()
            val arrTextPlayer = arrayListOf<TextView>()
            val arrTextNumber = arrayListOf<TextView>()
            val arrTextState = arrayListOf<TextView>()

            x = 0
            while (x < 5) {

                val lineChild = LinearLayout(this)
                val params = LinearLayout.LayoutParams(140, 140)
                params.setMargins(2,2,2,2)
                lineChild.layoutParams = params
                lineChild.orientation = LinearLayout.VERTICAL
                lineChild.gravity = Gravity.CENTER_VERTICAL
                val border = GradientDrawable()
                border.setColor(-0x1) //white background
                border.setStroke(1, -0x1000000) //black border with full opacity
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    lineChild.setBackgroundDrawable(border)
                } else {
                    lineChild.background = border
                }
                lineChild.setPadding(5,5,5,5)

                val text2 = TextView(this)
                text2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                text2.textSize = 12F
                lineChild.addView(text2)
                arrTextNumber.add(text2)

                val text1 = TextView(this)
                text1.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                text1.gravity = Gravity.CENTER
                text1.textSize = 20F
                lineChild.addView(text1)
                arrTextPlayer.add(text1)

                val text3 = TextView(this)
                text3.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                text3.gravity = Gravity.CENTER
                lineChild.addView(text3)
                arrTextState.add(text3)

                arrLineParent.add(lineChild)
                lineParent.addView(lineChild)

                x++
            }

            arrLineButton[y] = arrLineParent
            arrLineTextNumber[y] = arrTextNumber
            arrLineTextPlayer[y] = arrTextPlayer
            arrLineTextState[y] = arrTextState

            line_parent?.addView(lineParent)
            y++
        }

        y = 4
        while (y > -1) {
            if (y % 2 == 0) {
                x = 0
                while (x < 5) {
                    arrKotak.add(arrLineButton[y]!![x])
                    arrTextNumber.add(arrLineTextNumber[y]!![x])
                    arrTextPlayer.add(arrLineTextPlayer[y]!![x])
                    arrTextState.add(arrLineTextState[y]!![x])
                    x++
                }
            } else {
                x = 4
                while (x > -1) {
                    arrKotak.add(arrLineButton[y]!![x])
                    arrTextNumber.add(arrLineTextNumber[y]!![x])
                    arrTextPlayer.add(arrLineTextPlayer[y]!![x])
                    arrTextState.add(arrLineTextState[y]!![x])
                    x--
                }
            }
            y--
        }

        var number = 1
        arrTextNumber.map {
            it.text = number.toString()
            number++
        }

        arrTextState[0].text = "Start"
        arrTextPlayer[0].text = "P1-P2"
        arrTextState[arrTextState.size-1].text = "Pinish"

        btn_add_snake.setOnClickListener {
            val mIntent = Intent(this, AddSnake::class.java)
            mIntent.putExtra("isAddSnake", true)
            startActivityForResult(mIntent, 1231)
        }
        btn_add_ladder.setOnClickListener {
            val mIntent = Intent(this, AddSnake::class.java)
            mIntent.putExtra("isAddLadder", true)
            startActivityForResult(mIntent, 1231)
        }
        btn_done.setOnClickListener {
            if (arrSnake.size > 0 && arrLadder.size > 0) {
                tv_top.text = "Mulai Main"
                isCreateBattleField = false
                btn_add_snake.visibility = View.GONE
                btn_add_ladder.visibility = View.GONE
                btn_done.visibility = View.GONE
                btn_roll.visibility = View.VISIBLE
                tv_keterangan.text = "Player ke $playerTurn"
                arrLadder.map {
                    arrTextState[it.from-1].text = "Tangga -> "+it.to
                }
                arrSnake.map {
                    arrTextState[it.from-1].text = "Ular -> "+it.to
                }
                btn_1.visibility = View.VISIBLE
                btn_2.visibility = View.VISIBLE
                btn_3.visibility = View.VISIBLE
                btn_4.visibility = View.VISIBLE
                btn_5.visibility = View.VISIBLE
                btn_6.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Tangga dan Ular Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
            }
        }

        btn_roll.setOnClickListener {
            val dice = getRandom(6)
            if (playerTurn == 1) {
                if ((positionP1 + dice) == 24) {
                    arrTextPlayer[positionP1].text = ""
                    setGameOver()
                } else {
                    arrTextPlayer[positionP1].text = ""
                    if ((positionP1 + dice) < 24) positionP1 += dice else {
                        val tempP1 = positionP1 + dice
                        positionP1 = 24 - (tempP1-24)
                    }
                    positionP1 = getCheck(positionP1, arrLadder)
                    positionP1 = getCheck(positionP1, arrSnake)
                    showDialogStockCard(activity = this, message = if (playerTurn==1) "Player 1 " else {"Player 2 "} + "Dapat "+ dice +" maju ke "+ (positionP1+1))
                    playerTurn = 2
                    tv_keterangan.text = "Player ke $playerTurn"
                    setPosition()
                }
            } else {
                if ((positionP2 + dice) == 24) {
                    arrTextPlayer[positionP2].text = ""
                    setGameOver()
                } else {
                    arrTextPlayer[positionP2].text = ""
                    if ((positionP2 + dice) < 24) positionP2 += dice else {
                        val tempP2 = positionP2 + dice
                        positionP2 = 24 - (tempP2 - 24)
                    }
                    positionP2 = getCheck(positionP2, arrLadder)
                    positionP2 = getCheck(positionP2, arrSnake)
                    showDialogStockCard(activity = this, message = if (playerTurn==1) "Player 1 " else {"Player 2 "} + "Dapat "+ dice +" maju ke "+ (positionP2+1))
                    playerTurn = 1
                    tv_keterangan.text = "Player ke $playerTurn"
                    setPosition()
                }
            }
        }
        setPosition()
        btn_1.setOnClickListener { rollingPlayer(1) }
        btn_2.setOnClickListener { rollingPlayer(2) }
        btn_3.setOnClickListener { rollingPlayer(3) }
        btn_4.setOnClickListener { rollingPlayer(4) }
        btn_5.setOnClickListener { rollingPlayer(5) }
        btn_6.setOnClickListener { rollingPlayer(6) }
    }

    private fun rollingPlayer(dice: Int) {
        if (playerTurn == 1) {
            if ((positionP1 + dice) == 24) {
                arrTextPlayer[positionP1].text = ""
                setGameOver()
            } else {
                arrTextPlayer[positionP1].text = ""
                if ((positionP1 + dice) < 24) positionP1 += dice else {
                    val tempP1 = positionP1 + dice
                    positionP1 = 24 - (tempP1-24)
                }
                positionP1 = getCheck(positionP1, arrLadder)
                positionP1 = getCheck(positionP1, arrSnake)
                showDialogStockCard(activity = this, message = if (playerTurn==1) "Player 1 " else {"Player 2 "} + "Dapat "+ dice +" maju ke "+ (positionP1+1))
                playerTurn = 2
                tv_keterangan.text = "Player ke $playerTurn"
                setPosition()
            }
        } else {
            if ((positionP2 + dice) == 24) {
                arrTextPlayer[positionP2].text = ""
                setGameOver()
            } else {
                arrTextPlayer[positionP2].text = ""
                if ((positionP2 + dice) < 24) positionP2 += dice else {
                    val tempP2 = positionP2 + dice
                    positionP2 = 24 - (tempP2 - 24)
                }
                positionP2 = getCheck(positionP2, arrLadder)
                positionP2 = getCheck(positionP2, arrSnake)
                showDialogStockCard(activity = this, message = if (playerTurn==1) "Player 1 " else {"Player 2 "} + "Dapat "+ dice +" maju ke "+ (positionP2+1))
                playerTurn = 1
                tv_keterangan.text = "Player ke $playerTurn"
                setPosition()
            }
        }
    }

    private fun setPosition() {
        if (positionP1 == positionP2) {
            arrTextPlayer[positionP1].text = "P1-P2"
        } else {
            arrTextPlayer[positionP1].text = "P1"
            arrTextPlayer[positionP2].text = "P2"
        }
    }

    private fun getCheck(posisi: Int, list: MutableList<Ladder>): Int {
        var result = posisi
        list.mapIndexed { index, ladder ->
            if (posisi == (ladder.from-1)) {
                result = (ladder.to-1)
            }
        }

        return result
    }

    private fun setGameOver() {
        tv_keterangan.text = if (playerTurn == 1) "Player 1 Menang" else "Player 2 Menang"
        showDialogStockCard(activity = this, message = if (playerTurn == 1) "Player 1 Menang" else "Player 2 Menang")
        btn_roll.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            val rules2 = arrayListOf<MutableList<Int>>()
            rules2.add(arrayListOf())
            var i = 0
            while (i < 25) {
                if ((i+1) % 5 == 0) {
                    rules2[rules2.size-1].add(i+1)
                    if (i+1 < 25) rules2.add(arrayListOf())
                } else {
                    rules2[rules2.size-1].add(i+1)
                }
                i++
            }

            rules2.mapIndexed { index, mutableList ->
                if ((index) % 2 != 0) {
                    rules2[index] = mutableList.asReversed()
                }
            }

            val from = data.extras.getInt("from")
            val to = data.extras.getInt("to")
            if (data.extras.getBoolean("isAddLadder", false)) {
                if (from < to) {
                    rules2.mapIndexed { index, ints ->
                        ints.map {
                            if (from == it) {
                                when {
                                    from > 24 || to > 24 || from <= 1 || to <= 1 -> Toast.makeText(this, "Finish dan Start tidak boleh dipakai", Toast.LENGTH_SHORT).show()
                                    ints.contains(to) -> Toast.makeText(this, "Gagal Tidak Boleh Sebaris", Toast.LENGTH_SHORT).show()
                                    arrLadder.size > -1 -> {
                                        var isValid = true
                                        arrLadder.map {
                                            if (arrSnake.size > 0) checkInRules(arrSnake[0], Ladder(from, to),rules2)
                                            isValid = it.from != from && it.from != to && it.to != from && it.to != to
                                        }
                                        arrSnake.map {
                                            isValid = it.from != from && it.from != to && it.to != from && it.to != to
                                        }

                                        if (isValid) {
                                            if (arrSnake.size > 0) checkInRules(arrSnake[0], Ladder(from, to),rules2)
                                            if (arrLadder.size > 0) checkInRules(arrLadder[0], Ladder(from, to),rules2)
                                            arrLadder.add(Ladder(from, to))
                                        } else {
                                            Toast.makeText(this, "Kotak Sudah Terisi", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    arrLadder.map {
                        arrTextState[it.from-1].text = "Tangga -> "+it.to
                    }
                } else {
                    Toast.makeText(this, "Tangga awal harus lebih dari atas", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (from > to && !rules2[0].contains(from)) {
                    rules2.mapIndexed { index, ints ->
                        ints.map {
                            if (from == it) {
                                when {
                                    from > 24 || to > 24 || from <= 1 || to <= 1 -> Toast.makeText(this, "Finish dan Start tidak boleh dipakai", Toast.LENGTH_SHORT).show()
                                    ints.contains(to) -> Toast.makeText(this, "Gagal Tidak Boleh Sebaris", Toast.LENGTH_SHORT).show()
//                                arrLadder.size > 1 -> {
//                                    if (f)
//                                }
                                    arrSnake.size > -1 -> {
                                        var isValid = true
                                        arrLadder.map {
                                            isValid = it.from != from && it.from != to && it.to != from && it.to != to
                                        }
                                        arrSnake.map {
                                            isValid = it.from != from && it.from != to && it.to != from && it.to != to
                                        }

                                        if (isValid) {
                                            if (arrSnake.size > 0) checkInRules(arrSnake[0], Ladder(from, to),rules2)
                                            if (arrLadder.size > 0) checkInRules(arrLadder[0], Ladder(from, to),rules2)
                                            arrSnake.add(Ladder(from, to))
                                        } else {
                                            Toast.makeText(this, "Kotak Sudah Terisi", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    arrSnake.map {
                        arrTextState[it.from - 1].text = "Snake -> " + it.to
                    }
                } else {
                    Toast.makeText(this, "Ular Atas Tidak Boleh Kurang dari ular Bawah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkInRules(ladder1: Ladder, ladder2: Ladder, rules: MutableList<MutableList<Int>>): Boolean {
        var x1 = 0
        var y1 = 0
        var x2 = 0
        var y2 = 0

        var xp1 = 0
        var xp2 = 0
        var yp1 = 0
        var yp2 = 0

        var valid = true

        rules.mapIndexed { index, mutableList ->
            if ((index) % 2 != 0) {
                rules[index] = mutableList.asReversed()
            }
        }

        rules.mapIndexed { index, mutableList ->
            if (mutableList.contains(ladder1.from)) {
                x1 = mutableList.indexOf(ladder1.from)
                y1 = index
            }
            if (mutableList.contains(ladder1.to)) {
                x2 = mutableList.indexOf(ladder1.to)
                y2 = index
            }
            if (mutableList.contains(ladder2.from)) {
                xp1 = mutableList.indexOf(ladder2.from)
                yp1 = index
            }
            if (mutableList.contains(ladder2.to)) {
                xp2 = mutableList.indexOf(ladder2.to)
                yp2 = index
            }
        }

//        x1 = if (x1 < x2) x1 else x2
//        x2 = if (x1 > x2) x1 else x2

        e("cihuy", ""+x1+", "+y1+" | "+xp1+", "+yp1)
        e("cihuy", ""+x2+", "+y2+" | "+xp2+", "+yp2)
//        if ((xp1 in x1..x2 && yp1 in y1..y1) || (xp2 in x1..x2 && yp2 in y2..y1)) {
//
//        }

        return false
    }

    private fun showDialogStockCard(activity: Activity, title: String? = null, message: CharSequence) {
//        val builder = AlertDialog.Builder(activity)
//
//        if (title != null) builder.setTitle(title)
//
//        builder.setMessage(message)
//        builder.setPositiveButton("Ok") { dialog, id ->
//            dialog.dismiss() }
//        builder.show()
        tv_keterangan2.text = message
    }

    private fun getRandom(length: Int) : Int {
        return random.nextInt(length)+1
    }
}
