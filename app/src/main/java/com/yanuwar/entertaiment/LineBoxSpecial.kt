package com.yanuwar.entertaiment

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class LineBoxSpecial(private val context: Context) {
    fun createBox(width: Int, height: Int): LinearLayout {
        val lineChild = LinearLayout(context)
        val params = LinearLayout.LayoutParams(width, height)
        params.setMargins(2,2,2,2)
        lineChild.layoutParams = params
        lineChild.orientation = LinearLayout.VERTICAL
        lineChild.gravity = Gravity.CENTER
        val border = GradientDrawable()
        border.setColor(-0x1) //white background
        border.setStroke(1, -0x1000000) //black border with full opacity
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            lineChild.setBackgroundDrawable(border)
        } else {
            lineChild.background = border
        }
        lineChild.setPadding(5,5,5,5)

        return lineChild
    }

    fun setBackgroundColorBox(linearLayout: LinearLayout, color: Int? = -0x1000000): LinearLayout {
        val border = GradientDrawable()
        border.setColor(-0x1) //white background
        border.setStroke(1, color?:-0x1000000) //black border with full opacity
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackgroundDrawable(border)
        } else {
            linearLayout.background = border
        }

        return linearLayout
    }

    fun createText(text: String, tag: Any): TextView {
        val textV = TextView(context)
        textV.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textV.textSize = 12F
        textV.tag = tag
        textV.text = text

        return textV
    }
}