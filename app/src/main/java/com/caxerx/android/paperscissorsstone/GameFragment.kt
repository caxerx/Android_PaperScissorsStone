package com.caxerx.android.paperscissorsstone

import android.graphics.Color
import android.icu.util.LocaleData
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game_log.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.db.*
import org.jetbrains.anko.textColor
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.util.*

class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    var clickedTime = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvGreeting.setOnClickListener {
            clickedTime++
            if (clickedTime == 5) {
                tvGreeting.textColor = Color.RED
                var pref = context!!.getSharedPreferences("gameProfile", 0).edit()
                pref.putInt("score", 499)
                pref.apply()
                Snackbar.make(view, "Cheat: Set score to 499!", BaseTransientBottomBar.LENGTH_SHORT).show()
                loadData()
            }
            if (clickedTime == 20) {
                async(UI) {
                    bg {
                        tvGreeting.textColor = Color.GREEN
                        val context = requireContext()
                        context.database.use {
                            this.dropTable("GameLog")
                            context.database.onCreate(this)
                        }
                        var pref = context.getSharedPreferences("gameProfile", 0).edit()
                        pref.clear()
                        pref.apply()
                        loadData()
                    }
                    Snackbar.make(view, "Data cleared", BaseTransientBottomBar.LENGTH_SHORT).show()
                }

            }
        }
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
        clickedTime = 0
        tvGreeting.textColor = Color.BLACK
    }

    fun loadData() {
        var pref = context!!.getSharedPreferences("gameProfile", 0)
        var score = pref.getInt("score", 0)
        if (pref.contains("name") && pref.contains("birth")) {
            cardView.visibility = View.VISIBLE
            tvGreeting.text = "Hello, ${pref.getString("name", "")}!"
            tvProfileName.text = "Name: ${pref.getString("name", "")}"
            tvProfileScore.text = "Score: $score"
            var df = SimpleDateFormat("yyyy/MM/dd")
            val birth = pref.getString("birth", "1990/01/01")
            var date: Date
            var now = Date(System.currentTimeMillis())
            try {
                date = df.parse(birth)
            } catch (e: Exception) {
                date = now
            }

            println(date.year)
            println(now.year)

            val age = now.year - date.year

            tvProfileAge.text = "Age: $age"
        } else {
            tvGreeting.text = "Hello!"
            cardView.visibility = View.INVISIBLE
        }

        if (score >= 500) {
            var context = this.context!!
            ivFeature.setImageDrawable(context.getDrawable(R.drawable.ic_clock))
            tvFeatureName.text = "Increase think time (Unlocked)"
            tvFeatureDescription.text = "Increase your thinking time to 10 seconds"
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        loadData()
    }


}
