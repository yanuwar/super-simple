package com.yanuwar.entertaiment

import android.app.Activity
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            val borderInit = GradientDrawable()
            borderInit.setColor(-0x1) //white background
            borderInit.setStroke(1, -0x1000000) //black border with full opacity
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                lineInit.setBackgroundDrawable(borderInit)
            } else {
                lineInit.background = borderInit
            }
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
                resetStock()
                resetBattleField()
                playerTurn = 2
                orientation = 0
                type = 5
                setTitleTop(playerTurn)
            } else {
                btn_patrol.visibility = View.GONE
                btn_submarine.visibility = View.GONE
                btn_destroyer.visibility = View.GONE
                btn_battleship.visibility = View.GONE
                btn_carrier.visibility = View.GONE
                resetBattleField()
                resetStock()
                addingEventBom()
                btn_ganti.visibility = View.VISIBLE
                btn_next.visibility = View.GONE
            }
        }
        btn_ganti.setOnClickListener {
            resetBattleField()
            playerTurn = if (playerTurn == 1) 2 else 1
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
        e("cihuy", x.toString() +", "+y.toString())
        if (playerTurn == 1) {
            e("cihuy", x.toString() +", "+y.toString())
            if ((x + tempStokShipP1[type].quota - 1 < 10 && orientation == 1) || (y + tempStokShipP1[type].quota - 1 < 10 && orientation == 2)) {
                if (tempStokShipP1[type].stock > 0) {
                    var isValid = true
                    tempInitShipP1.mapIndexed { index, itemShip ->

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
//                var i = 0
//                while (i < tempStokShipP1[type].quota) {
//
//
//                    }
//                    i++
//                }
                    tempInitShipP2.add(ItemShip(id = type,
                            stock = 0, name = tempStokShipP1[type].name,
                            quota = tempStokShipP1[type].quota, x = x, y = y, orientation = orientation))
                    tempStokShipP1[type] = (ItemShip(id = type,
                            stock = 0, name = tempStokShipP1[type].name,
                            quota = tempStokShipP1[type].quota))
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
                        afterAddBomP1(indexX, indexY)
                    }
                }
            }
        } else {
            arrLineParentP2.mapIndexed { indexY, mutableList ->
                mutableList!!.mapIndexed { indexX, linearLayout ->
                    linearLayout.setOnClickListener {
                        afterAddBomP2(indexX, indexY)
                    }
                }
            }
        }
    }

    private fun afterAddBomP1(x: Int, y: Int) {
        bomP1.add(Coor(x, y))
        bomP1.map {
            arrLineTextShipP1[it.y]!![it.x].text = "O"
        }
    }

    private fun afterAddBomP2(x: Int, y: Int) {
        bomP2.add(Coor(x, y))
        bomP2.map {
            arrLineTextShipP1[it.y]!![it.x].text = "O"
        }
    }

    private fun resetStock() {
        tempStokShipP1.mapIndexed { index, itemShip ->
            tempStokShipP1[index] = ItemShip(itemShip.id, 1, itemShip.name, itemShip.quota)
        }
    }

    private fun setTitleTop(playerTurn: Int) {
        tv_top.text = "Creat Battlefield Player $playerTurn"
        if (playerTurn == 1) btn_next.text = "Lanjut Ke Player 2"
        else if (playerTurn == 2) btn_next.text = "Mainkan"
    }
}
