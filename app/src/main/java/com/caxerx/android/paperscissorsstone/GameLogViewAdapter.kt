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

    companion object {
        fun getDrawable(context: Context, play: Int): Drawable {
            return when (play) {
                1 -> context.resources.getDrawable(paper, null)
                2 -> context.resources.getDrawable(scissors, null)
                else -> context.resources.getDrawable(stone, null)
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

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemLayoutView = LayoutInflater.from(parent.context).inflate(R.layout.game_log_list_item, null)
        context = parent.context
        return ViewHolder(itemLayoutView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemLayoutView.ivMyChoose.setImageDrawable(getDrawable(context, items[position].myChoose))
        holder.itemLayoutView.ivOpponentChoose.setImageDrawable(getDrawable(context, items[position].opponentChoose))
        holder.itemLayoutView.tvResult.text = getResultText(items[position].myChoose, items[position].opponentChoose)

    }


}

class ViewHolder(var itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

data class ListData(var myChoose: Int, var opponentChoose: Int, var opponentName: String, var playTime: Date)
