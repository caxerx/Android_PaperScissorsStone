package com.caxerx.android.paperscissorsstone

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class GameLogDbContext(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "GameLogs", null, 1) {

    companion object {
        private var instance: GameLogDbContext? = null

        @Synchronized
        fun getInstance(ctx: Context): GameLogDbContext {
            if (instance == null) {
                instance = GameLogDbContext(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable("GameLog", true,
                "gameNo" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "gamedate" to TEXT,
                "gametime" to TEXT,
                "opponentName" to TEXT,
                "opponentAge" to INTEGER,
                "yourHand" to INTEGER,
                "opponentHand" to INTEGER)
    }


    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}


val Context.database: GameLogDbContext
    get() = GameLogDbContext.getInstance(applicationContext)