package com.example.maichel.submission2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx:Context): MyDatabaseOpenHelper{
            if(instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoritesMatch.TABLE_FAVORITE, true,
            FavoritesMatch.MATCH_ID to TEXT + PRIMARY_KEY,
            FavoritesMatch.DATE to TEXT,
            FavoritesMatch.TIME to TEXT,
            FavoritesMatch.TEAM_HOME_NAME to TEXT,
            FavoritesMatch.TEAM_AWAY_NAME to TEXT,
            FavoritesMatch.TEAM_HOME_BADGE to TEXT,
            FavoritesMatch.TEAM_AWAY_BADGE to TEXT,
            FavoritesMatch.TEAM_HOME_SCORE to TEXT,
            FavoritesMatch.TEAM_AWAY_SCORE to TEXT,
            FavoritesMatch.TEAM_HOME_SHOOT to TEXT,
            FavoritesMatch.TEAM_AWAY_SHOOT to TEXT,
            FavoritesMatch.TEAM_HOME_GOAL_DETAIL to TEXT,
            FavoritesMatch.TEAM_AWAY_GOAL_DETAIL to TEXT,
            FavoritesMatch.TEAM_HOME_GOAL_KEEPER to TEXT,
            FavoritesMatch.TEAM_AWAY_GOAL_KEEPER to TEXT,
            FavoritesMatch.TEAM_HOME_DEF to TEXT,
            FavoritesMatch.TEAM_AWAY_DEF to TEXT,
            FavoritesMatch.TEAM_HOME_MID to TEXT,
            FavoritesMatch.TEAM_AWAY_MID to TEXT,
            FavoritesMatch.TEAM_HOME_FRONT to TEXT,
            FavoritesMatch.TEAM_AWAY_FRONT to TEXT,
            FavoritesMatch.TEAM_HOME_SUBS to TEXT,
            FavoritesMatch.TEAM_AWAY_SUBS to TEXT,
            FavoritesMatch.TEAM_HOME_YELLOW_CARD to TEXT,
            FavoritesMatch.TEAM_AWAY_YELLOW_CARD to TEXT,
            FavoritesMatch.TEAM_HOME_RED_CARD to TEXT,
            FavoritesMatch.TEAM_AWAY_RED_CARD to TEXT,
            FavoritesMatch.TEAM_HOME_FORMATION to TEXT,
            FavoritesMatch.TEAM_AWAY_FORMATION to TEXT,
            FavoritesMatch.TEAM_HOME_ID to TEXT,
            FavoritesMatch.TEAM_AWAY_ID to TEXT,
            FavoritesMatch.SEASON to TEXT)

        db.createTable(FavoritesTeam.TABLE_TEAM_FAVORITE, true,
            FavoritesTeam.TEAM_ID to TEXT + PRIMARY_KEY + UNIQUE,
            FavoritesTeam.TEAM_NAME to TEXT + UNIQUE,
            FavoritesTeam.TEAM_YEAR to TEXT,
            FavoritesTeam.TEAM_STADION to TEXT,
            FavoritesTeam.TEAM_DESCRIPTION to TEXT,
            FavoritesTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoritesMatch.TABLE_FAVORITE, true)
        db.dropTable(FavoritesTeam.TABLE_TEAM_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)