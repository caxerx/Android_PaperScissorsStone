package com.caxerx.android.paperscissorsstone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game_log.*
import java.util.*

class GameLogActivity : Fragment() {

    /*
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
    */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_game_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var i1 = ListData(1, 2, "Hi", Date())
        var i2 = ListData(2, 3, "Hi", Date())
        var i3 = ListData(2, 2, "Hi", Date())
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = GameLogViewAdapter(listOf(i1, i2, i3))
        recyclerView.itemAnimator = DefaultItemAnimator()
    }


}
