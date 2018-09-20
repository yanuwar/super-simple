package com.yanuwar.entertaiment

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Log.e
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_battle_ship.*
import kotlinx.android.synthetic.main.activity_pingsut.*

class BattleShipActivity : AppCompatActivity() {

    private var arrLineParentP1 = arrayOfNulls<MutableList<LinearLayout>>(10)
    private var arrLineTextShipP1 = arrayOfNulls<MutableList<TextView>>(10)
    private var arrLineParentP2 = arrayOfNulls<MutableList<LinearLayout>>(10)
    private var arrLineTextShipP2 = arrayOfNulls<MutableList<TextView>>(10)
    private var tempStokShipP1 = arrayListOf<ItemShip>()
    private var tempInitShipP1 = arrayListOf<ItemShip>()
    private var tempInitShipP2 = arrayListOf<ItemShip>()
    private var orientation = 0
    private var type = 5
    private val bomP1 = arrayListOf<Coor>()
    private val bomP2 = arrayListOf<Coor>()
    private var playerTurn = 1
    private var isSalvoMode = false
    private var salvoCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_ship)

        tempStokShipP1.add(ItemShip(id = 1, stock = 1, quota = 5, name = "Currier"))
        tempStokShipP1.add(ItemShip(id = 2, stock = 1, quota = 4, name = "Battleship"))
        tempStokShipP1.add(ItemShip(id = 3, stock = 1, quota = 3, name = "Destroyer"))
        tempStokShipP1.add(ItemShip(id = 4, stock = 1, quota = 3, name = "Submarine"))
        tempStokShipP1.add(ItemShip(id = 5, stock = 1, quota = 2, name = "Patrol Boat"))

        var y = 0
        var x = 0
        val arrHurf = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")
        val arrAngka = arrayOf(0, 1, 2, 3 ,4, 5, 6, 7, 8, 9, 10)

        x = 0
        val lineParentInit = LinearLayout(this)
        lineParentInit.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lineParentInit.orientation = LinearLayout.HORIZONTAL
        while (x < arrAngka.size) {

            val lineChild = LinearLayout(this)
            val params = LinearLayout.LayoutParams(60, 60)
            params.setMargins(2,2,2,2)
            lineChild.layoutParams = params
            lineChild.orientation = LinearLayout.VERTICAL
            lineChild.gravity = Gravity.CENTER_VERTICAL
            if (x == 10) {
                lineChild.setOnClickListener {
                    isSalvoMode = true
                    salvoCount = 0
                    Toast.makeText(this, "Salvo Mode On", Toast.LENGTH_SHORT).show()
                }
            }
//            val border = GradientDrawable()
//            border.setColor(-0x1) //white background
//            border.setStroke(1, -0x1000000) //black border with full opacity
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                lineChild.setBackgroundDrawable(border)
//            } else {
//                lineChild.background = border
//            }
            lineChild.setPadding(5,5,5,5)

            val text2 = TextView(this)
            text2.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            text2.textSize = 12F
            text2.text = if (x > 0) arrAngka[x].toString() else ""
            text2.gravity = Gravity.CENTER
            lineChild.addView(text2)

            lineParentInit.addView(lineChild)

            x++
        }
        line_parent.addView(lineParentInit)

        while (y < 10) {
            val lineParent = LinearLayout(this)
            lineParent.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lineParent.orientation = LinearLayout.HORIZONTAL

            val arrLineParent = arrayListOf<LinearLayout>()
            val arrTextShip = arrayListOf<TextView>()

            val lineInit = LinearLayout(this)
            val params = LinearLayout.LayoutParams(60, 60)
            params.setMargins(2,2,2,2)
            lineInit.layoutParams = params
            lineInit.orientation = LinearLayout.VERTICAL
            lineInit.gravity = Gravity.CENTER_VERTICAL
//            val borderInit = GradientDrawable()
//            borderInit.setColor(-0x1) //white background
//            borderInit.setStroke(1, -0x1000000) //black border with full opacity
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                lineInit.setBackgroundDrawable(borderInit)
//            } else {
//                lineInit.background = borderInit
//            }
            lineInit.setPadding(5,5,5,5)

            val textInit = TextView(this)
            textInit.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            textInit.textSize = 12F
            textInit.text = arrHurf[y]
            textInit.gravity = Gravity.CENTER
            lineInit.addView(textInit)

            lineParent.addView(lineInit)

            x = 0
            while (x < 10) {

                val lineChild = LinearLayout(this)
                val params = LinearLayout.LayoutParams(60, 60)
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
                lineChild.addView(text2)
                arrTextShip.add(text2)

                arrLineParent.add(lineChild)
                lineParent.addView(lineChild)

                x++
            }

            arrLineParentP1[y] = arrLineParent
            arrLineParentP2[y] = arrLineParent
            arrLineTextShipP1[y] = arrTextShip
            arrLineTextShipP2[y] = arrTextShip

            line_parent?.addView(lineParent)
            y++
        }

        setTitleTop(playerTurn)

        btn_carrier.setOnClickListener {
            if (playerTurn == 2) {
                if (tempStokShipP1[0].stock > 0) {
                    showDialogStockCard(activity = this, type = 0, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (tempStokShipP1[0].stock > 0) {
                    showDialogStockCard(activity = this, type = 0, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_battleship.setOnClickListener {
            if (playerTurn == 1) {
                if (tempStokShipP1[1].stock > 0) {
                    showDialogStockCard(activity = this, type = 1, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (tempStokShipP1[1].stock > 0) {
                    showDialogStockCard(activity = this, type = 1, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_destroyer.setOnClickListener {
            if (playerTurn == 1) {
                if (tempStokShipP1[2].stock > 0) {
                    showDialogStockCard(activity = this, type = 2, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (tempStokShipP1[2].stock > 0) {
                    showDialogStockCard(activity = this, type = 2, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_submarine.setOnClickListener {
            if (playerTurn == 2) {
                if (tempStokShipP1[3].stock > 0) {
                    showDialogStockCard(activity = this, type = 3, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (tempStokShipP1[3].stock > 0) {
                    showDialogStockCard(activity = this, type = 3, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_patrol.setOnClickListener {
            if (playerTurn == 1) {
                if (tempStokShipP1[4].stock > 0) {
                    showDialogStockCard(activity = this, type = 4, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (tempStokShipP1[4].stock > 0) {
                    showDialogStockCard(activity = this, type = 4, message = "pilih horisontal/vertikal", player = playerTurn)
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn_next.setOnClickListener {
            if (playerTurn == 1) {
                if (tempInitShipP1.size < 5) {
                    Toast.makeText(this, "Battlefield Player 1 masih kurang", Toast.LENGTH_SHORT).show()
                } else {
                    resetStock()
                    resetBattleField()
                    playerTurn = 2
                    orientation = 0
                    type = 5
                    setTitleTop(playerTurn)
                }
            } else {
                if (tempInitShipP2.size < 5) {
                    Toast.makeText(this, "Battlefield Player 2 masih kurang", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                btn_patrol.visibility = View.GONE
                btn_submarine.visibility = View.GONE
                btn_destroyer.visibility = View.GONE
                btn_battleship.visibility = View.GONE
                btn_carrier.visibility = View.GONE
                playerTurn = 1
                tv_top.text = "Giliran player $playerTurn menembak"
                resetBattleField()
                resetStock()
                addingEventBom()
//                btn_ganti.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
            }
        }
        btn_ganti.setOnClickListener {
            resetBattleField()
            if (isWin(if (playerTurn == 1) tempInitShipP1 else tempInitShipP2)) {
                Toast.makeText(this, "Player $playerTurn menang", Toast.LENGTH_SHORT).show()
                tv_keterangan.text = "Player $playerTurn menang"
                tv_top.text = "Player $playerTurn menang"
            } else {
                playerTurn = if (playerTurn == 1) {
                    Toast.makeText(this, "Giliran player 2 menembak", Toast.LENGTH_SHORT).show()
                    tv_top.text = "Giliran player 2 menembak"
                    2
                } else {
                    Toast.makeText(this, "Giliran player 1 menembak", Toast.LENGTH_SHORT).show()
                    tv_top.text = "Giliran player 1 menembak"
                    1
                }
                renderBom()
                addingEventBom()
            }
        }

        tv_top.setOnClickListener {
            setCheat()
        }
    }

    private fun runGantiPlayer() {
        Toast.makeText(this, "Tunggu Giliran " + if (playerTurn == 1) 2 else 1, Toast.LENGTH_SHORT).show()
        Handler().postDelayed(
                {
                    runOnUiThread {
                        btn_ganti.performClick()
                    }
                },
                2500)
    }

    private fun setCheat() {
        resetBattleField()
        var i: Int
        if (playerTurn == 2) {
            tempInitShipP1.forEachIndexed { index, itemShip ->
                if (itemShip.orientation == 1) {
                    i = itemShip.x?:0
                    while (i < (itemShip.x?:0)+itemShip.quota) {
                        arrLineTextShipP1[itemShip.y?:0]!![i].text = itemShip.initial
                        i++
                    }
                } else {
                    i = itemShip.y?:0
                    while (i < (itemShip.y?:0)+itemShip.quota) {
                        arrLineTextShipP1[i]!![itemShip.x?:0].text = itemShip.initial
                        i++
                    }
                }
            }
        } else {
            tempInitShipP2.forEachIndexed { index, itemShip ->
                if (itemShip.orientation == 1) {
                    i = itemShip.x?:0
                    while (i < (itemShip.x?:0)+itemShip.quota) {
                        arrLineTextShipP2[itemShip.y?:0]!![i].text = itemShip.initial
                        i++
                    }
                } else {
                    i = itemShip.y?:0
                    while (i < (itemShip.y?:0)+itemShip.quota) {
                        arrLineTextShipP2[i]!![itemShip.x?:0].text = itemShip.initial
                        i++
                    }
                }
            }
        }
    }

    private fun showDialogStockCard(activity: Activity, title: String? = null, message: CharSequence, type: Int, player: Int) {
        val builder = AlertDialog.Builder(activity)

        if (title != null) builder.setTitle(title)

        builder.setMessage(message)
        builder.setPositiveButton("Horizontal") { dialog, id ->
            addingEventButton()
            this.type = type; this.orientation = 1
            dialog.dismiss() }
        builder.setNegativeButton("Vertical") { dialog, id ->
            addingEventButton()
            this.type = type; this.orientation = 2
            dialog.dismiss() }
        builder.show()
    }

    private fun initShipCurrier(x: Int, y: Int) {
        if (playerTurn == 1) {
            if ((x + tempStokShipP1[type].quota - 1 < 10 && orientation == 1) || (y + tempStokShipP1[type].quota - 1 < 10 && orientation == 2)) {
                if (tempStokShipP1[type].stock > 0) {
                    var isValid = true
                    var i: Int
                    var j: Int
                    tempInitShipP1.mapIndexed { index, itemShip ->
                        if (itemShip.orientation == 1) {
                            i = itemShip.x?:0
                            while (i < itemShip.quota+(itemShip.x?:0)) {
                                if (orientation == 1) {
                                    j = x
                                    while (j < tempStokShipP1[type].quota+x) {
                                        if (j == i && y == itemShip.y) { isValid = false; break; }
                                        j++
                                    }
                                } else {
                                    j = y
                                    while (j < tempStokShipP1[type].quota+y) {
                                        if (i == x && j == itemShip.y) { isValid = false; break; }
                                        j++
                                    }
                                }
                                if (!isValid) {
                                    break
                                }
                                i++
                            }
                        } else {
                            i = itemShip.y?:0
                            while (i < itemShip.quota+(itemShip.y?:0)) {
                                if (orientation == 1) {
                                    j = x
                                    while (j < tempStokShipP1[type].quota+x) {
                                        if (j == itemShip.x && y == i) { isValid = false; break; }
                                        j++
                                    }
                                } else {
                                    j = y
                                    while (j < tempStokShipP1[type].quota+y) {
                                        if (itemShip.x == x && j == i) { isValid = false; break; }
                                        j++
                                    }
                                }
                                if (!isValid) {
                                    break
                                }
                                i++
                            }
                        }
                    }

                    if (isValid) {
                        tempInitShipP1.add(ItemShip(id = type,
                                stock = 0, name = tempStokShipP1[type].name,
                                quota = tempStokShipP1[type].quota, x = x, y = y, orientation = orientation))
                        tempStokShipP1[type] = (ItemShip(id = type,
                                stock = 0, name = tempStokShipP1[type].name,
                                quota = tempStokShipP1[type].quota))
                    } else {
                        Toast.makeText(this, "Tabrakan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Out Of BattleField", Toast.LENGTH_SHORT).show()
            }
        } else {
            if ((x + tempStokShipP1[type].quota - 1 < 10 && orientation == 1) || (y + tempStokShipP1[type].quota - 1 < 10 && orientation == 2)) {
                if (tempStokShipP1[type].stock > 0) {
                    var isValid = true
                    var i: Int
                    var j: Int
                    tempInitShipP2.mapIndexed { index, itemShip ->
                        if (itemShip.orientation == 1) {
                            i = itemShip.x?:0
                            while (i < itemShip.quota+(itemShip.x?:0)) {
                                if (orientation == 1) {
                                    j = x
                                    while (j < tempStokShipP1[type].quota+x) {
                                        if (j == i && y == itemShip.y) { isValid = false; break; }
                                        j++
                                    }
                                } else {
                                    j = y
                                    while (j < tempStokShipP1[type].quota+y) {
                                        if (i == x && j == itemShip.y) { isValid = false; break; }
                                        j++
                                    }
                                }
                                if (!isValid) {
                                    break
                                }
                                i++
                            }
                        } else {
                            i = itemShip.y?:0
                            while (i < itemShip.quota+(itemShip.y?:0)) {
                                if (orientation == 1) {
                                    j = x
                                    while (j < tempStokShipP1[type].quota+x) {
                                        if (j == itemShip.x && y == i) { isValid = false; break; }
                                        j++
                                    }
                                } else {
                                    j = y
                                    while (j < tempStokShipP1[type].quota+y) {
                                        if (itemShip.x == x && j == i) { isValid = false; break; }
                                        j++
                                    }
                                }
                                if (!isValid) {
                                    break
                                }
                                i++
                            }
                        }
                    }

                    if (isValid) {
                        tempInitShipP2.add(ItemShip(id = type,
                                stock = 0, name = tempStokShipP1[type].name,
                                quota = tempStokShipP1[type].quota, x = x, y = y, orientation = orientation))
                        tempStokShipP1[type] = (ItemShip(id = type,
                                stock = 0, name = tempStokShipP1[type].name,
                                quota = tempStokShipP1[type].quota))
                    } else {
                        Toast.makeText(this, "Tabrakan", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Kapal sudah terpasang", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Out Of BattleField", Toast.LENGTH_SHORT).show()
            }
        }

        renderKapal()
        disableButton()
    }

    private fun disableButton() {
        if (playerTurn == 1) {
            arrLineParentP1.mapIndexed { index, mutableList ->
                mutableList!!.map {
                    it.setOnClickListener {  }
                }
            }
        } else{
            arrLineParentP2.mapIndexed { index, mutableList ->
                mutableList!!.map {
                    it.setOnClickListener {  }
                }
            }
        }
    }

    private fun addingEventButton() {
        if (playerTurn == 1) {
            arrLineParentP1.mapIndexed { indexY, mutableList ->
                mutableList!!.mapIndexed { indexX, linearLayout ->
                    linearLayout.setOnClickListener {
                        initShipCurrier(indexX, indexY)
                    }
                }
            }
        } else {
            arrLineParentP2.mapIndexed { indexY, mutableList ->
                mutableList!!.mapIndexed { indexX, linearLayout ->
                    linearLayout.setOnClickListener {
                        initShipCurrier(indexX, indexY)
                    }
                }
            }
        }
    }

    private fun renderKapal() {
        if (playerTurn == 1) {
            tempInitShipP1.mapIndexed { index, itemShip ->
                if (itemShip.orientation == 1) {
                    var i = itemShip.x?:0
                    while (i < (itemShip.x?:0)+itemShip.quota) {
                        arrLineTextShipP1[itemShip.y ?: 0]!![i].text = itemShip.initial
                        i++
                    }
                } else {
                    var i = itemShip.y?:0
                    while (i < (itemShip.y?:0)+itemShip.quota) {
                        arrLineTextShipP1[i]!![itemShip.x?:0].text = itemShip.initial
                        i++
                    }
                }
            }
        } else {
            tempInitShipP2.mapIndexed { index, itemShip ->
                if (itemShip.orientation == 1) {
                    var i = itemShip.x?:0
                    while (i < (itemShip.x?:0)+itemShip.quota) {
                        arrLineTextShipP2[itemShip.y ?: 0]!![i].text = itemShip.initial
                        i++
                    }
                } else {
                    var i = itemShip.y?:0
                    while (i < (itemShip.y?:0)+itemShip.quota) {
                        arrLineTextShipP2[i]!![itemShip.x?:0].text = itemShip.initial
                        i++
                    }
                }
            }
        }
    }

    private fun resetBattleField() {
        tv_keterangan.text = ""
        if (playerTurn == 1) {
            arrLineTextShipP1.mapIndexed { index, mutableList ->
                mutableList!!.map {
                    it.text = ""
                }
            }
        } else {
            arrLineTextShipP2.mapIndexed { index, mutableList ->
                mutableList!!.map {
                    it.text = ""
                }
            }
        }
    }

    private fun addingEventBom() {
        if (playerTurn == 1) {
            arrLineParentP1.mapIndexed { indexY, mutableList ->
                mutableList!!.mapIndexed { indexX, linearLayout ->
                    linearLayout.setOnClickListener {
                        var isValid = true

                        bomP1.forEachIndexed { index, coor ->
                            if (indexX == coor.x && coor.y == indexY) {
                                isValid = false
                            }
                        }

                        if (isValid) {
                            afterAddBomP1(indexX, indexY)
                        } else {
                            Toast.makeText(this, "Coordinat sudah terpakai", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            arrLineParentP2.mapIndexed { indexY, mutableList ->
                mutableList!!.mapIndexed { indexX, linearLayout ->
                    linearLayout.setOnClickListener {
                        var isValid = true

                        bomP2.forEachIndexed { index, coor ->
                            if (indexX == coor.x && coor.y == indexY) {
                                isValid = false
                            }
                        }

                        if (isValid) {
                            afterAddBomP2(indexX, indexY)
                        } else {
                            Toast.makeText(this, "Coordinat sudah terpakai", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun afterAddBomP1(x: Int, y: Int) {
        bomP1.add(Coor(x, y))
        renderBom()
        if (isSalvoMode) {
            if (salvoCount < 4) salvoCount++ else {
                isSalvoMode = false
                Toast.makeText(this, "Salvo Mode is Done", Toast.LENGTH_SHORT).show()
                disableButton()
                runGantiPlayer()
            }
        } else {
            disableButton()
            runGantiPlayer()
        }
//        btn_ganti.performClick()
    }

    private fun afterAddBomP2(x: Int, y: Int) {
        bomP2.add(Coor(x, y))
        renderBom()
        if (isSalvoMode) {
            if (salvoCount < 4) salvoCount++ else {
                isSalvoMode = false
                Toast.makeText(this, "Salvo Mode is Done", Toast.LENGTH_SHORT).show()
                disableButton()
                runGantiPlayer()
            }
        } else {
            disableButton()
            runGantiPlayer()
        }
//        btn_ganti.performClick()
    }

    private fun renderBom() {
        var i: Int
        var isShip = false
        var indexItem: Int = 0

        if (playerTurn == 1) {
            bomP1.map {
                isShip = false
                tempInitShipP2.forEachIndexed { index, itemShip ->
                    if (itemShip.orientation == 1) {
                        i = itemShip.x?:0
                        while (i < (itemShip.x?:0)+itemShip.quota) {
                            if (i == it.x && (itemShip.y?:0) == it.y) {
                                indexItem = index
                                tv_keterangan.text = "Kapal ${tempInitShipP2[indexItem].name} milik player 2 kena"
                                isShip = true
                                break
                            }
                            i++
                        }
                    } else {
                        i = itemShip.y?:0
                        while (i < (itemShip.y?:0)+itemShip.quota) {
                            if ((itemShip.x?:0) == it.x && i == it.y) {
                                indexItem = index
                                tv_keterangan.text = "Kapal ${tempInitShipP2[indexItem].name} milik player 2 kena"
                                isShip = true
                                break
                            }
                            i++
                        }
                    }
                }
                if (isShip) {
                    arrLineTextShipP1[it.y]!![it.x].text = "X"
                    checkIsWin()
                    if (tempInitShipP2[indexItem].isDestroy == true) {
                        tv_keterangan.text = "Kapal ${tempInitShipP2[indexItem].name} milik player 2 hancur"
                    }
                } else {
                    arrLineTextShipP1[it.y]!![it.x].text = "O"
                }
            }
        } else {
            bomP2.map {
                isShip = false
                tempInitShipP1.forEachIndexed { index, itemShip ->
                    if (itemShip.orientation == 1) {
                        i = itemShip.x?:0
                        while (i < (itemShip.x?:0)+itemShip.quota) {
                            if (i == it.x && (itemShip.y?:0) == it.y) {
                                indexItem = index
                                tv_keterangan.text = "Kapal ${tempInitShipP1[indexItem].name} milik player 1 kena"
                                isShip = true
                                break
                            }
                            i++
                        }
                    } else {
                        i = itemShip.y ?: 0
                        while (i < (itemShip.y ?: 0) + itemShip.quota) {
                            if ((itemShip.x ?: 0) == it.x && i == it.y) {
                                indexItem = index
                                tv_keterangan.text = "Kapal ${tempInitShipP1[indexItem].name} milik player 1 kena"
                                isShip = true
                                break
                            }
                            i++
                        }
                    }
                }
                if (isShip) {
                    arrLineTextShipP2[it.y]!![it.x].text = "X"
                    checkIsWin()
                    if (tempInitShipP1[indexItem].isDestroy == true) {
                        tv_keterangan.text = "Kapal ${tempInitShipP1[indexItem].name} milik player 1 hancur"
                    }
                } else {
                    arrLineTextShipP2[it.y]!![it.x].text = "O"
                }
            }
        }
    }

    private fun checkIsWin() {
        var i: Int
        var isWinn = true
        tempInitShipP2.forEachIndexed { index, itemShip ->
            isWinn = true
            if (itemShip.orientation == 1) {
                i = itemShip.x?:0
                while (i < (itemShip.x?:0)+itemShip.quota) {
                    if (arrLineTextShipP2[itemShip.y?:0]!![i].text != "X") {
                        isWinn = false
                        break
                    }
                    i++
                }
            } else {
                i = itemShip.y?:0
                while (i < (itemShip.y?:0)+itemShip.quota) {
                    if (arrLineTextShipP2[i]!![itemShip.x?:0].text != "X") {
                        isWinn = false
                        break
                    }
                    i++
                }
            }
            tempInitShipP2[index].isDestroy = isWinn
        }
        tempInitShipP1.forEachIndexed { index, itemShip ->
            if (itemShip.orientation == 1) {
                i = itemShip.x?:0
                while (i < (itemShip.x?:0)+itemShip.quota) {
                    if (arrLineTextShipP1[itemShip.y?:0]!![i].text != "X") {
                        isWinn = false
                        break
                    }
                    i++
                }
            } else {
                i = itemShip.y ?: 0
                while (i < (itemShip.y ?: 0) + itemShip.quota) {
                    if (arrLineTextShipP1[i]!![itemShip.x?:0].text != "X") {
                        isWinn = false
                        break
                    }
                    i++
                }
            }
            tempInitShipP1[index].isDestroy = isWinn
        }
    }

    private fun isWin(list: MutableList<ItemShip>): Boolean {
        var isWin = true

        list.map {
            e("cihuy", it.name+it.isDestroy)
            if (it.isDestroy == false) {
                isWin = false
            }
        }

        return isWin
    }

    private fun resetStock() {
        tempStokShipP1.mapIndexed { index, itemShip ->
            tempStokShipP1[index] = ItemShip(itemShip.id, 1, itemShip.name, itemShip.quota)
        }
    }

    private fun setTitleTop(playerTurn: Int) {
        tv_top.text = "Create Battlefield Player $playerTurn"
        if (playerTurn == 1) btn_next.text = "Lanjut Ke Player 2"
        else if (playerTurn == 2) btn_next.text = "Mainkan"
    }
}
