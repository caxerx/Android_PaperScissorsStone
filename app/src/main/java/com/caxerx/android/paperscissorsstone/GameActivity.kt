package com.caxerx.android.paperscissorsstone

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var onTouchListener: View.OnTouchListener = View.OnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                var builder = View.DragShadowBuilder(view)
                view.startDragAndDrop(ClipData.newPlainText("", ""), builder, view, 0)
                return@OnTouchListener true
            }
            println(event.action.toString())
            false
        }
        imageView7.setOnTouchListener(onTouchListener)
        imageView8.setOnTouchListener(onTouchListener)
        imageView9.setOnTouchListener(onTouchListener)

        imageView3.setOnDragListener { view, dragEvent ->
            //view.visibility = View.VISIBLE
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {

                }
                DragEvent.ACTION_DROP -> {
                    when (view.id) {
                        R.id.imageView7 -> {

                        }
                        R.id.imageView8 -> {

                        }
                        R.id.imageView9 -> {

                        }
                        else -> {

                        }
                    }
                }
                else -> {

                }
            }
            true
        }

    }
}
