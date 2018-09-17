package com.yanuwar.entertaiment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_todo_apps.*

class TodoApps : AppCompatActivity(), TodoAdapter.ListenerOnClick {
    private val listTodo: MutableList<ToDo> = arrayListOf()
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_apps)

        adapter = TodoAdapter(listTodo, this)
        rv_todolist.layoutManager = LinearLayoutManager(this)
        rv_todolist.itemAnimator = DefaultItemAnimator()
        rv_todolist.adapter = adapter

        btn_tambahkan.setOnClickListener {
            listTodo.add(ToDo(et_todo.text.toString(), false))
            adapter.notifyDataSetChanged()
        }
    }

    override fun setOnDelete(position: Int) {
        listTodo.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}
