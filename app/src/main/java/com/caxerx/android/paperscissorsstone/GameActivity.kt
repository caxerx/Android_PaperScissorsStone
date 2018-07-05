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
        imageView2.setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                view.startDragAndDrop(ClipData.newPlainText("", ""), View.DragShadowBuilder(view), view, 0)
                return@setOnTouchListener true
            }
            false
        }

        imageView3.setOnDragListener { view, dragEvent ->
            //view.visibility = View.VISIBLE
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED->{

                }
                DragEvent.ACTION_DROP -> {
                    println("drop")

                }
                else -> {

                }
            }
            true
        }
    }
}
