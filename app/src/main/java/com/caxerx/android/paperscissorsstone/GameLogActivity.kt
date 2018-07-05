package com.caxerx.android.paperscissorsstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game_log.*
import java.util.*

class GameLogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_log)
        var i1 = ListData(1, 2, "Hi", Date())
        var i2 = ListData(2, 3, "Hi", Date())
        var i3 = ListData(2, 2, "Hi", Date())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GameLogViewAdapter(listOf(i1, i2, i3))
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

}
