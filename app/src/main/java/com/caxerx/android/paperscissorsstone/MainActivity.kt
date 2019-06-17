package com.caxerx.android.paperscissorsstone

import android.content.Intent
import android.os.Bundle
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database.use {
            database.onCreate(this)
        }
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)




        fab.setOnClickListener { view ->
            var pref = getSharedPreferences("gameProfile", 0)
            if (pref.contains("name") && pref.contains("birth")) {
                startActivity(Intent(applicationContext, GameActivity::class.java))
            } else {
                AlertDialog.Builder(this).setMessage("Please create your profile before start game.").setPositiveButton("Create") { _, _ ->
                    nav_view.setCheckedItem(R.id.nav_profile)
                    changeFragment(getFragment(3)!!)
                }.setNegativeButton("Later") { _, _ -> }.show()
            }
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setCheckedItem(R.id.nav_game)
        changeFragment(getFragment(0)!!)


        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        nav_view.setCheckedItem(R.id.nav_game)
        changeFragment(getFragment(0)!!)
    }

    fun changeFragment(fragment: Fragment) {
        var ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.content_main, fragment)
        ft.commit()
    }

    fun getFragment(id: Int): Fragment? {
        return when (id) {
            0 -> GameFragment()
            1 -> GameLogFragment()
            2 -> BarChartFragment()
            3 -> ProfileFragment()
            else -> null
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_game -> {
                changeFragment(getFragment(0)!!)
            }
            R.id.nav_gamelog -> {
                changeFragment(getFragment(1)!!)
            }
            R.id.nav_statistic -> {
                changeFragment(getFragment(2)!!)
            }
            R.id.nav_profile -> {
                changeFragment(getFragment(3)!!)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
