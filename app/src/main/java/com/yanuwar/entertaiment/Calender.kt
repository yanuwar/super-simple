package com.yanuwar.entertaiment

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_calender.*
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class Calender : AppCompatActivity() {

    private val arrLine = arrayListOf<MutableList<LinearLayout>>()
    private val arrLineTextPlayer = arrayListOf<MutableList<TextView>>()
    private val arrLineTextHari = arrayListOf<MutableList<TextView>>()
    private val arrDay = arrayListOf<Date>()
    private val arr2Day = arrayListOf<MutableList<Date>>()

    private val listHari = arrayOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Ahad")
    private val listMonth = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)

        val adapterMonth = ArrayAdapter(this,android.R.layout.simple_spinner_item, listMonth)
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_month.adapter = adapterMonth

        et_year.setText("2018")

        btn_buka.setOnClickListener {
            line_parent.removeAllViews()
            arr2Day.clear()
            arrDay.clear()
            val calendar = Calendar.getInstance()
            val dateString = "01-"+(listMonth.indexOf(sp_month.selectedItem.toString())+1)+"-"+et_year.text.toString()
            calendar.time = dateString.simpleToDate()

            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            val dateMax = calendar.time

            calendar.set(Calendar.DAY_OF_MONTH,
                    calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
            val dateMin = calendar.time

            var i = dateMin.getDD()
            while (i <= dateMax.getDD()) {
                val dateToAdd = i.toString()+"-"+(listMonth.indexOf(sp_month.selectedItem.toString())+1)+"-"+et_year.text.toString()
                calendar.time = dateToAdd.simpleToDate()
                arrDay.add(calendar.time)
                i++
            }

            renderDate()
        }
    }

    private fun renderDate() {
        val calendar = Calendar.getInstance()
        calendar.time = arrDay[0]
        if (calendar.get(Calendar.DAY_OF_WEEK) != 1 && calendar.get(Calendar.DAY_OF_WEEK) != 2) {
            addDateEnd(calendar.get(Calendar.DAY_OF_WEEK)-2, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            addDateEnd(6, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {

        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
            addDateEnd(1, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
            addDateEnd(2, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
            addDateEnd(3, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            addDateEnd(4, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
            addDateEnd(5, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                arrDay.add(0, date)
            }
        }

        calendar.time = arrDay[arrDay.size-1]
        when {
            calendar.get(Calendar.DAY_OF_WEEK) == 1 -> {

            }
            calendar.get(Calendar.DAY_OF_WEEK) == 2 -> {
                addDateStart(6, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            }
            calendar.get(Calendar.DAY_OF_WEEK) == 3 -> {
                addDateStart(5, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            }
            calendar.get(Calendar.DAY_OF_WEEK) == 4 -> {
                addDateStart(4, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            }
            calendar.get(Calendar.DAY_OF_WEEK) == 5 -> {
                addDateStart(3, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            }
            calendar.get(Calendar.DAY_OF_WEEK) == 6 -> {
                addDateStart(2, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            }
            calendar.get(Calendar.DAY_OF_WEEK) == 7 -> {
                addDateStart(1, calendar.get(Calendar.MONTH)).forEachIndexed { index, date ->
                    arrDay.add(date)
                }
            } else -> {

            }
        }

        var i = 0
        arr2Day.add(arrayListOf())
        arrDay.forEachIndexed { index, date ->
            calendar.time = date
            if ((index+1)%7 != 0) {
                arr2Day[i].add(date)
            } else {
                arr2Day[i].add(date)
                i++
                arr2Day.add(arrayListOf())
            }
        }

        val lineParentInit = LinearLayout(this)
        lineParentInit.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lineParentInit.orientation = LinearLayout.HORIZONTAL

        listHari.forEachIndexed { index, s ->
            val lineChild = LinearLayout(this)
            val params = LinearLayout.LayoutParams(75, 75)
            params.setMargins(2,2,2,2)
            lineChild.layoutParams = params
            lineChild.orientation = LinearLayout.VERTICAL
            lineChild.gravity = Gravity.CENTER_VERTICAL
            val border = GradientDrawable()
            border.setColor(-0x1) //white background
            border.setStroke(1, -0x1000000) //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                lineChild.setBackgroundDrawable(border)
            } else {
                lineChild.background = border
            }
            lineChild.setPadding(5,5,5,5)

            val text2 = TextView(this)
            text2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            text2.textSize = 12F
            text2.gravity = Gravity.CENTER
            text2.text = s[0].toString()+s[1]+s[2]
            lineChild.addView(text2)
            lineParentInit.addView(lineChild)
        }

        line_parent.addView(lineParentInit)

        arr2Day.forEachIndexed { index, mutableList ->
            val lineParent = LinearLayout(this)
            lineParent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lineParent.orientation = LinearLayout.HORIZONTAL

            mutableList.forEach {
                calendar.time = it
                val lineChild = LinearLayout(this)
                val params = LinearLayout.LayoutParams(75, 75)
                params.setMargins(2,2,2,2)
                lineChild.layoutParams = params
                lineChild.orientation = LinearLayout.VERTICAL
                lineChild.gravity = Gravity.CENTER_VERTICAL
                if (it.getMM() == listMonth.indexOf(sp_month.selectedItem.toString())+1) {
                    val border = GradientDrawable()
                    border.setColor(-0x1) //white background
                    border.setStroke(1, -0x1000000) //black border with full opacity
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        lineChild.setBackgroundDrawable(border)
                    } else {
                        lineChild.background = border
                    }
                }
                lineChild.setPadding(5,5,5,5)

                val text2 = TextView(this)
                text2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
                text2.textSize = 12F
                text2.gravity = Gravity.CENTER
                text2.text = it.getDD().toString()
                lineChild.addView(text2)
                lineParent.addView(lineChild)
            }

            line_parent.addView(lineParent)
        }
    }

    private fun addDateEnd(length: Int, month: Int): MutableList<Date> {
        val arrDay = arrayListOf<Date>()
        val calendar = Calendar.getInstance()
        val dateString = "01-"+(month)+"-"+et_year.text.toString()
        calendar.time = dateString.simpleToDate()

        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val dateMax = calendar.time

        var i = dateMax.getDD()

        while (i > dateMax.getDD()-length) {
            val dateToAdd = i.toString()+"-"+(month)+"-"+et_year.text.toString()
            calendar.time = dateToAdd.simpleToDate()
            arrDay.add(calendar.time)
            i--
        }

        return arrDay
    }

    private fun addDateStart(length: Int, month: Int): MutableList<Date> {
        val arrDay = arrayListOf<Date>()
        val calendar = Calendar.getInstance()
        val dateString = "01-"+(month)+"-"+et_year.text.toString()
        calendar.time = dateString.simpleToDate()

        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        val dateMin = calendar.time

        var i = dateMin.getDD()

        while (i < dateMin.getDD()+length) {
            val dateToAdd = i.toString()+"-"+(month)+"-"+et_year.text.toString()
            calendar.time = dateToAdd.simpleToDate()
            arrDay.add(calendar.time)
            i++
        }

        return arrDay
    }

    private fun String.simpleToDate(): Date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(this)
    private fun Date.getDD(): Int = SimpleDateFormat("dd", Locale.getDefault()).format(this).toInt()
    private fun Date.getMM(): Int = SimpleDateFormat("MM", Locale.getDefault()).format(this).toInt()
}
