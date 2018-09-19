package com.yanuwar.entertaiment

class Ladder (
        val from: Int,
        val to: Int
) {
    var top = if (from > to) from else to
    var bottom = if (from < to) from else to
}