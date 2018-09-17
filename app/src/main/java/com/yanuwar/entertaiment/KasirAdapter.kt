//package com.yanuwar.entertaiment
//
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//
//class KasirAdapter(val list: MutableList<Barang>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.item_todo, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.itemView.tv_text_todo.text = list[position].todo
//        holder.itemView.btn_delete.setOnClickListener { listener.setOnDelete(position) }
//    }
//
//    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)
//}