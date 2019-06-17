package com.caxerx.android.paperscissorsstone

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GravityCompat
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.dropTable
import org.jetbrains.anko.db.insert
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.timerTask

class GameActivity : AppCompatActivity() {

    var gameEnded = false
        set(value) {
            field = value
            if (value) {
                runOnUiThread {
                    tvContinue.visibility = View.VISIBLE
                    ivYes.visibility = View.VISIBLE
                    ivNo.visibility = View.VISIBLE
                    tvOpponent.visibility = View.VISIBLE

                    tvDragHere.visibility = View.INVISIBLE
                    ivSelection.visibility = View.INVISIBLE

                    ivOppenentSelect.scaleY = -1F
                }
            } else {
                runOnUiThread {
                    myChoice = 0

                    var score = getSharedPreferences("gameProfile", 0).getInt("score", 0)

                    time = (if (score >= 500) 10 else 5) * 100
                    progressBar.progress = time

                    ivOppenentSelect.scaleY = 1F
                    ivOppenentSelect.setImageDrawable(GameUtils.getDrawable(applicationContext, 0))

                    ivScissor.visibility = View.VISIBLE
                    ivPaper.visibility = View.VISIBLE
                    ivStone.visibility = View.VISIBLE

                    tvDragHere.visibility = View.VISIBLE
                    ivSelection.visibility = View.VISIBLE

                    tvOpponent.visibility = View.VISIBLE
                    tvContinue.visibility = View.INVISIBLE
                    ivYes.visibility = View.INVISIBLE
                    ivNo.visibility = View.INVISIBLE
                }
            }
        }

    var timeOut = false
        set(value) {
            field = value
            if (value) {
                ivScissor.visibility = View.INVISIBLE
                ivPaper.visibility = View.INVISIBLE
                ivStone.visibility = View.INVISIBLE
                async(UI) {
                    showResult()
                }
            } else {
                ivStone.visibility = View.VISIBLE
                ivPaper.visibility = View.VISIBLE
                ivScissor.visibility = View.VISIBLE
            }
        }

    var myChoice = 0
    var tempChoice = 0
    var draggingView: View? = null
    lateinit var timerTask: TimerTask
    lateinit var timer: Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameEnded = false
        getOpponent()


        var onTouchListener: View.OnTouchListener = View.OnTouchListener { view, event ->
            if (gameEnded) {
                return@OnTouchListener false
            }
            if (event.action == MotionEvent.ACTION_DOWN) {
                view.visibility = View.INVISIBLE
                var builder = View.DragShadowBuilder(view)
                view.startDragAndDrop(ClipData.newPlainText("", ""), builder, view, 0)

                draggingView = view

                when (view.id) {
                    R.id.ivPaper -> {
                        tempChoice = 1
                    }
                    R.id.ivScissor -> {
                        tempChoice = 2
                    }
                    R.id.ivStone -> {
                        tempChoice = 3
                    }
                    else -> {
                        tempChoice = 0
                    }
                }

                return@OnTouchListener true
            }
            false
        }
        ivPaper.setOnTouchListener(onTouchListener)
        ivScissor.setOnTouchListener(onTouchListener)
        ivStone.setOnTouchListener(onTouchListener)

        ivYes.setOnClickListener {
            getOpponent()
            initTimer()
        }

        ivNo.setOnClickListener {
            timer.cancel()
            finish()
        }


        ivSelection.setOnDragListener { view, dragEvent ->
            //view.visibility = View.VISIBLE
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {

                }
                DragEvent.ACTION_DROP -> {
                    if (gameEnded) {
                        return@setOnDragListener false
                    }
                    myChoice = tempChoice
                    tempChoice = 0
                    timer.cancel()
                    async(UI) {
                        showResult()
                    }
                    return@setOnDragListener true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    when (myChoice) {
                        1 -> {
                            ivStone.visibility = View.INVISIBLE
                            ivPaper.visibility = View.VISIBLE
                            ivScissor.visibility = View.INVISIBLE
                        }
                        2 -> {
                            ivStone.visibility = View.INVISIBLE
                            ivPaper.visibility = View.INVISIBLE
                            ivScissor.visibility = View.VISIBLE
                        }
                        3 -> {
                            ivStone.visibility = View.VISIBLE
                            ivPaper.visibility = View.INVISIBLE
                            ivScissor.visibility = View.INVISIBLE
                        }
                        else -> {
                            if (!gameEnded) {
                                ivStone.visibility = View.VISIBLE
                                ivPaper.visibility = View.VISIBLE
                                ivScissor.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                else -> {

                }
            }
            true
        }

    }

    lateinit var opponentResult: OpponentData

    fun getOpponent() {
        async(UI) {
            tvOpponent.text = ""
            val data: Deferred<OpponentData> = bg {
                var resultData = URL("https://h4vttbs0ai.execute-api.ap-southeast-1.amazonaws.com/ptms")
                Gson().fromJson(resultData.readText(), OpponentData::class.java)
            }
            opponentResult = data.await()
            opponentResult.hand++
            tvOpponent.text = "${opponentResult}\n"
            gameEnded = false
        }
    }

    fun showResult() {
        try {
            var gameResult = GameUtils.getResultText(myChoice, opponentResult.hand)
            if (gameResult != "Draw") {
                gameResult = "You $gameResult"
            }
            tvOpponent.text = "${opponentResult}\n$gameResult!"
            ivOppenentSelect.setImageDrawable(GameUtils.getDrawable(applicationContext, opponentResult.hand))
            gameEnded = true
            println(gameEnded)
            editScore(myChoice, opponentResult.hand)
            bg {
                database.use {
                    val now = Date(System.currentTimeMillis())
                    val dateFormat = SimpleDateFormat("dd/MM/yyy")
                    val timeFormat = SimpleDateFormat("HH:mm:ss")

                    insert("GameLog", "gamedate" to dateFormat.format(now),
                            "gametime" to timeFormat.format(now),
                            "opponentName" to opponentResult.name,
                            "opponentAge" to opponentResult.age,
                            "yourHand" to myChoice,
                            "opponentHand" to opponentResult.hand
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun editScore(myHand: Int, opponentHand: Int) {
        var score = when (GameUtils.getResultText(myHand, opponentHand)) {
            "Win" -> 50
            "Lose" -> 5
            "Draw" -> 10
            else -> 0
        }
        var pref = getSharedPreferences("gameProfile", 0)
        var prefEditor = pref.edit()
        prefEditor.putInt("score", pref.getInt("score", 0) + score)
        prefEditor.apply()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        finish()
    }

    fun initTimer() {
        var score = getSharedPreferences("gameProfile", 0).getInt("score", 0)

        time = (if (score >= 500) 10 else 5) * 100
        progressBar.progress = time
        timer = Timer()
        timerTask = timerTask {
            time--
            progressBar.progress = time
            if (time == 0) {
                timer.cancel()
                runOnUiThread {
                    timeOut = true
                    gameEnded = true
                    draggingView?.cancelDragAndDrop()
                }
            }
        }
        timer.schedule(timerTask, 0L, 10L)
    }

    var time = 0
    override fun onResume() {
        super.onResume()
        progressBar.max = time
        progressBar.progress = time
        initTimer()
    }
}

data class OpponentData(val name: String, val age: Int, var hand: Int) {
    override fun toString(): String {
        return """Opponent:
            |Name: $name
            |Age: $age""".trimMargin()
    }
}