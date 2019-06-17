package com.caxerx.android.paperscissorsstone

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caxerx.android.paperscissorsstone.R.drawable.*
import kotlinx.android.synthetic.main.game_log_list_item.view.*
import java.util.*

class GameLogViewAdapter(var items: List<ListData>) : RecyclerView.Adapter<ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.game_log_list_item, parent, false)
        context = parent.context
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items[position]
        holder.itemLayoutView.ivMyChoose.setImageDrawable(GameUtils.getDrawable(context, item.myChoose))
        holder.itemLayoutView.ivOpponentChoose.setImageDrawable(GameUtils.getDrawable(context, item.opponentChoose))
        holder.itemLayoutView.tvResult.text = GameUtils.getResultText(item.myChoose, item.opponentChoose)
        holder.itemLayoutView.tvTimeAndOpponent.text = "${item.date} ${item.time} - ${item.opponentName}(${item.opponentAge}) "
    }

}

class ViewHolder(var itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

data class ListData(var myChoose: Int, var opponentChoose: Int, var opponentName: String, var time: String, var date: String, var opponentAge: Int)
