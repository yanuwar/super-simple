package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kanban_bot.*

class KanbanBotActivity : AppCompatActivity() {

    val listTodo = arrayListOf<Todoo>()
    var number = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kanban_bot)

        btn_submit.setOnClickListener {
            val text = et_command.text.toString()
            val arrText = text.split(" ")
            when {
                arrText[0] == "create" -> {
                    val leftover = text.substring(arrText[0].length+2, text.length-1)
                    listTodo.add(Todoo(leftover, number, 1))
                    number++
                }
                arrText[0] == "move" -> {
                    val number = arrText[1].toInt()
                    val i: Int =
                    when {
                        arrText[2] == "done" -> 3
                        arrText[2] == "progress" -> 2
                        arrText[2] == "todo" -> 1
                        else -> 0
                    }
                    if (i == 0) {
                        Toast.makeText(this, "Error Tujuan", Toast.LENGTH_SHORT).show()
                    } else {
                        listTodo[number - 1] = Todoo(listTodo[number - 1].text, listTodo[number - 1].number, i)
                    }
                }
                arrText[0] == "remove" -> {
                    val number = arrText[1].toInt()
                    listTodo.removeAt(number-1)

                }
                else -> {
                    Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show()
                }
            }
            renderTodo()

        }
    }

    private fun renderTodo() {
        line_todo.removeAllViews()
        line_progress.removeAllViews()
        line_done.removeAllViews()
        listTodo.forEachIndexed { index, it ->
            val text = TextView(this)
            text.textSize = 20F
            text.setTextColor(resources.getColor(android.R.color.black))
            text.text = (index+1).toString() +". "+ it.text
            when {
                it.position == 1 -> line_todo.addView(text)
                it.position == 2 -> line_progress.addView(text)
                it.position == 3 -> line_done.addView(text)
            }
        }
    }
}
