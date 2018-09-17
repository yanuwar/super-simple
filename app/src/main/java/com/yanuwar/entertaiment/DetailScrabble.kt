package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_detail_scrabble.*

class DetailScrabble : AppCompatActivity() {
    private var choosingBtn = arrayOfNulls<Button>(7)
    private var arrHuruf = arrayOfNulls<String>(7)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_scrabble)

        arrHuruf = intent.extras.getStringArray("arrStockCard")

        choosingBtn = arrayOf(btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7)

        tv_player_turn.text = "Player turn: " + intent.extras.getInt("turn")

        arrHuruf.mapIndexed { index, letter ->
            if (letter != null) {
                choosingBtn[index]?.visibility = View.VISIBLE
                choosingBtn[index]?.setOnClickListener {
                    val mIntent = intent
                    mIntent.putExtra("choosingHuruf", arrHuruf[index])
                    mIntent.putExtra("indexBtn", intent.extras.getInt("indexBtn"))
                    mIntent.putExtra("indexLine", intent.extras.getInt("indexLine"))
                    mIntent.putExtra("turn", intent.extras.getInt("turn"))
                    mIntent.putExtra("isDelete", false)
                    setResult(10004, mIntent)
                    finish()
                }
                choosingBtn[index]?.text = arrHuruf[index]
            }
        }

        btn_delete.setOnClickListener {
            val mIntent = intent
            mIntent.putExtra("indexBtn", intent.extras.getInt("indexBtn"))
            mIntent.putExtra("indexLine", intent.extras.getInt("indexLine"))
            mIntent.putExtra("turn", intent.extras.getInt("turn"))
            mIntent.putExtra("isDelete", true)
            setResult(10004, mIntent)
            finish()
        }
    }
}
