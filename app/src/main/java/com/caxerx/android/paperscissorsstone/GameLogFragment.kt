package com.caxerx.android.paperscissorsstone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_game_log.*
import org.jetbrains.anko.db.SqlOrderDirection
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import java.util.*

class GameLogFragment : Fragment() {

    /*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_game_log)
        var i1 = ListData(1, 2, "Hi", Date())
        var i2 = ListData(2, 3, "Hi", Date())
        var i3 = ListData(2, 2, "Hi", Date())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GameLogViewAdapter(listOf(i1, i2, i3))
        recyclerView.itemAnimator = DefaultItemAnimator()
    }
    */

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = mutableListOf<ListData>()
        var result = view.context.database.use {
            select("GameLog", "gamedate", "gametime", "opponentName", "opponentAge", "yourHand", "opponentHand")
                    .orderBy("gamedate", SqlOrderDirection.DESC)
                    .orderBy("gametime", SqlOrderDirection.DESC).limit(100).exec {
                        parseList(rowParser { gamedate: String, gametime: String, opponentName: String, opponentAge: Int, yourHand: Int, opponentHand: Int ->
                            list.add(ListData(yourHand, opponentHand, opponentName, gametime, gamedate, opponentAge))
                        })
                    }
        }

        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = GameLogViewAdapter(list)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }


}
