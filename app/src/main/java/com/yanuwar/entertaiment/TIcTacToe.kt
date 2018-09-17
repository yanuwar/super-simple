package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tic_tac_toe.*

class TIcTacToe : AppCompatActivity() {

    private lateinit var columns: Array<TextView>
    private var historyPlayer1 = arrayOfNulls<TextView>(3)
    private var indexTurn = 0
    private var historyPlayer2 = arrayOfNulls<TextView>(3)
    private var indexTurn2 = 0
    private var playerNow = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tic_tac_toe)

        columns = arrayOf(col00, col01, col02, col10, col11, col12, col20, col21, col22)

        for (col in columns) {
            col.setOnClickListener {
                if (playerNow == 1) {
                    indexTurn = if (indexTurn > 2) 0 else indexTurn
                    historyPlayer1[indexTurn]?.text = ""
                    historyPlayer1[indexTurn]?.isEnabled = true
                    historyPlayer1[indexTurn] = col
                    indexTurn++
                    if (playerNow == 1) { col.text = "X" } else if (playerNow == 2) { col.text = "O" }
                    col.isEnabled = false
                    if (checkingHistory("X")) {
                        disableAll(false)
                        tv_turn.text = "Selamat Player 1 Menang"
                    } else {
                        playerNow = 2
                        tv_turn.text = "player: "+ playerNow
                    }
                }else if (playerNow == 2) {
                    indexTurn2 = if (indexTurn2 > 2) 0 else indexTurn2
                    historyPlayer2[indexTurn2]?.text = ""
                    historyPlayer2[indexTurn2]?.isEnabled = true
                    historyPlayer2[indexTurn2] = col
                    indexTurn2++
                    if (playerNow == 1) { col.text = "X" } else if (playerNow == 2) { col.text = "O" }
                    col.isEnabled = false
                    if (checkingHistory("O")) {
                        disableAll(false)
                        tv_turn.text = "Selamat Player 2 Menang"
                    } else {
                        playerNow = 1
                        tv_turn.text = "player: "+ playerNow
                    }
                }
            }
        }
    }

    private fun checkingHistory(value: String): Boolean {
        var isValid = false
        columns.mapIndexed { index, textView ->
            if (index == 0 && textView.text == value) {
                if (columns[1].text == value) {
                    if (columns[2].text == value) {
                        isValid = true
                    }
                }
                if (columns[3].text == value) {
                    if (columns[6].text == value) {
                        isValid = true
                    }
                }
                if (columns[4].text == value) {
                    if (columns[8].text == value) {
                        isValid = true
                    }
                }
            }

            if (index == 1 && textView.text == value) {
                if (columns[4].text == value) {
                    if (columns[7].text == value) {
                        isValid = true
                    }
                }
            }

            if (index == 2 && textView.text == value) {
                if (columns[5].text == value) {
                    if (columns[8].text == value) {
                        isValid = true
                    }
                }
            }

            if (index == 3 && textView.text == value) {
                if (columns[4].text == value) {
                    if (columns[5].text == value) {
                        isValid = true
                    }
                }
            }

            if (index == 6 && textView.text == value) {
                if (columns[7].text == value) {
                    if (columns[8].text == value) {
                        isValid = true
                    }
                }
            }
        }

        return isValid
    }

    private fun disableAll(isDisable: Boolean) {
        for (col in columns) {
            col.isEnabled = isDisable
        }
    }
}
