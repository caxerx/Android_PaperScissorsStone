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
        var itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.game_log_list_item, null)
        context = parent.context
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemLayoutView.ivMyChoose.setImageDrawable(GameUtils.getDrawable(context, items[position].myChoose))
        holder.itemLayoutView.ivOpponentChoose.setImageDrawable(GameUtils.getDrawable(context, items[position].opponentChoose))
        holder.itemLayoutView.tvResult.text = GameUtils.getResultText(items[position].myChoose, items[position].opponentChoose)

    }


}

class ViewHolder(var itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

data class ListData(var myChoose: Int, var opponentChoose: Int, var opponentName: String, var playTime: Date)
