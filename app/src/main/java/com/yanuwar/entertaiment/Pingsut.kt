package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pingsut.*
import java.util.*

class Pingsut : AppCompatActivity() {

    lateinit var random: Random
    var com = 0
    var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pingsut)

        btn_kertas.setOnClickListener {
            com = getRandom(3)
            when (com) {
                0 -> tv_ai.text = "Seri"
                1 -> { tv_ai.text =  "Menang"; score++ }
                else -> tv_ai.text = "Kalah"
            }
            tv_score.setText("Score: "+ score)
        }
        btn_gunting.setOnClickListener{
            com = getRandom(3)
            when (com) {
                1 -> tv_ai.text = "Seri"
                2 -> { tv_ai.text =  "Menang"; score++ }
                else -> tv_ai.text = "Kalah"
            }
            tv_score.setText("Score: "+ score)
        }
        btn_batu.setOnClickListener {
            com = getRandom(3)
            when (com) {
                2 -> tv_ai.text = "Seri"
                0 -> { tv_ai.text =  "Menang"; score++ }
                else -> tv_ai.text = "Kalah"
            }
            tv_score.setText("Score: "+ score)
        }
    }

    private fun getRandom(length: Int) : Int {
        random = Random()
        return random.nextInt(length)
    }
}
