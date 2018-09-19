package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_snake.*

class AddSnake : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_snake)
        if (intent.extras.getBoolean("isAddLadder", false)) {
            tv_keterangan.text = "Form Tambah Tangga"
        }

        if (intent.extras.getBoolean("isAddSnake", false)) {
            tv_keterangan.text = "Form Tambah Ular"
        }

        btn_done.setOnClickListener {
            val mIntent = intent
            mIntent.putExtra("isAddLadder", intent.extras.getBoolean("isAddLadder", false))
            mIntent.putExtra("isAddSnake", intent.extras.getBoolean("isAddSnake", false))
            mIntent.putExtra("from", et_from.text.toString().toInt())
            mIntent.putExtra("to", et_to.text.toString().toInt())
            setResult(1211, mIntent)
            finish()
        }
    }
}
