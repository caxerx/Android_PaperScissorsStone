package com.caxerx.android.paperscissorsstone

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.text.DecimalFormat

class BarChartView(context: Context) : View(context, null) {
    constructor(context: Context, attributes: AttributeSet) : this(context)

    var paint = Paint()

    init {

    }


    override fun onDraw(canvas: Canvas) {
        paint.isAntiAlias = true
        var leftPaddingForText = 100F
        var bottomPaddingForText = 50F
        paint.color = Color.BLACK
        paint.strokeWidth = 3F

        //draw X line ( move X
        canvas.drawLine(leftPaddingForText, height - bottomPaddingForText, width.toFloat(), height - bottomPaddingForText, paint)
        //draw Y line ( move Y
        canvas.drawLine(leftPaddingForText, 0F, leftPaddingForText, height - bottomPaddingForText, paint)

        var bars = 3
        var bottomText = arrayOf("Win", "Lose", "Draw")
        var color = arrayOf(Color.RED, Color.GREEN, Color.BLUE)
        var barPadding = 50F

        var lineAndTextPadding = 25F
        var eachBarWidth = (width - leftPaddingForText - barPadding * (bars + 1)) / bars


        var score = arrayOf(27, 61, 57)

        paint.textAlign = Paint.Align.RIGHT
        paint.textSize = 30F
        var textRight = leftPaddingForText - lineAndTextPadding
        var textBottom = height - bottomPaddingForText

        var min = score.min()!!
        var max = score.max()!!
        var step = max / 7F
        var heightStep = bottom / 8F


        var df = DecimalFormat("0.0")

        for (i in 0 until 8) {
            canvas.drawText(df.format(step * i), textRight, textBottom, paint)
            textBottom -= heightStep
        }


        paint.textSize = 40F
        paint.strokeWidth = 3F
        paint.textAlign = Paint.Align.CENTER

        var rectLeft = leftPaddingForText + barPadding
        var rectTop = height - bottomPaddingForText + lineAndTextPadding

        for (i in 0 until bars) {
            paint.color = Color.BLACK

            var rect = Rect(rectLeft.toInt(), rectTop.toInt(), (rectLeft + eachBarWidth).toInt(), height)
            canvas.drawText(bottomText[i], rect.centerX().toFloat(), rect.bottom.toFloat(), paint)

            paint.color = color[i]
            var fullHeight = (height - bottomPaddingForText) - (textBottom + heightStep)
            var rectHeight = (score[i].toFloat() / max) * fullHeight
            canvas.drawRect(rectLeft, (height - bottomPaddingForText) - rectHeight, rectLeft + eachBarWidth, height - bottomPaddingForText, paint)

            rectLeft += eachBarWidth + barPadding
        }


    }
}