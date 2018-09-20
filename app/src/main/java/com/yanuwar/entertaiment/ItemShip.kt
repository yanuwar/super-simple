package com.yanuwar.entertaiment

class ItemShip (
        val id:Int,
        val stock: Int,
        val name: String,
        val quota: Int,
        val orientation: Int? = 0,
        val x: Int? = 0,
        val y: Int? = 0,
        var isDestroy: Boolean? = false
) {
    val initial = name[0].toString()
}