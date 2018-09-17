package com.yanuwar.entertaiment

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        btn_converter_suhu.setOnClickListener {
//            startActivity(Intent(this, ConverterSuhu::class.java))
//        }
//        btn_terbilang.setOnClickListener {
//            startActivity(Intent(this, Terbilang::class.java))
//        }
//        btn_tic_tac_toe.setOnClickListener {
//            startActivity(Intent(this, TIcTacToe::class.java))
//        }
//        btn_currency.setOnClickListener {
//            startActivity(Intent(this, Currency::class.java))
//        }
//        btn_hangman.setOnClickListener {
//            startActivity(Intent(this, Hangman::class.java))
//        }
//        btn_berat_ideal.setOnClickListener {
//            startActivity(Intent(this, BeratBadanIdeal::class.java))
//        }
//        btn_wheel_of_name.setOnClickListener {
//            startActivity(Intent(this, WheelOfName::class.java))
//        }
//        btn_jadwal_sholat.setOnClickListener {
//            startActivity(Intent(this, JadwalSholat::class.java))
//        }
//        btn_todo.setOnClickListener {
//            startActivity(Intent(this, TodoApps::class.java))
//        }
        btn_wtf.setOnClickListener {
            startActivity(Intent(this, WhereTheF::class.java))
        }
        btn_calc.setOnClickListener {
            startActivity(Intent(this, Calc::class.java))
        }
        btn_kasir.setOnClickListener {
            startActivity(Intent(this, Kasier::class.java))
        }
        btn_tukar.setOnClickListener {
            startActivity(Intent(this, TukarUang::class.java))
        }
        btn_suit.setOnClickListener {
            startActivity(Intent(this, Pingsut::class.java))
        }

    }
}
