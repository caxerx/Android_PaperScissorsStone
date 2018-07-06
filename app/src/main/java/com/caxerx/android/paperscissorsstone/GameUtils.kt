package com.caxerx.android.paperscissorsstone

import android.content.Context
import android.graphics.drawable.Drawable

object GameUtils {
    fun getDrawable(context: Context, play: Int): Drawable {
        return when (play) {
            1 -> context.resources.getDrawable(R.drawable.paper, null)
            2 -> context.resources.getDrawable(R.drawable.scissors, null)
            else -> context.resources.getDrawable(R.drawable.stone, null)
        }
    }

    fun getResultText(myChoose: Int, opponentChoose: Int): String {
        if (myChoose == opponentChoose) {
            return "Draw"
        }
        if ((myChoose == 1 && opponentChoose == 3) || (myChoose == 2 && opponentChoose == 1) || (myChoose == 3 && opponentChoose == 2)) {
            return "Win"
        }
        return "Lose"
    }

}