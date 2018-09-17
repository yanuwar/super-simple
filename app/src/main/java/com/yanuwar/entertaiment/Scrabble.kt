package com.yanuwar.entertaiment

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log.e
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_scrabble.*
import java.util.*
import android.text.InputType
import android.widget.EditText

class Scrabble : AppCompatActivity() {

    private lateinit var random: Random
    private val listCard: MutableList<Letter> = arrayListOf()
    private val tempCardOnHandPlayer1: MutableList<Letter> = arrayListOf()
    private val tempAnsPlayer1: MutableList<Coor> = arrayListOf()
    private val stockPlayer1: MutableList<Letter> = arrayListOf()
    private val tempAnsPlayer2: MutableList<Coor> = arrayListOf()
    private val tempCardOnHandPlayer2: MutableList<Letter> = arrayListOf()
    private val stockPlayer2: MutableList<Letter> = arrayListOf()
    private var playerTurn = 0
    private var arrLineButton = arrayOfNulls<MutableList<Button>>(15)
    private var arrButton = arrayOfNulls<Button>(15)
    private var orientation = 0
    private var tempCoor: MutableList<Coor> = arrayListOf()
    private var isDoubleSkip = 0
    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrabble)

        playerTurn = 1

        listCard.add(Letter("-", 0, 2))
        listCard.add(Letter("A", 1, 19))
        listCard.add(Letter("B", 5, 4))
        listCard.add(Letter("C", 8, 3))
        listCard.add(Letter("D", 3, 4))
        listCard.add(Letter("E", 1, 8))
        listCard.add(Letter("F", 5, 1))
        listCard.add(Letter("G", 3, 3))
        listCard.add(Letter("H", 4, 2))
        listCard.add(Letter("I", 1, 8))
        listCard.add(Letter("J", 10, 1))
        listCard.add(Letter("K", 2, 3))
        listCard.add(Letter("L", 4, 3))
        listCard.add(Letter("M", 2, 3))
        listCard.add(Letter("N", 1, 9))
        listCard.add(Letter("O", 1, 3))
        listCard.add(Letter("P", 4, 2))
        listCard.add(Letter("R", 1, 4))
        listCard.add(Letter("S", 1, 3))
        listCard.add(Letter("T", 1, 5))
        listCard.add(Letter("U", 1, 5))
        listCard.add(Letter("V", 8, 1))
        listCard.add(Letter("W", 5, 1))
        listCard.add(Letter("Y", 5, 2))
        listCard.add(Letter("Z", 10, 1))

        stockPlayer1.addAll(listCard)
        stockPlayer2.addAll(listCard)

        tempCardOnHandPlayer1.addAll(getCardOnHandP1())
        tempCardOnHandPlayer2.addAll(getCardOnHandP2())

        var btnIndex = 0
        val lineFirst = LinearLayout(this)
        lineFirst.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        var i = 0
        while (i < 16) {
            val btnFirst = Button(this)
            btnFirst.layoutParams = LinearLayout.LayoutParams(70, 70)
            btnFirst.text = (i).toString()
            btnFirst.textSize = 12F
            lineFirst.addView(btnFirst)
            i++
        }
        line_parent?.addView(lineFirst)


        arrLineButton.mapIndexed { indexLineButton, lineButton ->
            val line = LinearLayout(this)
            line.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            val btnFirst = Button(this)
            btnFirst.layoutParams = LinearLayout.LayoutParams(70, 70)
            btnFirst.text = (indexLineButton + 1).toString()
            btnFirst.textSize = 12F
            line.addView(btnFirst)

            val arrayButton: MutableList<Button> = arrayListOf()
            arrButton.mapIndexed { index, button ->
                val btn = Button(this)
                btn.layoutParams = LinearLayout.LayoutParams(70, 70)
                btn.id = btnIndex; btnIndex++
                btn.textSize = 12F
                btn.setOnClickListener {
                    val mIntent = Intent(this, DetailScrabble::class.java)
                    mIntent.putExtra("arrStockCard", if (playerTurn == 1) getArrayHuruf(tempCardOnHandPlayer1) else getArrayHuruf(tempCardOnHandPlayer2))
                    mIntent.putExtra("indexBtn", index)
                    mIntent.putExtra("indexLine", indexLineButton)
                    mIntent.putExtra("turn", playerTurn)
                    startActivityForResult(mIntent, 1002)
                }
                line.addView(btn)
                arrayButton.add(btn)
            }
            line_parent?.addView(line)
            arrLineButton[indexLineButton] = arrayButton
        }

        btn_done.setOnClickListener {
            tempCoor.clear()
            if (playerTurn == 1) {
                tempAnsPlayer1.map {
                    if (it.x == 7 && it.y == 7) {
                        isFirst = false
                    }
                }
            } else {
                tempAnsPlayer2.map {
                    if (it.x == 7 && it.y == 7) {
                        isFirst = false
                    }
                }
            }
            if (!isFirst) {
                if (tempAnsPlayer1.size > 0 || tempAnsPlayer2.size > 0) {
                    if (tempAnsPlayer1.size > 1 && playerTurn == 1) {
                        orientation = getOrientation(tempAnsPlayer1)
                        val kata: MutableList<String> = arrayListOf()
                        var poin = 0
                        if (orientation == 1) {
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (!kata.contains(getKataCoor(tempAnsPlayer1[index],1))) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],1), tempCoor); kata.add(getKataCoor(tempAnsPlayer1[index],1))
                                }
                            }
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer1[index], 2).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],2), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer1[index], 2))
                                }
                            }
                        } else if (orientation == 2) {
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (!kata.contains(getKataCoor(tempAnsPlayer1[index], 2))) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],2), tempCoor);
                                    kata.add(getKataCoor(tempAnsPlayer1[index], 2))
                                }
                            }
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer1[index], 1).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],1), tempCoor);
                                    kata.add(getKataCoor(tempAnsPlayer1[index], 1))
                                }
                            }
                        }
                        showDialogDone(activity = this, message = "Yakin kata ${kata.map { it }} sudah tepat", poin = poin)
                    } else if (tempAnsPlayer2.size > 1 && playerTurn == 2) {
                        orientation = getOrientation(tempAnsPlayer2)
                        val kata: MutableList<String> = arrayListOf()
                        var poin = 0
                        if (orientation == 1) {
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (!kata.contains(getKataCoor(tempAnsPlayer2[index], 1))) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],1), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 1))
                                }
                            }
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer2[index], 2).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],2), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 2))
                                }
                            }
                        } else if (orientation == 2) {
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (!kata.contains(getKataCoor(tempAnsPlayer2[index], 2))) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],2), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 2))
                                }
                            }
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer2[index], 1).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],1), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 1))
                                }
                            }
                        }
                        showDialogDone(activity = this, message = "Yakin kata ${kata.map { it }} sudah tepat", poin = poin)
                    } else {
                        val kata: MutableList<String> = arrayListOf()
                        var poin = 0
                        if (playerTurn == 1) {
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer1[index], 1).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],1), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer1[index], 1))
                                }
                            }
                            tempAnsPlayer1.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer1[index], 2).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer1[index],2), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer1[index], 2))
                                }
                            }
                        } else if (playerTurn == 2){
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer2[index], 2).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],2), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 2))
                                }
                            }
                            tempAnsPlayer2.forEachIndexed { index, coor ->
                                if (getKataCoor(tempAnsPlayer2[index], 1).length > 1) {
                                    poin += getValue(getKataCoorTag(tempAnsPlayer2[index],1), tempCoor)
                                    kata.add(getKataCoor(tempAnsPlayer2[index], 1))
                                }
                            }
                        }

                        showDialogDone(activity = this, message = "Yakin kata ${kata.map { it }} sudah tepat", poin = poin)
                    }
                } else {
                    Toast.makeText(this, "pikir baik2", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Wajib mengisi tengah", Toast.LENGTH_SHORT).show()
            }
        }

        btn_skip.setOnClickListener {
            showDialogSkip(activity = this, message = "Lewati giliranmu?")
        }

        btn_stock_card.setOnClickListener {
            var stockP1 = "Stok Huruf Player 1: \n"
            stockPlayer1.map {
                stockP1 += it.huruf +" | "+it.jumlah+"\n"
            }
            var stockP2 = "Stok Huruf Player 2: \n"
            stockPlayer2.map {
                stockP2 += it.huruf +" | "+it.jumlah+"\n"
            }

            showDialogStockCard(activity = this, message = if (playerTurn == 1) stockP1 else stockP2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.extras?.getInt("turn") == 1) {
            when {
                data.extras.getBoolean("isDelete", false) -> {
                    if (arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text.isNotEmpty()) {
                        var position = 0
                        tempCardOnHandPlayer1.mapIndexed { index, letter ->
                            if (arrLineButton[data.extras?.getInt("indexLine")
                                            ?: 0]!![data.extras?.getInt("indexBtn")
                                            ?: 0].text == letter.huruf
                                    && letter.isShow == true) {
                                position = index
                            }
                        }

                        tempCardOnHandPlayer1[position] =
                                Letter(tempCardOnHandPlayer1[position].huruf,
                                        tempCardOnHandPlayer1[position].poin, tempCardOnHandPlayer1[position].jumlah, false)

                        var positionAns = 0
                        tempAnsPlayer1.mapIndexed { index, coor ->
                            if (coor.x == data.extras?.getInt("indexBtn") ?: 0 && data.extras?.getInt("indexLine") ?: 0 == coor.y) {
                                positionAns = index
                            }
                        }
                        tempAnsPlayer1.removeAt(positionAns)
                        arrLineButton[data.extras?.getInt("indexLine")
                                ?: 0]!![data.extras?.getInt("indexBtn") ?: 0].text = ""
                        arrLineButton[data.extras?.getInt("indexLine")
                                ?: 0]!![data.extras?.getInt("indexBtn") ?: 0].tag = ""
                    }
                }
                data.extras?.getString("choosingHuruf") == "-" -> {
                    showAlertBlank(data)
                }
                arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text.isNullOrEmpty() -> {
                    var position = 0
                    tempCardOnHandPlayer1.mapIndexed { index, letter ->
                        if (data.extras?.getString("choosingHuruf") == letter.huruf
                                && letter.isShow == false) {
                            position = index
                        }
                    }
                    tempCardOnHandPlayer1[position] =
                            Letter(tempCardOnHandPlayer1[position].huruf,
                                    tempCardOnHandPlayer1[position].poin, tempCardOnHandPlayer1[position].jumlah, true)
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text = data.extras?.getString("choosingHuruf")
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag = data.extras?.getString("choosingHuruf")
                    tempAnsPlayer1.add(Coor(data.extras?.getInt("indexBtn")?:0, data.extras?.getInt("indexLine")?:0))
                }
                else -> {
                    var position = 0
                    tempCardOnHandPlayer1.mapIndexed { index, letter ->
                        if (arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag == letter.huruf
                                && letter.isShow == true) {
                            position = index
                        }
                    }

                    var position2 = 0
                    tempCardOnHandPlayer1.mapIndexed { index, letter ->
                        if (data.extras?.getString("choosingHuruf") == letter.huruf
                                && letter.isShow == false) {
                            position2 = index
                        }
                    }
                    tempCardOnHandPlayer1[position] =
                            Letter(tempCardOnHandPlayer1[position].huruf,
                                    tempCardOnHandPlayer1[position].poin, tempCardOnHandPlayer1[position].jumlah, false)
                    tempCardOnHandPlayer1[position2] =
                            Letter(tempCardOnHandPlayer1[position2].huruf,
                                    tempCardOnHandPlayer1[position2].poin, tempCardOnHandPlayer1[position2].jumlah, true)
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text = data.extras?.getString("choosingHuruf")
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag = data.extras?.getString("choosingHuruf")
                }
            }
        } else if (data?.extras?.getInt("turn") == 2) {
            when {
                data.extras.getBoolean("isDelete", false) -> {
                    if (arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text.isNotEmpty()) {
                        var position = 0
                        tempCardOnHandPlayer2.mapIndexed { index, letter ->
                            if (arrLineButton[data.extras?.getInt("indexLine")
                                            ?: 0]!![data.extras?.getInt("indexBtn")
                                            ?: 0].text == letter.huruf
                                    && letter.isShow == true) {
                                position = index
                            }
                        }

                        tempCardOnHandPlayer2[position] =
                                Letter(tempCardOnHandPlayer2[position].huruf,
                                        tempCardOnHandPlayer2[position].poin, tempCardOnHandPlayer2[position].jumlah, false)

                        var positionAns = 0
                        tempAnsPlayer2.mapIndexed { index, coor ->
                            if (coor.x == data.extras?.getInt("indexBtn") ?: 0 && data.extras?.getInt("indexLine") ?: 0 == coor.y) {
                                positionAns = index
                            }
                        }
                        tempAnsPlayer2.removeAt(positionAns)
                        arrLineButton[data.extras?.getInt("indexLine")
                                ?: 0]!![data.extras?.getInt("indexBtn") ?: 0].text = ""
                        arrLineButton[data.extras?.getInt("indexLine")
                                ?: 0]!![data.extras?.getInt("indexBtn") ?: 0].tag = ""
                    }
                }
                data.extras?.getString("choosingHuruf") == "-" -> {
                    showAlertBlank(data)
                }
                arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text.isNullOrEmpty() -> {
                    var position = 0
                    tempCardOnHandPlayer2.mapIndexed { index, letter ->
                        if (data.extras?.getString("choosingHuruf") == letter.huruf
                                && letter.isShow == false) {
                            position = index
                        }
                    }
                    tempCardOnHandPlayer2[position] =
                            Letter(tempCardOnHandPlayer2[position].huruf,
                                    tempCardOnHandPlayer2[position].poin, tempCardOnHandPlayer2[position].jumlah, true)
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text = data.extras?.getString("choosingHuruf")
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag = data.extras?.getString("choosingHuruf")
                    tempAnsPlayer2.add(Coor(data.extras?.getInt("indexBtn")?:0, data.extras?.getInt("indexLine")?:0))
                }
                else -> {
                    var position = 0
                    tempCardOnHandPlayer2.mapIndexed { index, letter ->
                        if (arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag == letter.huruf
                                && letter.isShow == true) {
                            position = index
                        }
                    }

                    var position2 = 0
                    tempCardOnHandPlayer2.mapIndexed { index, letter ->
                        if (data.extras?.getString("choosingHuruf") == letter.huruf
                                && letter.isShow == false) {
                            position2 = index
                        }
                    }
                    tempCardOnHandPlayer2[position] =
                            Letter(tempCardOnHandPlayer2[position].huruf,
                                    tempCardOnHandPlayer2[position].poin, tempCardOnHandPlayer2[position].jumlah, false)
                    tempCardOnHandPlayer2[position2] =
                            Letter(tempCardOnHandPlayer2[position2].huruf,
                                    tempCardOnHandPlayer2[position2].poin, tempCardOnHandPlayer2[position2].jumlah, true)
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].text = data.extras?.getString("choosingHuruf")
                    arrLineButton[data.extras?.getInt("indexLine")?:0]!![data.extras?.getInt("indexBtn")?:0].tag = data.extras?.getString("choosingHuruf")
                }
            }
        }
    }

    private fun getRandom(length: Int) : Int {
        random = Random()
        return random.nextInt(length)
    }

    private fun getCardOnHandP1(): MutableList<Letter> {
        val tempCard: MutableList<Letter> = arrayListOf()
        val tempBag = getBagHuruf(stockPlayer1)
        while (tempCard.size < 7) {
            val rand = getRandom(tempBag.size)
            var position: Int? = null
            stockPlayer1.mapIndexed { index, letter ->
                if (letter.huruf == tempBag[rand]) position = index
            }
            if (stockPlayer1[position?:0].jumlah > 0) {
                tempCard.add(listCard[position?:0])
                stockPlayer1[position?:0] = Letter(listCard[position?:0].huruf, listCard[position?:0].poin, stockPlayer1[position?:0].jumlah-1)
            }
        }

        return tempCard
    }

    private fun addCardOnHandP1(): MutableList<Letter> {
        val tempCard: MutableList<Letter> = arrayListOf()
        val tempBag = getBagHuruf(stockPlayer1)
        tempCardOnHandPlayer1.map {
            if (it.isShow == false) {
                tempCard.add(it)
            }
        }
        while (tempCard.size < 7) {
            val rand = getRandom(tempBag.size)
            var position: Int? = null
            stockPlayer1.mapIndexed { index, letter ->
                if (letter.huruf == tempBag[rand]) position = index
            }
            if (stockPlayer1[position?:0].jumlah > 0) {
                tempCard.add(listCard[position?:0])
                stockPlayer1[position?:0] = Letter(listCard[position?:0].huruf, listCard[position?:0].poin, stockPlayer1[position?:0].jumlah-1)
            }
        }

        return tempCard
    }

    private fun addCardOnHandP2(): MutableList<Letter> {
        val tempCard: MutableList<Letter> = arrayListOf()
        val tempBag = getBagHuruf(stockPlayer2)
        tempCardOnHandPlayer2.map {
            if (it.isShow == false) {
                tempCard.add(it)
            }
        }
        while (tempCard.size < 7) {
            val rand = getRandom(tempBag.size)
            var position: Int? = null
            stockPlayer2.mapIndexed { index, letter ->
                if (letter.huruf == tempBag[rand]) position = index
            }
            if (stockPlayer2[position?:0].jumlah > 0) {
                tempCard.add(listCard[position?:0])
                stockPlayer2[position?:0] = Letter(listCard[position?:0].huruf, listCard[position?:0].poin, stockPlayer2[position?:0].jumlah-1)
            }
        }

        return tempCard
    }

    private fun getCardOnHandP2(): MutableList<Letter> {
        val tempCard: MutableList<Letter> = arrayListOf()
        val tempBag = getBagHuruf(stockPlayer2)
        while (tempCard.size < 7) {
            val rand = getRandom(tempBag.size)
            var position: Int? = null
            stockPlayer2.mapIndexed { index, letter ->
                if (letter.huruf == tempBag[rand]) position = index
            }
            if (stockPlayer2[position?:0].jumlah > 0) {
                tempCard.add(listCard[position?:0])
                stockPlayer2[position?:0] = Letter(listCard[position?:0].huruf, listCard[position?:0].poin, stockPlayer2[position?:0].jumlah-1)
            }
        }

        return tempCard
    }

    private fun getArrayHuruf(cardOnHand: MutableList<Letter> = arrayListOf()): Array<String?> {
        val arr_huruf = arrayOfNulls<String>(7)
        cardOnHand.mapIndexed { index, letter ->
            if (letter.isShow == false) arr_huruf[index] = letter.huruf
        }
        return arr_huruf
    }

    fun showDialogDone(activity: Activity, title: String? = null, message: CharSequence, poin: Int) {
        val builder = AlertDialog.Builder(activity)

        if (title != null) builder.setTitle(title)

        builder.setMessage(message)
        builder.setPositiveButton("Ya") { dialog, id ->
            if (playerTurn == 1) {
                val tempPoin = tv_val_p1.text.toString().toInt()
                tv_val_p1.text = (tempPoin+poin).toString()
                playerTurn = 2
                val temp = addCardOnHandP1()
                tempCardOnHandPlayer1.clear()
                tempCardOnHandPlayer1.addAll(temp)
                tempAnsPlayer1.clear()
            } else if (playerTurn == 2) {
                val tempPoin = tv_val_p2.text.toString().toInt()
                tv_val_p2.text = (tempPoin+poin).toString()
                playerTurn = 1
                tempAnsPlayer2.clear()
                val temp = addCardOnHandP2()
                tempCardOnHandPlayer2.clear()
                tempCardOnHandPlayer2.addAll(temp)
                tempAnsPlayer2.clear()

            }

            isDoubleSkip = 0
            arrLineButton.mapIndexed { indexLine, mutableList ->
                mutableList?.mapIndexed { indexBtn, button ->
                    if (button.text.toString().isNotEmpty()) {
                        button.isEnabled = false
                    }
                }
            }

            dialog.dismiss() }
        builder.setNegativeButton("Tidak") { dialog, id ->
            dialog.dismiss() }
        builder.show()
    }

    fun showDialogSkip(activity: Activity, title: String? = null, message: CharSequence) {
        val builder = AlertDialog.Builder(activity)

        if (title != null) builder.setTitle(title)

        builder.setMessage(message)
        builder.setPositiveButton("Ya") { dialog, id ->
            if (playerTurn == 1) {
                playerTurn = 2
                tempCardOnHandPlayer1.mapIndexed { index, letter ->
                    tempCardOnHandPlayer1[index] = Letter(letter.huruf, letter.poin, letter.jumlah, false)
                }
                tempAnsPlayer1.clear()
            } else if (playerTurn == 2) {
                playerTurn = 1
                tempCardOnHandPlayer2.mapIndexed { index, letter ->
                    tempCardOnHandPlayer2[index] = Letter(letter.huruf, letter.poin, letter.jumlah, false)
                }
                tempAnsPlayer2.clear()
            }

            isDoubleSkip++
            tempCoor.clear()
            orientation = 0
            arrLineButton.mapIndexed { indexLine, mutableList ->
                mutableList?.mapIndexed { indexBtn, button ->
                    if (button.text.toString().isNotEmpty() && button.isEnabled) {
                        button.text = ""
                    }
                }
            }

            if (isDoubleSkip > 1) {
                arrLineButton.mapIndexed { indexLine, mutableList ->
                    mutableList?.mapIndexed { indexBtn, button ->
                        button.isEnabled = false
                    }
                }
                gameOver()
            }
            dialog.dismiss() }
        builder.setNegativeButton("Tidak") { dialog, id ->
            dialog.dismiss() }
        builder.show()
    }

    private fun showDialogStockCard(activity: Activity, title: String? = null, message: CharSequence) {
        val builder = AlertDialog.Builder(activity)

        if (title != null) builder.setTitle(title)

        builder.setMessage(message)
        builder.setPositiveButton("Ok") { dialog, id ->
            dialog.dismiss() }
        builder.show()
    }

    private fun getBagHuruf(listStock: MutableList<Letter>): MutableList<String> {
        val result: MutableList<String> = arrayListOf()
        listStock.map {
            var i = 0
            while (i < it.jumlah){
                result.add(it.huruf)
                i++
            }
        }

        return result
    }

    private fun getKataCoor(coor: Coor, orientation: Int): String {
        var result = ""
        var terkecil = 15
        var terbesar = 0

        if (orientation == 1) {
            var i = coor.y
            while (i >= 0) {
                if (arrLineButton[i]!![coor.x].text.isNotEmpty()) {
                    terkecil = i
                } else {
                    break
                }
                i--
            }
            i = coor.y
            while (i < 15) {
                if (arrLineButton[i]!![coor.x].text.isNotEmpty()) {
                    terbesar = i
                } else {
                    break
                }
                i++
            }

            i = terkecil
            while (i <= terbesar) {
                result += arrLineButton[i]!![coor.x].text
                tempCoor.add(Coor(coor.x, i))
                i++
            }
        } else if (orientation == 2) {
            var i = coor.x
            while (i >= 0) {
                if (arrLineButton[coor.y]!![i].text.isNotEmpty()) {
                    terkecil = i
                } else {
                    break
                }
                i--
            }
            i = coor.x
            while (i < 15) {
                if (arrLineButton[coor.y]!![i].text.isNotEmpty()) {
                    terbesar = i
                } else {
                    break
                }
                i++
            }

            i = terkecil
            while (i <= terbesar) {
                result += arrLineButton[coor.y]!![i].text
                i++
            }
        }

        return result
    }

    private fun getKataCoorTag(coor: Coor, orientation: Int): String {
        var result = ""
        var terkecil = 15
        var terbesar = 0

        if (orientation == 1) {
            var i = coor.y
            while (i >= 0) {
                if (arrLineButton[i]!![coor.x].text.isNotEmpty()) {
                    terkecil = i
                } else {
                    break
                }
                i--
            }
            i = coor.y
            while (i < 15) {
                if (arrLineButton[i]!![coor.x].text.isNotEmpty()) {
                    terbesar = i
                } else {
                    break
                }
                i++
            }

            i = terkecil
            while (i <= terbesar) {
                result += arrLineButton[i]!![coor.x].text
                tempCoor.add(Coor(coor.x, i))
                i++
            }
        } else if (orientation == 2) {
            var i = coor.x
            while (i >= 0) {
                if (arrLineButton[coor.y]!![i].text.isNotEmpty()) {
                    terkecil = i
                } else {
                    break
                }
                i--
            }
            i = coor.x
            while (i < 15) {
                if (arrLineButton[coor.y]!![i].text.isNotEmpty()) {
                    terbesar = i
                } else {
                    break
                }
                i++
            }

            i = terkecil
            while (i <= terbesar) {
                result += arrLineButton[coor.y]!![i].tag
                i++
            }
        }

        return result
    }

    private fun getValue(kata: String, listCoorAns: MutableList<Coor>): Int {
        var value = 0

        val arrKata = kata.toCharArray()

        arrKata.mapIndexed{ index, char ->
            val letter = getPoin(char.toString())
            value += letter.poin
        }

        return value
    }

    private fun getPoin(char: String) : Letter {
        var indexKata = 0

        listCard.mapIndexed { index, letter ->
            if (char.capitalize() == letter.huruf.capitalize()) {
                indexKata = index
            }
        }

        return listCard[indexKata]
    }

    private fun showAlertBlank(data: Intent?) {
        val edittext = EditText(this)
        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(18, 18, 18, 18)
        edittext.layoutParams = lp
        edittext.maxLines = 1
        edittext.inputType = InputType.TYPE_CLASS_TEXT
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Pilih Huruf")
        builder.setView(edittext)
        builder.setPositiveButton("Pilih", { dialog, whichButton ->
            val value = edittext.text.toString()

            var position = 0
            tempCardOnHandPlayer1.mapIndexed { index, letter ->
                if (data?.extras?.getString("choosingHuruf") == letter.huruf
                        && letter.isShow == false) {
                    position = index
                }
            }
            tempCardOnHandPlayer1[position] =
                    Letter(tempCardOnHandPlayer1[position].huruf,
                            tempCardOnHandPlayer1[position].poin, tempCardOnHandPlayer1[position].jumlah, true)
            arrLineButton[data?.extras?.getInt("indexLine")?:0]!![data?.extras?.getInt("indexBtn")?:0].text = value
            arrLineButton[data?.extras?.getInt("indexLine")?:0]!![data?.extras?.getInt("indexBtn")?:0].tag = "-"
            tempAnsPlayer1.add(Coor(data?.extras?.getInt("indexBtn")?:0, data?.extras?.getInt("indexLine")?:0))
            dialog.dismiss()
        })

        builder.setNegativeButton("Batal", { dialog, whichButton ->
            dialog.dismiss()
        })

        builder.show()
    }

    private fun getOrientation(listCoorAns: MutableList<Coor>) : Int {
        var orientation = 0

        if (listCoorAns[0].x == listCoorAns[1].x) orientation = 1
        else if (listCoorAns[0].y == listCoorAns[1].y) orientation = 2

        return orientation
    }

    private fun gameOver() {
        val builder = AlertDialog.Builder(this)

        if (title != null) builder.setTitle(title)

        builder.setMessage("Game Over")
        builder.setPositiveButton("OK") { dialog, id ->
            dialog.dismiss()
        }
        builder.show()
    }
}
